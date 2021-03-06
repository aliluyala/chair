package com.chair.manager.controller;

import java.util.Date;
import java.util.List;

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
import com.chair.manager.pojo.Device;
import com.chair.manager.pojo.DeviceCommandLog;
import com.chair.manager.pojo.DeviceLog;
import com.chair.manager.pojo.Factory;
import com.chair.manager.pojo.FactoryProxy;
import com.chair.manager.pojo.Manager;
import com.chair.manager.pojo.Shop;
import com.chair.manager.service.DeviceCommandLogService;
import com.chair.manager.service.DeviceLogService;
import com.chair.manager.service.DeviceService;
import com.chair.manager.service.FactoryProxyService;
import com.chair.manager.service.FactoryService;
import com.chair.manager.service.ManagerService;
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
	
	@Autowired
	private ManagerService managerService;
	

	@Autowired
	private DeviceLogService deviceLogService;
	
	@Autowired
	private DeviceCommandLogService deviceCommandLogService;
	
	
	/**
	 * 查询设备列表，分页（管理台前端）
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="listForPage",method=RequestMethod.POST)
	private EasyUIResult queryDeviceListForPage(@RequestParam("page") Integer page, @RequestParam("rows") Integer rows , @RequestParam("deviceNo") String deviceNO){
		Device device = new Device();
		if(!StringUtils.isEmpty(deviceNO))
			device.setDeviceNo(deviceNO);
		return deviceService.queryDeviceListForPage(device, page, rows);
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
		Manager factory = (deivce.getFacrotyId() != null || deivce.getFacrotyId() != 0 ) ? managerService.findById(deivce.getFacrotyId()) : null;
		Manager proxy = (deivce.getProxyId() != null || deivce.getProxyId() != 0 ) ? managerService.findById(deivce.getProxyId()) : null;
		Manager shop = (deivce.getShopId() != null || deivce.getShopId() != 0 ) ? managerService.findById(deivce.getShopId()) : null;
		
		logger.debug("------厂家详情：--->>>"+factory);
		logger.debug("------代理详情：--->>>"+proxy);
		logger.debug("------商家详情：--->>>"+shop);
		deivce.setFactoryName(factory.getFactoryName());
		deivce.setProxyName(proxy.getProxyName());
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
	
	
	/**
	 * 编辑设备（管理台前端）
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="edit",method=RequestMethod.POST)
	private EasyUIResult updateDevice(Device deivce){
		logger.info("------【编辑设备】参数------"+deivce);
		Manager factory = (deivce.getFacrotyId() != null || deivce.getFacrotyId() != 0 ) ? managerService.findById(deivce.getFacrotyId()) : null;
		Manager proxy = (deivce.getProxyId() != null || deivce.getProxyId() != 0 ) ? managerService.findById(deivce.getProxyId()) : null;
		Manager shop = (deivce.getShopId() != null || deivce.getShopId() != 0 ) ? managerService.findById(deivce.getShopId()) : null;
		
		deivce.setFactoryName(factory.getFactoryName());
		deivce.setProxyName(proxy.getProxyName());
		deivce.setShopName(shop.getShopName());
		deivce.setShopLocation(shop.getShopLocation());
		deivce.setStatus(1);
//		deivce.setCreateTime(new Date());
		deivce.setLastUpdate(new Date());
		int saveRs = deviceService.updateSelective(deivce);
		if(saveRs <= 0) throw new ChairException("-2", getClass()+",SQL操作失败。"); 
		return new EasyUIResult();
	}
	
	
	/**
	 * 查询设备状态日志列表，分页（管理台前端）
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="listDeviceLogForPage",method=RequestMethod.POST)
	private EasyUIResult queryDeviceLogListForPage(@RequestParam("page") Integer page, @RequestParam("rows") Integer rows , @RequestParam("deviceNo") String deviceNO){
		DeviceLog deviceLog = new DeviceLog();
		if(!StringUtils.isEmpty(deviceNO))
			deviceLog.setDeviceNo(deviceNO);
		return deviceLogService.queryDeviceLogListForPage(deviceLog, page, rows);
	}
	
	
	/**
	 * 查询设备命令日志列表，分页（管理台前端）
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="listDeviceCommandForPage",method=RequestMethod.POST)
	private EasyUIResult queryDeviceCommandListForPage(@RequestParam("page") Integer page, @RequestParam("rows") Integer rows , @RequestParam("deviceNo") String deviceNO){
		DeviceCommandLog deviceCommand = new DeviceCommandLog();
		if(!StringUtils.isEmpty(deviceNO))
			deviceCommand.setDeviceNo(deviceNO);
		return deviceCommandLogService.queryDeviceCommandListForPage(deviceCommand, page, rows);
	}
	
}
