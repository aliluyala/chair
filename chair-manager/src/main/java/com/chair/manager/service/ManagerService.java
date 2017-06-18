package com.chair.manager.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chair.manager.bean.EasyUIResult;
import com.chair.manager.mapper.StatiscticsMapper;
import com.chair.manager.pojo.Manager;
import com.chair.manager.pojo.Statisctics;
import com.github.pagehelper.PageInfo;

@Service
public class ManagerService extends BaseService<Manager> {
	private static Logger LOGGER = Logger.getLogger(ManagerService.class);
	
	@Autowired
	private StatiscticsMapper statiscticsMapper;
	
	
	/**
	 * 用户登陆业务
	 * @param manager
	 * @return
	 */
	public Manager login(Manager manager){
		//manager.setPassword(getMd5(manager.getPassword()));
		manager.setPassword(manager.getPassword());
		List<Manager> result= super.queryList(manager);
		if(result!=null&&result.size()>0){
			LOGGER.info("user="+manager.getUser()+"登录成功");
			return result.get(0);
		}
		return null;
	}

	
	/**
	 * 获取MD5加密串
	 * @param plainText
	 * @return
	 */
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

	/**
	 * 查询管理员分页
	 * @param manager
	 * @param page
	 * @param rows
	 * @return
	 */
	public EasyUIResult queryListForPage(Manager manager, Integer page, Integer rows) {
		PageInfo<Manager> pageInfo= super.queryListPage(manager, page, rows);
		return new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
	}

	/**
	 * 查询代理下的统计数据
	 * @param m
	 * @return
	 */
	public List<Statisctics> queryProxyStatisctics(Manager m) {
		return statiscticsMapper.queryProxyStatisctics(m);
	}
	
	/**
	 * 查询代理基本数据统计
	 * @param m
	 * @return
	 */
	public Statisctics  queryBaseStatisctics(Manager m){
		return statiscticsMapper.queryBaseStatisctics(m);
	}
	
	/**
	 * 查询商家下的统计数据
	 * @param m
	 * @return
	 */
	public List<Statisctics> queryShopStatisctics(Manager m) {
		return statiscticsMapper.queryShopStatisctics(m);
	}

	/**
	 * 查询商家基本数据统计
	 * @param m
	 * @return
	 */
	public Statisctics queryShopBaseStatisctics(Manager m) {
		return statiscticsMapper.queryShopBaseStatisctics(m);
	}

	
	/**
	 * 
	 * @param m
	 * @return
	 */
	public Statisctics queryDayIncomeForProxy(Manager m) {
		return statiscticsMapper.queryDayIncomeForProxy(m);
	}
	

}
