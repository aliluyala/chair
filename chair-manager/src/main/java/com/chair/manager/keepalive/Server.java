package com.chair.manager.keepalive;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.chair.manager.controller.DateUtils;
import com.chair.manager.exception.ChairException;
import com.chair.manager.pojo.Device;
import com.chair.manager.service.ConsumedDetailsService;
import com.chair.manager.service.DeviceService;
import com.chair.manager.service.UserAccountService;

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
	private static ConcurrentHashMap<Socket, String> socketCCID;
	private ConcurrentHashMap<String, String> ipToken = new ConcurrentHashMap<String, String>();
	private Thread connWatchDog;
	@Autowired
	private JedisCluster jedisCluster;
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private ConsumedDetailsService consumedDetailsService;
	@Autowired
	private UserAccountService userAccountService;

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
		if (ipSocket == null)
			ipSocket = new ConcurrentHashMap<String, Socket>();
		if (ccidSocket == null)
			ccidSocket = new ConcurrentHashMap<String, Socket>();
		if (socketCCID == null)
			socketCCID = new ConcurrentHashMap<Socket, String>();

		// 开始设备定时任务
//		quartzJob();
	}

	private void quartzJob() {
		Runnable runnable = new Runnable() {  
            public void run() {  
                // task to run goes here  
				logger.info("--------定时任务执行时间-Hello !!!------->>>>"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				printSokcet();	//打印socket列表
				//查询所有设备列表
				Device device = new Device();
				device.setStatus(1);	//在线
				List<Device> devices = deviceService.queryList(device);
					for(Device d : devices){
						if(d.getOnlineTime() == null)
							continue;
						String str1 = DateUtils.formatString(new Date(), "yyyy-MM-dd HH:mm:ss");
						Date tempDate = DateUtils.addSecond(d.getOnlineTime(), 30);	
						String str2 = DateUtils.formatString(tempDate, "yyyy-MM-dd HH:mm:ss");
						logger.info("---设备信息---"+d+"\n 当前时刻="+str1+"\n 最后心跳时间="+str2);
						if(DateUtils.compareDate(str1, str2)){
							//设备下线
							offlineDevice(d.getDeviceNo());
							
							Socket socket = ccidSocket.get(d.getDeviceNo());
							
							//清除redis数据
							flushRedis(socket);
							
							if(socket !=null && !socket.isClosed()){
								try {
									logger.info("["+d.getDeviceNo()+"] 设备关闭socket---"+socket.getRemoteSocketAddress());
									socket.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}/**/
							
						}
				}
            }  
        };  
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();  
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间  
        service.scheduleAtFixedRate(runnable, 5, 65, TimeUnit.SECONDS);  
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
		// System.out.println("---b---"+b);
		// System.out.println("*R1,001,0000000000,898602b6111700445060,864811034682927,1.0,1.0,0.1#\0");
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
					logger.error(s+"-------------设备超时断开---------------");
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
						logger.error(s+"-------------设备异常断开---------------" + e.getMessage());
						e.printStackTrace();
						overThis();
					}
				}
			}
			System.err.println("running=" + running + "\trun =" + run);
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
				// Socket clientSocket =
				// ipSocket.get(toClientIP+":"+toClientPort);
				Socket clientSocket = ccidSocket.get(ccid);
				logger.info("------【向" + ccid + " 发送消息，获取socket对象】--->>>" + clientSocket + " ---消息为：>>>" + toMessage);
				if(clientSocket == null) 
					return false;
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
			// s.setKeepAlive(true);// 设置长连接
