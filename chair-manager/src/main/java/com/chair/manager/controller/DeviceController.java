package com.chair.manager.controller;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chair.manager.bean.EasyUIResult;
import com.chair.manager.exception.ChairException;
import com.chair.manager.pojo.Device;
import com.chair.manager.pojo.Factory;
import com.chair.manager.pojo.FactoryProxy;
import com.chair.manager.pojo.Shop;
import com.chair.manager.service.DeviceService;
import com.chair.manager.service.FactoryProxyService;
import com.chair.manager.service.FactoryService;
import com.chair.manager.service.ShopService;

@RequestMapping("/device")
@Controller
public class DeviceController {
	private Logger logger = Logger.getLogger(DeviceController.class);
	
	@Autowired
	private DeviceService deviceService;
	
	@Autowired
	private FactoryService factoryService;
	
	@Autowired
	private FactoryProxyService factoryProxyService;
	
	@Autowired
	private ShopService shopService;
	
	

	
	/**
	 * 查询设备列表，分页（管理台前端）
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="listForPage",method=RequestMethod.POST)
	private EasyUIResult queryDeviceListForPage(@RequestParam("page") Integer page, @RequestParam("rows") Integer rows){
		return deviceService.queryDeviceListForPage(page, rows);
	}
	
	
	/**
	 * 新增设备（管理台前端）
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="save",method=RequestMethod.POST)
	private EasyUIResult addDevice(Device deivce){
		logger.info("------【新增设备】参数------"+deivce);
		Factory factory = factoryService.findById(deivce.getFacrotyId());
		FactoryProxy factoryProxy = factoryProxyService.findById(deivce.getProxyId());
		Shop shop = shopService.findById(deivce.getShopId());
		logger.debug("------厂家详情：--->>>"+factory);
		logger.debug("------代理详情：--->>>"+factoryProxy);
		logger.debug("------商家详情：--->>>"+shop);
		deivce.setFactoryName(factory.getFactoryName());
		deivce.setProxyName(factoryProxy.getProxyName());
		deivce.setShopName(shop.getShopName());
		deivce.setShopLocation(shop.getShopLocation());
		deivce.setStatus(1);
		deivce.setCreateTime(new Date());
		deivce.setLastUpdate(new Date());
		int saveRs = deviceService.save(deivce);
		if(saveRs <= 0) throw new ChairException("-2", getClass()+",SQL操作失败。"); 
		return new EasyUIResult();
	}
	
	
	
	/**
	 * 删除设备（管理台前端）
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="batDel",method=RequestMethod.POST)
	private Integer delDevice(@RequestParam("ids") Integer[] ids){
		logger.info("---将要删除的设备ids--->>>"+ids);
		return deviceService.deleteByIds(ids);
	}
	
	
}
