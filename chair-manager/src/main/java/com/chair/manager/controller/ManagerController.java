package com.chair.manager.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chair.manager.bean.EasyUIResult;
import com.chair.manager.exception.ChairException;
import com.chair.manager.pojo.Factory;
import com.chair.manager.pojo.FactoryProxy;
import com.chair.manager.pojo.Manager;
import com.chair.manager.service.ManagerService;

/**
 * 管理员表，包含厂家，代理，商铺等信息
 * 
 * @author yaoyuming
 *
 */
@Controller
@RequestMapping("/manager")
public class ManagerController {
	private Logger logger = Logger.getLogger(ManagerController.class);

	@Autowired
	private ManagerService managerService;

	/**
	 * 用户登陆
	 * 
	 * @param manager
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	@ResponseBody
	public EasyUIResult login(Manager manager, HttpSession session) {
		System.err.println("------manager-----"+manager);
		Manager user = managerService.login(manager);
		EasyUIResult rs = new EasyUIResult(null,null,user.getType());
		if (user != null) {
			session.setAttribute("user", user);
		} else {
			rs.setStatus(404L);
		}
		return rs;
	}

	/**
	 * 用户登出
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "out", method = RequestMethod.GET)
	@ResponseBody
	public EasyUIResult out(HttpSession session) {
		session.removeAttribute("user");
		return new EasyUIResult();
	}

	/*------------------------------------------管理台页面，代理管理-------------------------------------------------*/
	/**
	 * 查询代理列表，分页（管理台前端）
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "listProxyForPage", method = RequestMethod.POST)
	private EasyUIResult queryProxyListForPage(@RequestParam("page") Integer page, @RequestParam("rows") Integer rows) {
		Manager manager = new Manager();
		manager.setType(2); // 代理
		return managerService.queryListForPage(manager, page, rows);
	}
	
	
	/**
	 * 新增代理/商家（管理台前端）
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="add",method=RequestMethod.POST)
	private EasyUIResult add(Manager manager){
		logger.info("------【新增代理/商家】参数------"+manager);
		if(manager.getType() == 2){
			Manager factoryManager = managerService.findById(manager.getFactoryId());
			if(factoryManager == null){
				throw new ChairException("2010", "根据【"+manager.getFactoryId()+"】查询不到厂家数据");
			}
			manager.setFactoryName(factoryManager.getFactoryName());
		}else if(manager.getType() == 3){
			Manager proxyManager = managerService.findById(manager.getProxyId());
			if(proxyManager == null){
				throw new ChairException("2010", "根据【"+manager.getProxyId()+"】查询不到代理数据");
			}
			manager.setProxyName(proxyManager.getProxyName());
		}
		//manager.setPassword(managerService.getMd5(manager.getPassword()));
		manager.setPassword(manager.getPassword());
//		manager.setType(2);	//代理
		manager.setCreateTime(new Date());
		manager.setLastUpdate(new Date());
		int saveRs = managerService.save(manager);
		if(saveRs <= 0) throw new ChairException("-2", getClass()+",SQL操作失败。");
		return new EasyUIResult();
	}
	
	/**
	 * 编辑代理/商家（管理台前端）
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="edit",method=RequestMethod.POST)
	private EasyUIResult edit(Manager manager){
		logger.info("------【编辑代理/商家】参数------"+manager);
		if(manager.getType() == 2){
			Manager factoryManager = managerService.findById(manager.getFactoryId());
			if(factoryManager == null){
				throw new ChairException("2010", "根据【"+manager.getFactoryId()+"】查询不到厂家数据");
			}
			manager.setFactoryName(factoryManager.getFactoryName());
		}else if(manager.getType() == 3){
			Manager proxyManager = managerService.findById(manager.getProxyId());
			if(proxyManager == null){
				throw new ChairException("2010", "根据【"+manager.getProxyId()+"】查询不到代理数据");
			}
			manager.setProxyName(proxyManager.getProxyName());
		}
		manager.setPassword(manager.getPassword());
		manager.setLastUpdate(new Date());
		int saveRs = managerService.updateSelective(manager);
		if(saveRs <= 0) throw new ChairException("-2", getClass()+",SQL操作失败。");
		return new EasyUIResult();
	}
	
	/**
	 * 批量删除代理/商家（管理台前端）
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="delete",method=RequestMethod.POST)
	private Integer del(@RequestParam("ids") Integer[] ids, @RequestParam("type") Integer type){
		logger.info("---将要删除的代理/商家ids--->>>"+ids);
		return managerService.deleteByIds(ids);
	}
	
	/**
	 * 查询商家列表，分页（管理台前端）
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "listShopForPage", method = RequestMethod.POST)
	private EasyUIResult queryShopListForPage(@RequestParam("page") Integer page, @RequestParam("rows") Integer rows) {
		Manager manager = new Manager();
		manager.setType(3); // 商家
		return managerService.queryListForPage(manager, page, rows);
	}
	
	/**
	 * 查询厂家列表（管理台前端）
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryFactoryList", method = RequestMethod.POST)
	private List<Manager> queryFactoryList() {
		Manager manager = new Manager();
		manager.setType(1); // 厂家
		List<Manager> factorys = managerService.queryList(manager);
		return factorys;
	}	
	
	/**
	 * 查询厂家列表（管理台前端）
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryProxyList", method = RequestMethod.POST)
	private List<Manager> queryProxyList(HttpSession session, @RequestParam("factoryID") Integer factoryID) {
		List<Manager> proxys = null;
		if(factoryID == 0){
			Manager manager = (Manager) session.getAttribute("user");
			Manager proxyManager = new Manager();
			proxyManager.setFactoryId(manager.getId());
			proxyManager.setType(2); // 代理
			proxys = managerService.queryList(proxyManager);
		}else{
			Manager proxyManager = new Manager();
			proxyManager.setFactoryId(factoryID);
			proxyManager.setType(2); // 代理
			proxys = managerService.queryList(proxyManager);
		}
		return proxys;
	}	
	
	/**
	 * 查询商家列表（管理台前端）
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryShopList", method = RequestMethod.POST)
	private List<Manager> queryShopList(@RequestParam("proxyID") Integer proxyID) {
		Manager manager = new Manager();
		if(proxyID == null || proxyID != 0)
			manager.setProxyId(proxyID);
		manager.setType(3); // 商家
		List<Manager> shops = managerService.queryList(manager);
		return shops;
	}
	
	/**
	 * 根据paramID查询厂家、代理或商家对象
	 * @param paramID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryManager", method = RequestMethod.GET)
	private Manager queryManager(@RequestParam("paramID") Integer paramID){
		System.err.println("------paramID---------"+paramID);
		return managerService.findById(paramID);
	}

}
