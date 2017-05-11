package com.chair.manager.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chair.manager.pojo.Shop;
import com.chair.manager.service.ShopService;

@RequestMapping("/shop")
@Controller
public class ShopController {
	private Logger logger = Logger.getLogger(ShopController.class);

	@Autowired
	private ShopService shopService;

	/**
	 * 查询厂家列表（管理台前端）
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "list", method = RequestMethod.POST)
	private List<Shop> queryFactoryList() {
		logger.info("------【查询商家列表】------");
		List<Shop> shops = shopService.queryList(null);
		return shops;
	}
	
}
