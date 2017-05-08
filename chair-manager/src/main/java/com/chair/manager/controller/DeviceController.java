package com.chair.manager.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chair.manager.bean.ReqParam;
import com.chair.manager.bean.ResponseResult;
import com.chair.manager.service.DeviceService;

@RequestMapping("/device")
@Controller
public class DeviceController {
	private Logger logger = Logger.getLogger(DeviceController.class);
	
	@Autowired
	private DeviceService deviceService;

	
	/**
	 * 查询设备列表
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="queryDeviceListForPage",method=RequestMethod.POST)
	private ResponseResult queryDeviceList(@RequestBody ReqParam param){
		logger.info("------【查询设备列表】---参数>>>"+param);
		return new ResponseResult(deviceService.queryDeviceList());
	}
}
