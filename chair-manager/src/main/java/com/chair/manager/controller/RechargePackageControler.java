package com.chair.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chair.manager.bean.EasyUIResult;
import com.chair.manager.service.RechargePackageService;


@RequestMapping("/recharge")
@Controller
public class RechargePackageControler {

	@Autowired
	private RechargePackageService rechargeRecordService;
	
	
	@RequestMapping(value="list",method=RequestMethod.POST)
    @ResponseBody
    public EasyUIResult postList(@RequestParam("page") Integer page, @RequestParam("rows") Integer rows){
        return  rechargeRecordService.queryPage(page,rows);
    }
	
}