//			InputStream is = s.getInputStream();
//			if (is.available() > 0) {
//				// ipMapping.put(clientIP+":", s);// 以k-v保存ip对应的socket对象
//				logger.debug("---开始接收消息---is.available()---" + is.available() + "---is.read()---" + is.read() + " --- "
//						+ s.toString());
//				int length = 0;
//				byte[] buffer = new byte[1024];
//				while (-1 != (length = is.read(buffer, 0, 1024))) {
//					String reciverMsg = "";
//					reciverMsg += new String(buffer, 0, length);
//					// TODO 处理接收到的消息，解析报文
//					System.err.println("---------------reciverMsg-------"+reciverMsg);
//					resolveMessage(clientIP, clientPort, reciverMsg.trim());
//				}
//			} else {
//				Thread.sleep(10);
//			}
			
			InputStream is = s.getInputStream();
			while (true) {
                byte[] b = new byte[1024];
                int r = is.read(b);
                if(r>-1){
                    String reciverMsg = new String(b);
                    System.err.println("---------------reciverMsg-------"+reciverMsg);
                    resolveMessage(clientIP, clientPort, reciverMsg.trim());
                }
            }
			
			
		}

		// 解析报文
		public void resolveMessage(String ip, int clientPort, String reciverMsg) throws IOException {
			String regEx = "^\\*.*#$";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(reciverMsg);
			boolean b = m.find();
			logger.info(
					"---【解析报文[" + reciverMsg + "]，匹配以*开头，以#结尾，结果为】---" + b + "\n ip:port = " + ip + ":" + clientPort);
			if (b) {
				String[] requestBodys = reciverMsg.substring(reciverMsg.indexOf("*") + 1, reciverMsg.length() - 1)
						.split(",");
				for (String key : requestBodys) {
					System.err.print(key + ",");
					if ("R1".equalsIgnoreCase(key)) { // 注册命令
						String token = get(ip + ":" + clientPort);
						String snk = "001";
						if ("".equals(token) || null == token) {
							// 生成token，并且保存到redis
							token = "R" + new Date().getTime();
							logger.info("---token为空，创建token--->>>" + token);
							set(ip + ":" + clientPort, token); // ip-token
						}

						// 新增或者更新设备
						Device device = new Device();
						device.setDeviceToken(token);
						device.setOnlineTime(new Date());
						device.setDeviceNo(requestBodys[3]);
						device.setStatus(1);
						device.setLastUpdate(new Date());
						device.setCreateTime(new Date());
						deviceService.saveOrUpdate(device);
						 set(token, requestBodys[3]);
						 set(requestBodys[3], ip+":"+clientPort);
						ipSocket.put(ip + ":" + clientPort, s);
						ccidSocket.put(requestBodys[3], s);
						// 响应客户端消息
						String send2ClientMsg = "*" + key + "," + snk + "," + token + "#";
						logger.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+ "------ 响应客户端R1消息内容------" + send2ClientMsg);
						responseByOutputStream(send2ClientMsg);

					} else if ("H0".equalsIgnoreCase(key)) { // H0，心跳消息
						// 【解析报文[*H0,001,R1497108915104,031,0,0#]，匹配以*开头，以#结尾，结果为】---true
						String token = requestBodys[2];
						Device device = deviceService.queryDeviceByToken(token);
						device.setOnlineTime(new Date());
						device.setLastUpdate(new Date());
						if(device.getStatus() != 3){
							device.setStatus(1);
						}
						deviceService.updateSelective(device);
						logger.info("---H0命令更新设备token：【"+token+"】---CCID：【"+device.getDeviceNo()+"】的最后心跳时间为："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
					}
				}
			}
		}

		/**
		 * 响应消息(OutputStream)
		 */
		private void responseByOutputStream(String sendMsg) throws IOException {
			OutputStream os = s.getOutputStream();
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
					logger.error("socket关闭失败，失败原因：" + e.getMessage());
					throw new ChairException("-1", "系统错误");
				}
			}
			//清除redis
			String deviceNO = flushRedis(s);
			//下线设备
			offlineDevice(deviceNO);
			logger.info("-----关闭socket-----：" + s.getRemoteSocketAddress());
		}
		

	}
	
	
	/**
	 * 清除redis数据
	 */
	private String flushRedis(Socket s){
		if(s == null) return "";
		String clientIP = s.getInetAddress().toString().replace("/", "");
		int clientPort = s.getPort();
		String token = get(clientIP + ":" + clientPort);
		String ccid = get(token);
		del(clientIP + ":" + clientPort);
		del(token);
		del(ccid);
		return ccid;
	}
	
	
	
	/**
	 * 改变设备状态（下线设备）
	 */
	private void offlineDevice(String deviceNO){
		logger.info("---["+deviceNO+"] 设备下线---");
		Device device = new Device();
		device.setDeviceNo(deviceNO);
		device.setStatus(2); // 设备不在线
		device.setLastUpdate(new Date());
//		deviceService.updateSelective(device);
		deviceService.updateByDeviceNO(device);
	}
	
	private void set(String key, String value) {
		logger.info("------【保存redis.set()】------key：" + key + " \t value：" + value);
		jedisCluster.set(key, value);
	}

	private String get(String key) {
		logger.info("------【查询redis.get()】------key：" + key);
		String res = jedisCluster.get(key);
		return res;
	}

	private void del(String key) {
		logger.info("------【删除redis.del()】------key：" + key);
		if(!StringUtils.isEmpty(key))
			jedisCluster.del(key);
	}
	
	/**
	 * 将socket和设备的关系打印出来
	 */
	private void printSokcet(){
		logger.info("--------socket.map一共有几条数据?----------"+socketCCID.size());
		for (Map.Entry<Socket,String> entry : socketCCID.entrySet()){
			logger.info("---【"+entry.getKey()+"】---【"+entry.getValue()+"】---");
		}
	}
	

}
