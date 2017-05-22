package com.chair.manager.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.chair.manager.pojo.Manager;

@Service
public class ManagerService extends BaseService<Manager> {
	private static Logger LOGGER = Logger.getLogger(ManagerService.class);
	public Manager login(Manager manager){
		manager.setPassword(getMd5(manager.getPassword()));
		List<Manager> result= super.queryList(manager);
		if(result!=null&&result.size()>0){
			LOGGER.info("user="+manager.getUser()+"登录成功");
			return result.get(0);
		}
		return null;
	}

	public  String getMd5(String plainText) {  
		try {  
			MessageDigest md = MessageDigest.getInstance("MD5");  
			md.update(plainText.getBytes());  
			byte b[] = md.digest();  

			int i;  

			StringBuffer buf = new StringBuffer("");  
			for (int offset = 0; offset < b.length; offset++) {  
				i = b[offset];  
				if (i < 0)  
					i += 256;  
				if (i < 16)  
					buf.append("0");  
				buf.append(Integer.toHexString(i));  
			}  
			return buf.toString();  
		} catch (NoSuchAlgorithmException e) {  
			e.printStackTrace();  
			return null;  
		}  

	}  


}
