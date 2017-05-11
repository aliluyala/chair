package com.chair.manager.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chair.manager.bean.EasyUIResult;
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
		// return deviceService.queryDeviceListForPage();
	}
}
