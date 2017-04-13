package com.chair.manager.controller;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chair.manager.bean.ReqParam;
import com.chair.manager.bean.ResponseResult;
import com.chair.manager.pojo.ConsumePackage;
import com.chair.manager.pojo.ConsumedDetails;
import com.chair.manager.pojo.Device;
import com.chair.manager.service.ConsumePackageService;
import com.chair.manager.service.ConsumedDetailsService;
import com.chair.manager.service.DeviceService;
import com.chair.manager.service.UserAccountService;

@RequestMapping("/account")
@Controller
public class UserAccountController {
	private static Logger logger = Logger.getLogger(UsersController.class);

	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private ConsumePackageService consumePackageService;
	@Autowired
	private ConsumedDetailsService consumedDetailsService;
	
	
	
	@ResponseBody
	@RequestMapping(value="recharge",method=RequestMethod.POST)
	private ResponseResult recharge(@RequestBody ReqParam param){
		return new ResponseResult(userAccountService.recharge(param.getPhoneNumber(),param.getPackageID()));
	}
	
	
	
	/**
	 * 用户选择套餐使用
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="choosePackage",method=RequestMethod.POST)
	private ResponseResult choosePackage(@RequestBody ReqParam param){
		logger.info("---用户选择套餐---参数为：："+param);
		//查询设备信息
		Device d = new Device();
		d.setDeviceNo(param.getDeviceNO());
		Device device = deviceService.findByUnique(d);
		//查询套餐信息
		ConsumePackage consumePackage = consumePackageService.findById(param.getConsumePackageID());
		
		//TODO 发送消息给硬件启动设备， 同步任务
		
		//设置消费明细对象
		ConsumedDetails consumedDetails = new ConsumedDetails();
		consumedDetails.setPhoneNumber(param.getPhoneNumber());
		consumedDetails.setConsumedPackageId(consumePackage.getId());
		consumedDetails.setConsumedDuration(consumePackage.getConsumedDuration());
		consumedDetails.setFactoryId(device.getFacrotyId());
		consumedDetails.setFactoryName(device.getFactoryName());
		consumedDetails.setProxyId(device.getProxyId());
		consumedDetails.setProxyName(device.getProxyName());
		consumedDetails.setShopId(device.getShopId());
		consumedDetails.setShopName(device.getShopName());
		consumedDetails.setDeviceId(device.getId());
		consumedDetails.setConsumedTime(new Date());
		consumedDetails.setCreateTime(new Date());
		consumedDetails.setLastUpdate(new Date());
		//保存消费明细信息
		consumedDetailsService.save(consumedDetails);
		
		
		return new ResponseResult(userAccountService.recharge(param.getPhoneNumber(),param.getPackageID()));
	}
}
