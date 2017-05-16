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
	
	
	/**
	 * 查询商家列表，分页（管理台前端）
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="listForPage",method=RequestMethod.POST)
	private EasyUIResult queryProxyListForPage(@RequestParam("page") Integer page, @RequestParam("rows") Integer rows){
		return shopService.queryShopListForPage(page, rows);
	}
	
	
	/**
	 * 新增商家（管理台前端）
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="save",method=RequestMethod.POST)
	private EasyUIResult addProxy(Shop shop){
		logger.info("------【新增商家】参数------"+shop);
		shop.setCreateTime(new Date());
		shop.setLastUpdate(new Date());
		int saveRs = shopService.save(shop);
		if(saveRs <= 0) throw new ChairException("-2", getClass()+",SQL操作失败。"); 
		return new EasyUIResult();
	}
	
	
	/**
	 * 批量删除商家（管理台前端）
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="batDel",method=RequestMethod.POST)
	private Integer delProxy(@RequestParam("ids") Integer[] ids){
		logger.info("---将要删除的商家ids--->>>"+ids);
		return shopService.deleteByIds(ids);
	}
}
