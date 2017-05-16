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
import com.chair.manager.pojo.Factory;
import com.chair.manager.service.FactoryService;

@RequestMapping("/factory")
@Controller
public class FactoryController {
	private Logger logger = Logger.getLogger(FactoryController.class);

	@Autowired
	private FactoryService factoryService;

	/**
	 * 查询厂家列表（管理台前端）
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "list", method = RequestMethod.POST)
	private List<Factory> queryFactoryList() {
		logger.info("------【查询工厂列表】------");
		List<Factory> factorys = factoryService.queryList(null);
		return factorys;
	}
	
	
	/**
	 * 查询厂家列表，分页（管理台前端）
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="listForPage",method=RequestMethod.POST)
	private EasyUIResult queryFactoryListForPage(@RequestParam("page") Integer page, @RequestParam("rows") Integer rows){
		return factoryService.queryFactoryListForPage(page, rows);
	}
	
	
	/**
	 * 新增厂家（管理台前端）
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="save",method=RequestMethod.POST)
	private EasyUIResult addFactory(Factory factory){
		logger.info("------【新增厂家】参数------"+factory);
		factory.setCreateTime(new Date());
		factory.setLastUpdate(new Date());
		int saveRs = factoryService.save(factory);
		if(saveRs <= 0) throw new ChairException("-2", getClass()+",SQL操作失败。"); 
		return new EasyUIResult();
	}
	
	
	/**
	 * 批量删除厂家（管理台前端）
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="batDel",method=RequestMethod.POST)
	private Integer delDevice(@RequestParam("ids") Integer[] ids){
		logger.info("---将要删除的厂家ids--->>>"+ids);
		return factoryService.deleteByIds(ids);
	}
	
}
