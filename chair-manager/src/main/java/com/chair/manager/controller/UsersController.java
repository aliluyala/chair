package com.chair.manager.controller;

import java.util.Date;
import java.util.List;

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
import com.chair.manager.pojo.RechargeRecord;
import com.chair.manager.pojo.Users;
import com.chair.manager.service.ConsumePackageService;
import com.chair.manager.service.ConsumedDetailsService;
import com.chair.manager.service.RechargeRecordService;
import com.chair.manager.service.UsersService;


@RequestMapping("/users")
@Controller
public class UsersController {
	private Logger logger = Logger.getLogger(UsersController.class);

	@Autowired
	private RechargeRecordService rechargeRecordService;
	@Autowired
	private ConsumedDetailsService consumedDetailsService;
	@Autowired
	private UsersService  usersService;
	@Autowired
	private ConsumePackageService  consumePackageService;

	/**
	 * 查看用户消费明细
	 * @param userID 用户ID
	 * @param phoneNumber 用户手机号
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="queryRechargeDetails",method=RequestMethod.POST)
	private ResponseResult queryRechargeDetails(@RequestBody ReqParam param){
		RechargeRecord rr=new RechargeRecord();
		rr.setPhoneNumbe(param.getPhoneNumber());
		return new ResponseResult(rechargeRecordService.queryList(rr));
	}
	/**
	 * 查看用户消费明细
	 * @param userID 用户ID
	 * @param phoneNumber 用户手机号
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="queryConsumedDetails",method=RequestMethod.POST)
	private ResponseResult queryConsumedDetails(@RequestBody ReqParam param){
		ConsumedDetails cd=new ConsumedDetails();
		cd.setPhoneNumber(param.getPhoneNumber());
		return new ResponseResult(consumedDetailsService.queryList(cd));
	}
	
	

	/**
	 * 用户登录
	 * @param userID 用户ID
	 * @param phoneNumber 用户手机号
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="login",method=RequestMethod.POST)
	private ResponseResult userLogin(@RequestBody ReqParam param){
		return new ResponseResult(usersService.login(param.getPhoneNumber(), param.getIdentCode())); 
	}
	

	/**
	 * 发送用户验证码
	 * @param userID 用户ID
	 * @param phoneNumber 用户手机号
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="sendCode",method=RequestMethod.POST)
	private ResponseResult sendCode(@RequestBody ReqParam param){
		
		usersService.sendCode(param.getPhoneNumber());
		return new ResponseResult(null); 
	}
	
	
	
}
