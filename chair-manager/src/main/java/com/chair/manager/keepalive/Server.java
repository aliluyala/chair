package com.chair.manager.keepalive;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

	private int port;
	private volatile boolean running = false;
	private long receiveTimeDelay = 30000;
	private ConcurrentHashMap<Class, ServerObjectAction> actionMapping = new ConcurrentHashMap<Class, ServerObjectAction>();
	private Thread connWatchDog;

	/**
	 * 构造函数
	 * 
	 * @param port
	 */
	public Server(int port) {
		this.port = port;
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

	public static void main(String[] args) {
		System.out.println("----服务器启动----");
		int port = 11111;
		Server server = new Server(port);
		server.start();
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

	class ConnWatchDog implements Runnable {
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

	class SocketAction implements Runnable {
		Socket s;
		boolean run = true;
		long lastReceiveTime = System.currentTimeMillis();

		public SocketAction(Socket s) {
			this.s = s;
		}

		public void run() {
			while (running && run) {
				if (System.currentTimeMillis() - lastReceiveTime > receiveTimeDelay) {
					overThis();
				} else {
					try {
						String clientIP = s.getInetAddress().toString().replace("/", "");
						System.out.println("====客户端socket.getInetAddress()=====" + clientIP);
						s.setKeepAlive(true);// 设置长连接
						DataInputStream dis = new DataInputStream(s.getInputStream());
						System.out.println("服务器端接受请求");
						String reciverMsg = dis.readUTF();
						System.out.println("------------来自客户端消息----:" + reciverMsg);
						/*
						 * InputStream in = s.getInputStream();
						 * 
						 * if (in.available() > 0) { ObjectInputStream ois = new
						 * ObjectInputStream(in); Object obj = ois.readObject();
						 * lastReceiveTime = System.currentTimeMillis();
						 * //接收客户端消息 ServerObjectAction oa =
						 * actionMapping.get(obj.getClass()); //
						 * System.out.println("oa---"+oa+"接收：\t" + obj);
						 * s.setKeepAlive(true);
						 * System.out.println(s.getKeepAlive()+"接收：\t" + obj);
						 * oa = oa == null ? new ServerDefaultObjectAction() :
						 * oa; addActionMap(obj.getClass(),oa); //打印客户端详情 //
						 * showClientInfo();
						 * 
						 * 
						 * Object out = oa.doAction(obj); //响应客户端消息 if (out !=
						 * null) { ObjectOutputStream oos = new
						 * ObjectOutputStream(s.getOutputStream());
						 * oos.writeObject(out+"-aaa-"); oos.flush(); }
						 */
						/*
						 * } else { Thread.sleep(1000); }
						 */
					} catch (Exception e) {
						e.printStackTrace();
						overThis();
					}
				}
			}
		}

		private void overThis() {
			if (run)
				run = false;
			if (s != null) {
				try {
					s.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			System.out.println("关闭：" + s.getRemoteSocketAddress());
		}

	}

	private void showClientInfo() {
		System.out.println("---打印客户端信息---");
		// Enumeration<Class> enums = actionMapping.keys();
		// Class cls = enums.nextElement();
		// while(cls != null){
		// System.err.println("---enum ---"+cls);
		// cls = enums.nextElement();
		// }
		Set<Class> keys = actionMapping.keySet();
		for (Class key : keys) {
			System.err.println(key + "---" + actionMapping.get(key));
		}
	}

}
