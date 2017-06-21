package com.chair.manager.keepalive;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import com.chair.manager.controller.MyVector;
import com.chair.manager.exception.ChairException;
import com.chair.manager.pojo.ConsumedDetails;
import com.chair.manager.pojo.Device;
import com.chair.manager.pojo.DeviceCommandLog;
import com.chair.manager.pojo.DeviceLog;
import com.chair.manager.pojo.UserAccount;
import com.chair.manager.pojo.dto.TempDto;
import com.chair.manager.service.ConsumedDetailsService;
import com.chair.manager.service.DeviceCommandLogService;
import com.chair.manager.service.DeviceLogService;
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
	private static ConcurrentHashMap<Thread, Socket> socketThread;
	private ConcurrentHashMap<String, String> ipToken = new ConcurrentHashMap<String, String>();
	private Thread connWatchDog;
	@Autowired
	private JedisCluster jedisCluster;
	@Autowired
	private DeviceService deviceService;

	@Autowired
	private DeviceLogService deviceLogService;
	
	@Autowired
	private DeviceCommandLogService deviceCommandLogService;
	
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private  ConsumedDetailsService consumedDetailsService;
	

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
		if (socketThread == null)
			socketThread = new ConcurrentHashMap<Thread, Socket>();

		// 定时任务1：更新“在线”并且“当前时间>最后心跳时间”的设备状态
		quartzJob();
		
		//定时任务2：更新“正在使用”并且 “当前时间>消费结束时间”的设备状态
		updateDeviceStatusJob();
		
		//定时任务3：清除与当前设备不绑定的socket
