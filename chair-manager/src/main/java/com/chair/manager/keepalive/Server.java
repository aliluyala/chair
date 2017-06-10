package com.chair.manager.keepalive;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chair.manager.exception.ChairException;
import com.chair.manager.pojo.Device;
import com.chair.manager.service.DeviceService;

import redis.clients.jedis.JedisCluster;

/**
 * 接收消息方法：receiveByInputStream() 响应消息方法：responseByOutputStream() 发送消息方法：send()
 * 
 * @author Administrator
 *
 */
@Component
public class Server {
	private Logger logger = Logger.getLogger(Server.class);
	
	// *R1,000,0000000000,898602b6111700445060,864811034682927,1.0,1.0,0.1#
	private int port;
	private volatile boolean running = false;
	private long receiveTimeDelay = Constant.RECEIVE_TIME_DELAY;
	private ConcurrentHashMap<Class, ServerObjectAction> actionMapping = new ConcurrentHashMap<Class, ServerObjectAction>();
	private static ConcurrentHashMap<String, Socket> ipSocket;
	private static ConcurrentHashMap<String, Socket> ccidSocket;
	private ConcurrentHashMap<String, String> ipToken = new ConcurrentHashMap<String, String>();
	private Thread connWatchDog;
	@Autowired
	private JedisCluster jedisCluster;
	@Autowired
	private DeviceService deviceService;

	public DeviceService getDeviceService() {
		return deviceService;
	}

	public void setDeviceService(DeviceService deviceService) {
		this.deviceService = deviceService;
	}


	public Server() {
	}

	/**
	 * 构造函数
	 * 
	 * @param port
	 */
	public Server(int port) {
		this.port = port;
		this.start();
		if(ipSocket == null)
			ipSocket = new ConcurrentHashMap<String, Socket>();
		if(ccidSocket == null)
			ccidSocket = new ConcurrentHashMap<String, Socket>();
	}

	public void start() {
		if (running)
			return;
		running = true;
		connWatchDog = new Thread(new ConnWatchDog());
		connWatchDog.start();
	}

	@SuppressWarnings("deprecation")
	public void stop() {
		if (running)
			running = false;
		if (connWatchDog != null)
			connWatchDog.stop();
	}

	// 测试
	public static void main(String[] args) throws IOException {
		int port = Constant.PORT;
		System.out.println("----服务器启动--端口---" + port);
		Server server = new Server(port);
		server.start();
		
		String regEx = "^\\*.*#$";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher("*R1,001,0000000000,898602b6111700445060,864811034682927,1.0,1.0,0.1#\0");
		boolean b = m.find();
//		System.out.println("---b---"+b);
//		System.out.println("*R1,001,0000000000,898602b6111700445060,864811034682927,1.0,1.0,0.1#\0");
	}

	/*------------------------------------------------------------------------------------------*/
	/**
	 * 添加接收对象的处理对象。
	 * 
	 * @param cls
	 *            待处理的对象，其所属的类。
	 * @param action
	 *            处理过程对象。
	 */
	public void addActionMap(Class<?> cls, ServerObjectAction action) {
		actionMapping.put(cls, action);
	}

	public class ConnWatchDog implements Runnable {

		public void run() {
			try {
				ServerSocket ss = new ServerSocket(port, 5);
				while (running) {
					Socket s = ss.accept();
					new Thread(new SocketAction(s)).start();
				}
			} catch (IOException e) {
				e.printStackTrace();
				Server.this.stop();
			}

		}
	}

	public class SocketAction implements Runnable {
		Socket s;
		boolean run = true;
		long lastReceiveTime = System.currentTimeMillis();

		public SocketAction() {
		}

		public SocketAction(Socket s) {
			this.s = s;
		}

		public void run() {
			while (running && run) {
				if (System.currentTimeMillis() - lastReceiveTime > receiveTimeDelay) {
					overThis();
				} else {
					try {
						receiveByInputStream(); // 接收消息
						/*
						 * InputStream is = s.getInputStream();
						 * if(is.available()>0){ lastReceiveTime =
						 * System.currentTimeMillis();
						 * ipMapping.put(s.getInetAddress().toString().replace(
						 * "/", ""), s);// 以k-v保存ip对应的socket对象 // receive(); //
						 * 接收消息 // response(); // 响应消息 lastReceiveTime =
						 * System.currentTimeMillis(); receiveByInputStream();
						 * responseByOutputStream(); }else { Thread.sleep(100);
						 * }
						 */
					} catch (Exception e) {
						e.printStackTrace();
						overThis();
					}
				}
			}
		}

