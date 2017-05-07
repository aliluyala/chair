package com.chair.manager.keepalive;


import org.apache.log4j.Logger;


/**
 * 长链接数据常量
 * @author yaoym
 *
 */
public class Constant {
	private static Logger logger = Logger.getLogger(Constant.class);
	//长连接超时时间单位：秒
	public static final long RECEIVE_TIME_DELAY = 3 * 60 * 1000;
	//端口
	public static final int PORT = 11005;
	
	
	public static void main(String[] args) {
		while(true){
			logger.debug("-----This's debug-----");
			logger.info("-----This's info-----");
			logger.warn("-----This's warn-----");
			logger.error("-----This's error-----");
			try {
				Thread.sleep(10*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
