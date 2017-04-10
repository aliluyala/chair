package com.chair.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chair.manager.bean.ResponseResult;
import com.chair.manager.pojo.ConsumedDetails;
import com.chair.manager.pojo.RechargeRecord;
import com.chair.manager.service.ConsumedDetailsService;
import com.chair.manager.service.RechargeRecordService;


@RequestMapping("/users")
@Controller
public class UsersController {

	@Autowired
	private RechargeRecordService rechargeRecordService;
	@Autowired
	private ConsumedDetailsService consumedDetailsService;

	/**
	 * 查看用户消费明细
	 * @param userID 用户ID
	 * @param phoneNumber 用户手机号
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="queryRechargeDetails",method=RequestMethod.POST)
	private ResponseResult queryRechargeDetails(@RequestParam("userID") Integer userID, @RequestParam("phoneNumber") String phoneNumber){
		RechargeRecord rr=new RechargeRecord();
		rr.setPhonenumber(phoneNumber);
		return new ResponseResult(rechargeRecordService.queryList(rr));
	}
	/**
	 * 查看用户消费明细
	 * @param userID 用户ID
	 * @param phoneNumber 用户手机号
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="queryRechargeDetails",method=RequestMethod.POST)
	private ResponseResult queryConsumedDetails(@RequestParam("userID") Integer userID, @RequestParam("phoneNumber") String phoneNumber){
		ConsumedDetails cd=new ConsumedDetails();
		cd.setPhonenumber(phoneNumber);
		return new ResponseResult(consumedDetailsService.queryList(cd));
	}
}