		/**
		 * 发送消息
		 * 
		 * @param toClientIP
		 *            客户端IP
		 * @param toMessage
		 *            消息内容
		 * @throws IOException
		 */
		public boolean send(String ccid, String toMessage) {
			try {
//				Socket clientSocket = ipSocket.get(toClientIP+":"+toClientPort);
				Socket clientSocket = ccidSocket.get(ccid);
				logger.info("------【向"+ccid+" 发送消息，获取socket对象】--->>>"+clientSocket+" ---消息为：>>>"+toMessage);
				OutputStream os = clientSocket.getOutputStream();
				byte[] b = toMessage.getBytes();
				os.write(b);
				os.flush();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}

		/**
		 * 接收消息(InputStream)
		 * 
		 * @throws IOException
		 * @throws ClassNotFoundException
		 * @throws InterruptedException
		 */
		private void receiveByInputStream() throws IOException, ClassNotFoundException, InterruptedException {
			String clientIP = s.getInetAddress().toString().replace("/", "");
			int clientPort = s.getPort();
			s.setKeepAlive(true);// 设置长连接
			InputStream is = s.getInputStream();
			if (is.available() > 0) {
//				ipMapping.put(clientIP+":", s);// 以k-v保存ip对应的socket对象
				logger.debug("---开始接收消息---is.available()---" + is.available() + "---is.read()---" + is.read()
						+ " --- " + s.toString());
				int length = 0;
				byte[] buffer = new byte[1024];
				while (-1 != (length = is.read(buffer, 0, 1024))) {
					String reciverMsg = "";
					reciverMsg += new String(buffer, 0, length);
//					logger.info("--接收来自客户端消息--" + reciverMsg);
					// TODO 处理接收到的消息，解析报文
					resolveMessage(clientIP, clientPort, reciverMsg.trim());
//					responseByOutputStream("from server"); //响应客户端
				}
			} else {
				Thread.sleep(10);
			}
		}

		// 解析报文
		public void resolveMessage(String ip, int clientPort, String reciverMsg) throws IOException {
			String regEx = "^\\*.*#$";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(reciverMsg);
			boolean b = m.find();
			logger.info("---【解析报文["+reciverMsg+"]，匹配以*开头，以#结尾，结果为】---" + b + "\n ip:port = " + ip+":"+clientPort);
			if (b) {
				String[] requestBodys = reciverMsg.substring(reciverMsg.indexOf("*") + 1, reciverMsg.length() - 1)
						.split(",");
				for (String key : requestBodys) {
					System.err.print(key + ",");
					if ("R1".equalsIgnoreCase(key)) { // 注册命令
						logger.info(jedisCluster + "---requestBodys的第三位数---" + requestBodys[2]);
						String token = get(ip+":"+clientPort);
						String snk = "001";
						if ("".equals(token) || null == token) {
							// 生成token，并且保存到redis
							token = "R" + new Date().getTime();
							logger.info("---token为空，创建token--->>>" + token);
							set(ip+":"+clientPort, token);	//ip-token
						}

						// 新增或者更新设备
						Device device = new Device();
						device.setDeviceNo(requestBodys[3]);
						device.setStatus(1);
						device.setLastUpdate(new Date());
						device.setCreateTime(new Date());
						deviceService.saveOrUpdate(device);
						logger.debug("------新增或者更新设备信息；后------" + device);
						set(token, requestBodys[3]);
						set(requestBodys[3], ip+":"+clientPort);
						ipSocket.put(ip+":"+clientPort, s);
						ccidSocket.put(requestBodys[3], s);
						// 响应客户端消息
						String send2ClientMsg = "*" + key + "," + snk + "," + token + "#";
						logger.info("------ 响应客户端R1消息内容------" + send2ClientMsg);
						responseByOutputStream(send2ClientMsg);

					} else if ("G0".equalsIgnoreCase(key)) { // 保持连接，H0
						String token = get(ip);
						String snk = "001";
						if ("".equals(token) || null == token) {
							snk = "000";
						}
						String send2ClientMsg = "*" + key + "," + snk + "," + token + "#";
						responseByOutputStream(send2ClientMsg);
					}
				}
			}
		}

		/**
		 * 响应消息(OutputStream)
		 */
		private void responseByOutputStream(String sendMsg) throws IOException {
			OutputStream os = s.getOutputStream();
			System.out.println(
					new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "---响应客户端消息---" + sendMsg);
			byte[] b = sendMsg.getBytes();
			os.write(b);
			os.flush();
		}

		/**
		 * 客户端断开链接
		 */
		private void overThis() {
			if (run)
				run = false;
			if (s != null) {
				try {
					s.close();
				} catch (IOException e) {
					logger.error("socket关闭失败，失败原因："+e.getMessage());
					throw new ChairException("-1", "系统错误");
				}
			}
			
			String clientIP = s.getInetAddress().toString().replace("/", "");
			int clientPort = s.getPort();
			logger.info("---客户端断开连接----"+clientIP+":"+clientPort);
			String token = get(clientIP+":"+clientPort);
			String ccid = get(token);
			del(clientIP+":"+clientPort);
			del(token);
			del(ccid);
			//删除记录设备是否正在使用的记录
//			del("T"+ccid);
			logger.info("关闭：" + s.getRemoteSocketAddress());
		}

	}

	// @Override
	// public void onApplicationEvent(ContextRefreshedEvent event) {
	// if (event.getSource() instanceof XmlWebApplicationContext) {
	// if (((XmlWebApplicationContext)
	// event.getSource()).getDisplayName().equals("Root WebApplicationContext"))
	// {
	// int port = Constant.PORT;
	// System.err.println("----spring初始化Socket服务器启动，建立长连接--端口---" + port);
	// Server server = new Server(port);
	// server.start();
	// set("test","---hys---"+new SimpleDateFormat("yyyy-MM-dd
	// HH:mm:ss").format(new Date()));
	// get("test");
	// }
	// }
	//
	// }

	private void set(String key, String value) {
		logger.info("------【保存redis.set()】------key："+key+" \t value："+value);
		jedisCluster.set(key, value);
	}

	private String get(String key) {
		logger.info("------【查询redis.get()】------key："+key);
		String res = jedisCluster.get(key);
		return res;
	}
	
	private void del(String key){
		logger.info("------【删除redis.del()】------key："+key);
		jedisCluster.del(key);
	}
	

}
