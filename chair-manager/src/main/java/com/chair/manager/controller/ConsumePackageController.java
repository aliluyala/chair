package com.chair.manager.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chair.manager.bean.EasyUIResult;
import com.chair.manager.pojo.ConsumePackage;
import com.chair.manager.service.ConsumePackageService;



@RequestMapping("/consume")
@Controller
public class ConsumePackageController {

	@Autowired
	private ConsumePackageService consumePackageService;


	@RequestMapping(value="list",method=RequestMethod.POST)
	@ResponseBody
	public EasyUIResult rechargeList(@RequestParam("page") Integer page, @RequestParam("rows") Integer rows){
		return  consumePackageService.queryPage(page,rows);
	}
	@RequestMapping(value="save",method=RequestMethod.POST)
	@ResponseBody
	public EasyUIResult saveRecharge(ConsumePackage cp){
		cp.setCreateTime(new Date());
		cp.setLastUpdate(new Date());
		cp.setStatus(1);
		consumePackageService.save(cp);
		EasyUIResult rs= new EasyUIResult();
		return rs;

	}



}
