package com.chair.manager.controller;

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
import com.chair.manager.service.FactoryProxyService;

@RequestMapping("/proxy")
@Controller
public class FactoryProxyController {
	private Logger logger = Logger.getLogger(FactoryProxyController.class);

	@Autowired
	private FactoryProxyService factoryProxyService;

	/**
	 * 查询搭理列表（管理台前端）
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
	private EasyUIResult addProxy(FactoryProxy proxy){
		logger.info("------【新增代理】参数------"+proxy);
		proxy.setCreateTime(new Date());
		proxy.setLastUpdate(new Date());
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
	
	
}
