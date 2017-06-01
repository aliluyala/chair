package com.chair.manager.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chair.manager.bean.EasyUIResult;
import com.chair.manager.service.RechargeRecordService;

/**
 * 统计
 * @author Administrator
 *
 */
@RequestMapping("/statistics")
@Controller
public class Statistics {
	private Logger logger = Logger.getLogger(Statistics.class);
	
	@Autowired
	private RechargeRecordService rechargeRecordService;

	/**
	 * 查询充值记录列表，分页（管理台前端）
	 * @param param
	 * @return
	 */
	@RequestMapping(value="listForPage",method=RequestMethod.POST)
	@ResponseBody
	public EasyUIResult queryRechargeRecordList(@RequestParam("page") Integer page, @RequestParam("rows") Integer rows, @RequestParam("params") Object params ){
		System.err.println("---查询充值记录，分页---page："+page+"\t rows："+rows+" \t params："+params);
		return  rechargeRecordService.queryPage(page,rows);
	}	
}
