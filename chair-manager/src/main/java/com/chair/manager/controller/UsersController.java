package com.chair.manager.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chair.manager.bean.ReqParam;
import com.chair.manager.bean.ResponseResult;
import com.chair.manager.pojo.ConsumedDetails;
import com.chair.manager.pojo.RechargeRecord;
import com.chair.manager.pojo.Users;
import com.chair.manager.service.ConsumedDetailsService;
import com.chair.manager.service.RechargeRecordService;
import com.chair.manager.service.UsersService;


@RequestMapping("/users")
@Controller
public class UsersController {

	@Autowired
	private RechargeRecordService rechargeRecordService;
	@Autowired
	private ConsumedDetailsService consumedDetailsService;
	@Autowired
	private UsersService  usersService;

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
		rr.setPhonenumber(param.getPhoneNumber());
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
		cd.setPhonenumber(param.getPhoneNumber());
		return new ResponseResult(consumedDetailsService.queryList(cd));
	}
	
	

	/**
	 * 查看用户消费明细
	 * @param userID 用户ID
	 * @param phoneNumber 用户手机号
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="login",method=RequestMethod.POST)
	private ResponseResult userLogin(@RequestBody ReqParam param){
		param.getPhoneNumber();
		param.getIdentCode();
		//1.验证登陆信息
		//2.查询存在则更新，不存在则新增
		//insert into users(phoneNumber, createTime, lastUpdate) values('13530380829',now(), now()) ON DUPLICATE KEY UPDATE lastUpdate=now();
		Users u=new Users();
		u.setPhonenumber(param.getPhoneNumber());
		u.setCreatetime(new Date());
		u.setLastupdate(new Date());
		Users user = usersService.saveOrUpdate(u);
		System.out.println("---user-------"+user);
		return new ResponseResult(null);
	}
}
