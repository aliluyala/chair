package com.chair.manager.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chair.manager.pojo.FactoryProxy;
import com.chair.manager.service.FactoryProxyService;

@RequestMapping("/proxy")
@Controller
public class FactoryProxyController {
	private Logger logger = Logger.getLogger(FactoryProxyController.class);

	@Autowired
	private FactoryProxyService factoryProxyService;

	/**
	 * 查询厂家列表（管理台前端）
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
}
