package com.chair.manager.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chair.manager.bean.EasyUIResult;
import com.chair.manager.pojo.RechargePackage;
import com.chair.manager.service.RechargePackageService;


@RequestMapping("/recharge")
@Controller
public class RechargePackageControler {

	@Autowired
	private RechargePackageService rechargeRecordService;


	@RequestMapping(value="list",method=RequestMethod.POST)
	@ResponseBody
	public EasyUIResult rechargeList(@RequestParam("page") Integer page, @RequestParam("rows") Integer rows){
		return  rechargeRecordService.queryPage(page,rows);
	}
	@RequestMapping(value="save",method=RequestMethod.POST)
	@ResponseBody
	public EasyUIResult saveRecharge(RechargePackage rp){
		rp.setCreateTime(new Date());
		rp.setLastUpdate(new Date());
		rechargeRecordService.save(rp);
		EasyUIResult rs= new EasyUIResult();
		return rs;

	}

}