//		clearSocketThreadJob();
	}
	
	private void clearSocketThreadJob(){
		Runnable runnable = new Runnable() {  
            public void run() {  
            	//打印socket,thread列表
				printSokcetMap();
            	logger.warn("###");
            	logger.warn("###");
            	logger.warn("###");
            	clearSocket();
            	logger.warn("---------------------------黄金分割线-----------------------");
            	clearThread();
            }  
        };  
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();  
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间  
        service.scheduleAtFixedRate(runnable, 5, 5, TimeUnit.MINUTES); 
	}
	

	private void quartzJob() {
		Runnable runnable = new Runnable() {  
            public void run() {  
                // task to run goes here  
				logger.info("--------定时任务执行时间-Hello !!!------->>>>"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				
				//查询所有设备列表
				Device device = new Device();
				device.setStatus(1);	//在线
				List<Device> devices = deviceService.queryList(device);
					for(Device d : devices){
						if(d.getOnlineTime() == null)
							continue;
						String str1 = DateUtils.formatString(new Date(), "yyyy-MM-dd HH:mm:ss");
						Date tempDate = DateUtils.addSecond(d.getOnlineTime(), 65);	
						String str2 = DateUtils.formatString(tempDate, "yyyy-MM-dd HH:mm:ss");
						logger.info("---设备信息---"+d.getDeviceNo()+"\t 当前时刻="+str1+"\t 最后心跳时间="+str2);
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
        service.scheduleAtFixedRate(runnable, 5, 20, TimeUnit.SECONDS);  
	}
	
	//更新正在使用并且当前时间>消费结束时间的设备状态
	private void updateDeviceStatusJob(){
		Runnable runnable = new Runnable() {  
            public void run() {  
				logger.info("--------检视设备状态定时任务----------->>>>"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				deviceService.updateUsingDeviceStatus();
            }  
        };  
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();  
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间  
        service.scheduleAtFixedRate(runnable, 30, 10, TimeUnit.SECONDS); 
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
		/*int port = Constant.PORT;
		System.out.println("----服务器启动--端口---" + port);
		Server server = new Server(port);
		server.start();

		String regEx = "^\\*.*#$";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher("*R1,001,0000000000,898602b6111700445060,864811034682927,1.0,1.0,0.1#\0");
		boolean b = m.find();
		// System.out.println("---b---"+b);
		// System.out.println("*R1,001,0000000000,898602b6111700445060,864811034682927,1.0,1.0,0.1#\0");*/
		/*for(int i=0; i<10; i++){
			MyVector myVector  = MyVector.getInstance();
			Vector v = myVector.getVector();
			System.out.println("myVector："+myVector.hashCode() + "\t Vector："+v.hashCode());
		}*/
/*		List<String> l = new LinkedList<String>();
		l.add("A");
		l.add("B");
		l.add("C");
		l.add("D");
		System.out.println("--linded长度--"+l.size());
		for (String string : l) {
			if("B".equals(string)){
				l.remove(string);
			}
			System.out.println(string);
		}
		System.out.println("--linded长度--"+l.size());*/
		

		List<String> list = new ArrayList<String>();
		list.add("A");
		list.add("B");
		list.add("C");
		list.add("D");
		list.add("E");
		list.add("F");
		for(int i = 0; i<list.size() ; i++){
			System.out.println("---list---"+list.size());
			if("B".equals(list.get(i))){
				list.remove(list.get(i));
			}
			System.out.println(list.get(i));
		}
		
		
		
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

	List<Socket> socketList = new ArrayList<Socket>();
	List<Thread> threadList = new ArrayList<Thread>();
	
	public class ConnWatchDog implements Runnable {

		public void run() {
			try {
				ServerSocket ss = new ServerSocket(port);
				while (running) {
					Socket s = ss.accept();
					
					//将socket添加到list
//					socketList.add(s);
//					printSocketList();
					
					
					Thread  t = new Thread(new SocketAction(s));
					t.start();

					//将thread添加到list
//					threadList.add(t);
//					printThreadList();

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
					} catch (Exception e) {
						logger.error(s+"-------------设备异常断开---------------" + e.getMessage());
						overThis();
						e.printStackTrace();
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
		public boolean send( DeviceCommandLogService deviceCommandLogService, String ccid, String toMessage) {
			try {
				
				Socket clientSocket = ccidSocket.get(ccid);
				logger.info("------【向" + ccid + " 发送消息，获取socket对象】--->>>" + clientSocket + " ---消息为：>>>" + toMessage);
				if(clientSocket == null) 
					return false;
				
				OutputStream os = clientSocket.getOutputStream();
				byte[] b = toMessage.getBytes();
				os.write(b);
				os.flush();

				//跟踪设备命令详情
				if(deviceCommandLogService != null){
					DeviceCommandLog deviceCommandLog = new DeviceCommandLog();
					deviceCommandLog.setDeviceNo(ccid);
					deviceCommandLog.setCommandType(2);	//1.设备上报命令 2.设备下发命令
					deviceCommandLog.setCommandDesc(toMessage);
					deviceCommandLog.setCreateTime(new Date());
					deviceCommandLog.setLastUpdate(new Date());
					
					deviceCommandLogService.save(deviceCommandLog);	
				}
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
			InputStream is = s.getInputStream();
			while (true) {
                byte[] b = new byte[1024];
                int r = is.read(b);
                if(r>-1){
                    String reciverMsg = new String(b);
                    System.out.println("---------------接收来自客户端消息：>>>----------"+reciverMsg);
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
			logger.info("\t当前socket："+s+"\t当前线程为："+Thread.currentThread()+"---【解析报文[" + reciverMsg + "]，匹配以*开头，以#结尾，结果为】---" + b );
			if (b) {
				String[] requestBodys = reciverMsg.substring(reciverMsg.indexOf("*") + 1, reciverMsg.length() - 1).split(",");
				//设备上报/下发
				for (String key : requestBodys) {
					System.err.print(key + ",");
					if ("R1".equalsIgnoreCase(key)) { // 正在注册
						String token = requestBodys[2];
						if ("00000000000000".equals(token) || null == token || "".equals(token.trim())) {
							// 生成token，并且保存到redis
							token = "R" + new Date().getTime();
							//logger.info("---token为空，创建token--->>>" + token);
						}
						
						//跟踪设备命令详情
						recordCommand(requestBodys[3], 1, reciverMsg);
						
						String snk= "001";
						Device d = new Device();
						d.setDeviceNo(requestBodys[3]);
						
						Device device = deviceService.queryByDeviceNO(d);
						if(device == null){
							device = new Device();
							device.setDeviceToken(token);
							device.setDeviceNo(requestBodys[3]);
							device.setStatus(2);
							device.setLastUpdate(new Date());
							device.setCreateTime(new Date());
							deviceService.save(device);
							//设备日志跟踪
							recordDeviceLog(requestBodys[3], 2, "下线");
						}else{
							device.setDeviceToken(token);
							device.setDeviceNo(requestBodys[3]);
							device.setLastUpdate(new Date());
							deviceService.updateSelective(device);
						}
						
						// 响应客户端消息
						String send2ClientMsg = "*" + key + "," + snk + "," + token + "#";
//						logger.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+ "------ 【正在注册】响应客户端R1消息内容------" + send2ClientMsg);
						logger.info("【正在注册】响应客户端R1消息内容"+send2ClientMsg);

						//跟踪设备命令详情
						recordCommand(requestBodys[3], 2, send2ClientMsg);
						responseByOutputStream(send2ClientMsg);
					} else if("R2".equalsIgnoreCase(key)){	//注册成功
						String token = requestBodys[2];
						if ("00000000000000".equals(token) || null == token || "".equals(token.trim())) {
							logger.error("--------------第三次握手，得到的token为空--------------");
							return;
							//throw new ChairException("-10", "第三次握手，得到的token为空");
						}
						
						//根据设备号查询设备信息
						Device d = new Device();
						d.setDeviceNo(requestBodys[3]);
						Device device = deviceService.queryByDeviceNO(d);
						
						if(device == null){
							logger.error("--------------第三次握手，得到的设备号【"+requestBodys[3]+"】查询不到设备。--------------");
							return;
							//throw new ChairException("-10", "第三次握手，得到的设备号查询不到设备");
						}
						
						if(!device.getDeviceToken().equals(token)){
							logger.error("--------------第三次握手，得到的token【"+token+"】与发送给客户端的token不一致，校验不通过。--------------");
							return;
						//	throw new ChairException("-10", "第三次握手，得到的token校验不通过");
						}
						device.setStatus(1);
						device.setOnlineTime(new Date());
						device.setLastUpdate(new Date());
						deviceService.updateSelective(device);
						
						//设备日志跟踪
						recordDeviceLog(requestBodys[3], 1, "在线");
						//跟踪设备命令详情
						recordCommand(requestBodys[3], 1, reciverMsg);
						

						set(ip + ":" + clientPort, token); // ip-token
						set(token, requestBodys[3]);
						set(requestBodys[3], ip+":"+clientPort);

						ccidSocket.put(requestBodys[3], s);
						socketCCID.put(s, requestBodys[3]);
						socketThread.put(Thread.currentThread(), s);
						
						//清除多余的socket
						//clearSocket();
						
						logger.info("【注册成功】");
					} else if ("H0".equalsIgnoreCase(key)) { // H0，心跳消息
						// 【解析报文[*H0,001,R1497108915104,031,0,0#]，匹配以*开头，以#结尾，结果为】---true
						String token = requestBodys[2];
						Device device = deviceService.queryDeviceByToken(token);
						device.setOnlineTime(new Date());
						device.setLastUpdate(new Date());
						String str1 = DateUtils.formatString(new Date(), "yyyy-MM-dd HH:mm:ss");
						String str2 = device.getExpTime();
						//当前设备状态 != 3 或者  设备状态==3并且当前时间 > 消费结束时间
						if(device.getStatus() != 3 || (device.getStatus() == 3 &&  DateUtils.compareDate(str1, str2))){
							device.setStatus(1);
							//设备日志跟踪
							recordDeviceLog(device.getDeviceNo(), 1, "在线");
						}
						deviceService.updateSelective(device);
						
						logger.info("---H0命令更新设备token：【"+token+"】---CCID：【"+device.getDeviceNo()+"】的最后心跳时间<<<---");
					}else if("T1".equalsIgnoreCase(key) || "T2".equalsIgnoreCase(key)){	//设备启动成功
						Device device = deviceService.queryDeviceByToken(requestBodys[2]);
						Map<String, TempDto> map = MyVector.getMap();
						TempDto dto = map.get(device.getDeviceNo());
						if(userAccountService == null){
							userAccountService = new UserAccountService();
						}
						
						//设备日志跟踪
						recordDeviceLog(device.getDeviceNo(), 3, "正在使用");
						//跟踪设备命令详情
						recordCommand(device.getDeviceNo(), 1, reciverMsg); 

						//更新消费明细状态
						dto.getConsumerID();
						ConsumedDetails consumedDetails = new ConsumedDetails();
						consumedDetails.setId(dto.getConsumerID());
						consumedDetails.setStatus(2);	//消费成功
						consumedDetails.setLastUpdate(new Date());
						consumedDetailsService.updateSelective(consumedDetails);
						
						//获取消费时间
						int duration = consumedDetailsService.findById(dto.getConsumerID()).getConsumedDuration();
						logger.info("---根据消费明细ID："+dto.getConsumerID()+"获取消费时长为："+duration);
						Date date = DateUtils.addMinute(new Date(), duration);
						date = DateUtils.addSecond(date, 15);
						String expTime = "";
						try {
							expTime = DateUtils.parseToFormatString(date, "yyyy-MM-dd HH:mm:ss");
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						// 更新账户信息
						UserAccount userAccount = userAccountService.findById(dto.getAccountID());
						userAccount.setUsedDuration(userAccount.getUsedDuration() + duration);
						userAccount.setRestDuration(userAccount.getRestDuration() - duration);
						userAccount.setLastUpdate(new Date());
						userAccountService.updateSelective(userAccount);

						//更改设备状态 // 将过期时间写入设备表
						device.setStatus(3);
						device.setExpTime(expTime);	//设置为正在使用
						device.setLastUpdate(new Date());
						deviceService.updateSelective(device);

						//删除map的对象
						map.remove(device.getDeviceNo());
						
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

		ccidSocket.remove(ccid);
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
		deviceService.updateByDeviceNO(device);
		
		//设备日志跟踪
		recordDeviceLog(deviceNO, 2, "下线");
//		recordCommand("123456",1,"test");
	}
	
	
	private void recordDeviceLog(String deviceNO, int status, String statusDesc){
		logger.info("设备日志跟踪------deviceNO："+deviceNO+"\tstatus："+status+"\tstatusDesc："+statusDesc);
		DeviceLog deviceLog = new DeviceLog();
		deviceLog.setDeviceNo(deviceNO);
		deviceLog.setDeviceStatus(status);	//1、2、3
		deviceLog.setDeviceStatusDesc(statusDesc);//在线、下线、正在使用
		deviceLog.setCreateTime(new Date());
		deviceLog.setLastUpdate(new Date());
		deviceLogService.save(deviceLog);
	}
	
	
	private void recordCommand(String deviceNO, int type, String commad){
		logger.info("设备命令跟踪------deviceNO："+deviceNO+"\ttype："+type+"\tcommad："+commad);
		DeviceCommandLog deviceCommandLog = new DeviceCommandLog();
		deviceCommandLog.setDeviceNo(deviceNO);
		deviceCommandLog.setCommandType(type);	//1.设备上报命令 2.设备下发命令
		deviceCommandLog.setCommandDesc(commad);
		deviceCommandLog.setCreateTime(new Date());
		deviceCommandLog.setLastUpdate(new Date());
		
		deviceCommandLogService.save(deviceCommandLog);
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
	private void printSokcetMap(){
		logger.warn("--------Socket.map一共有几条数据?----------"+ccidSocket.size());
		for (Map.Entry<String,Socket> entry : ccidSocket.entrySet()){
			logger.warn("---socket----【"+entry.getKey()+"】---【"+entry.getValue()+"】---");
		}

		logger.warn("--------Thread.map一共有几条数据?----------"+socketThread.size());
		for (Entry<Thread, Socket> entry : socketThread.entrySet()){
			logger.warn("---thread----【"+entry.getKey()+"】---【"+entry.getValue()+"】---");
		}
	}
	
	
	private void printSocketList(){
		logger.warn("S--------socketList一共有几条数据?----------"+socketList.size());
		for (Socket socket : socketList) {
			logger.warn("通过socket查询是否有值？"+socketCCID.get(socket)+"---【socket】---"+socket+"  isConnection()--?"+isConnection(socket)+"  isClosed()--?"+socket.isClosed());
		}
	}
	
	
	private void printThreadList(){
		logger.warn("T--------threadList一共有几条数据?----------"+threadList.size());
		for (Thread thread : threadList) {
			logger.warn("通过thread查询是否有值？"+socketThread.get(thread)+"----thread-----"+thread+" - isAlive ? "+thread.isAlive()+" - isInterrupted ? "+ thread.isInterrupted());
		}
	}
	
	private void clearSocket(){
		logger.warn("----清除与当前设备不绑定的socket----清除前socket数量为："+socketList.size());
		if(socketList.size() <= 0 ){
			return;
		}
		for (int i =0; i < socketList.size(); i++) {
			Socket socket = socketList.get(i);
			if(!StringUtils.isEmpty(socketCCID.get(socket)) && socket.isConnected()){
				continue;
			}
			if(socket != null){
				try {
					logger.warn("---关闭的socket为---"+socket);
					socket.close();
					//删除List数组对应的值
					socketList.remove(socketList.get(i));
				} catch (IOException e) {
					logger.error("---cleanSocketThread---失败--->>>"+e.getMessage());
				}
			}
		}
		logger.warn("----清除与当前设备不绑定的socket----清除后socket数量为："+socketList.size());
	}
	
	private void clearThread(){
		logger.warn("----清除与当前设备不绑定的thread----清除前thread数量为："+threadList.size());
		if(threadList.size() <= 0 ){
			return;
		}
		for (int i =0; i < threadList.size(); i++) {
			Thread thread = threadList.get(i);
			if(socketThread.get(thread) != null && thread.isAlive()){
				continue;
			}
			if(thread != null){
				//TODO 关闭线程
				logger.warn("---关闭的thread为---"+thread);
				thread.interrupt();
				//删除List数组对应的值
				threadList.remove(thread);
			}
		}
		logger.warn("----清除与当前设备不绑定的thread----清除后thread数量为："+threadList.size());
	}
	
	
	//判断是否断开连接，断开返回false,没有返回true
	private boolean isConnection(Socket s){
		try {
			if(s == null) return false;
			s.sendUrgentData(0xFF);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
}
