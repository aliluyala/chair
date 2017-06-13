package com.chair.manager.controller;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chair.manager.bean.EasyUIResult;
import com.chair.manager.exception.ChairException;
import com.chair.manager.pojo.FactoryProxy;
import com.chair.manager.pojo.Manager;
import com.chair.manager.service.FactoryProxyService;
import com.chair.manager.service.ManagerService;

import sun.misc.BASE64Encoder;

@RequestMapping("/proxy")
@Controller
public class FactoryProxyController {
	private Logger logger = Logger.getLogger(FactoryProxyController.class);

	@Autowired
	private FactoryProxyService factoryProxyService;
	
	@Autowired
	private ManagerService managerService;
	

	/**
	 * 查询代理列表（管理台前端）
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "list", method = RequestMethod.POST)
	private List<FactoryProxy> queryFactoryList() {
		logger.info("------【查询代理列表】------");
		List<FactoryProxy> proxys = factoryProxyService.queryList(null);
		return proxys;
	}
	
	
	/**
	 * 查询代理列表，分页（管理台前端）
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="listForPage",method=RequestMethod.POST)
	private EasyUIResult queryProxyListForPage(@RequestParam("page") Integer page, @RequestParam("rows") Integer rows){
		return factoryProxyService.queryProxyListForPage(page, rows);
	}
	
	
	/**
	 * 新增代理（管理台前端）
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="save",method=RequestMethod.POST)
	private EasyUIResult addProxy(FactoryProxy proxy, @RequestParam("user") String user, @RequestParam("password") String password){
		logger.info("------【新增代理】参数------"+proxy);
		proxy.setCreateTime(new Date());
		proxy.setLastUpdate(new Date());
		
		//新增管理员
		Manager manager = new Manager();
		manager.setUser(user);
		//manager.setPassword(new ManagerService().getMd5(password));
		manager.setPassword(password);
		manager.setType(2);
		manager.setCreateTime(new Date());
		manager.setLastUpdate(new Date());
		managerService.save(manager);
		
		int saveRs = factoryProxyService.save(proxy);
		if(saveRs <= 0) throw new ChairException("-2", getClass()+",SQL操作失败。");
		
		
		
		return new EasyUIResult();
	}
	
	
	/**
	 * 批量删除代理（管理台前端）
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="batDel",method=RequestMethod.POST)
	private Integer delProxy(@RequestParam("ids") Integer[] ids){
		logger.info("---将要删除的代理ids--->>>"+ids);
		return factoryProxyService.deleteByIds(ids);
	}
	
	
	  /**利用MD5进行加密
     * @param str  待加密的字符串
     * @return  加密后的字符串
     * @throws NoSuchAlgorithmException  没有这种产生消息摘要的算法
     * @throws UnsupportedEncodingException  
     */
    public static String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        //确定计算方法
        MessageDigest md5=MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        md5.digest(str.getBytes("utf-8"));
        String newstr=base64en.encode(md5.digest(str.getBytes("utf-8")));
        return newstr;
    }
	
	
	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
//		System.out.println(EncoderByMd5("123456"));
//		new ManagerService().getMd5("123456");
		System.out.println(new ManagerService().getMd5("123456"));
	}
	
}
