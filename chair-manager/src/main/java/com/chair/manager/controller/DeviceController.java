package com.chair.manager.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chair.manager.bean.EasyUIResult;
import com.chair.manager.service.DeviceService;

@RequestMapping("/device")
@Controller
public class DeviceController {
	private Logger logger = Logger.getLogger(DeviceController.class);
	
	@Autowired
	private DeviceService deviceService;

	
	/**
	 * 查询设备列表（管理台前端）
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="list",method=RequestMethod.POST)
	private EasyUIResult queryDeviceListForPage(@RequestParam("page") Integer page, @RequestParam("rows") Integer rows){
		return deviceService.queryDeviceListForPage(page, rows);
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
	
	
	public static void main(String[] args) {
		Integer i =123;
		System.out.println(i.intValue());
	}
}
