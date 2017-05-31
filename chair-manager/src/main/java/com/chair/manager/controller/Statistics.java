package com.chair.manager.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chair.manager.bean.EasyUIResult;
import com.chair.manager.pojo.ConsumedDetails;
import com.chair.manager.pojo.Manager;
import com.chair.manager.pojo.RechargeRecord;
import com.chair.manager.service.ConsumedDetailsService;
import com.chair.manager.service.RechargeRecordService;

/**
 * 统计
 * 
 * @author Administrator
 *
 */
@RequestMapping("/statistics")
@Controller
public class Statistics {
	private Logger logger = Logger.getLogger(Statistics.class);

	@Autowired
	private RechargeRecordService rechargeRecordService;

	@Autowired
	private ConsumedDetailsService consumedDetailsService;

	/**
	 * 查询充值记录列表，分页（管理台前端）
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "listRecordForPage", method = RequestMethod.POST)
	@ResponseBody
	public EasyUIResult queryRechargeRecordList(@RequestParam("page") Integer page, @RequestParam("rows") Integer rows,
			@RequestParam("phoneNumber") String phoneNumber) {
		System.err.println("---查询充值记录，分页---page：" + page + "\t rows：" + rows + "\t phoneNumber：" + phoneNumber);
		RechargeRecord record = new RechargeRecord();
		if (!"0".equals(phoneNumber))
			record.setPhoneNumber(phoneNumber);
		return rechargeRecordService.queryPage(record, page, rows);
	}

	/**
	 * 查询消费记录列表，分页（管理台前端）
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "listConsumeForPage", method = RequestMethod.POST)
	@ResponseBody
	public EasyUIResult queryConsumeList(HttpSession session, @RequestParam("page") Integer page,
			@RequestParam("rows") Integer rows, @RequestParam("factoryID") Integer factoryID,
			@RequestParam("proxyID") Integer proxyID, @RequestParam("shopID") Integer shopID) {
		System.err.println("---查询消费记录，分页---page：" + page + "\n rows：" + rows + " \n factoryID：" + factoryID
				+ " \n proxyID：" + proxyID + " \n shopID：" + shopID);
		Manager manager = (Manager) session.getAttribute("user");
		System.err.println("---session.manager---" + manager);
		ConsumedDetails consume = new ConsumedDetails();
		if ("admin".equalsIgnoreCase(manager.getUser())) { // 厂家权限
			logger.info("---我是厂家权限---");
			if (factoryID != 0)
				consume.setFactoryId(factoryID);
			if (proxyID != 0)
				consume.setProxyId(proxyID);
			if (shopID != 0)
				consume.setShopId(shopID);
		} else if ("user1".equalsIgnoreCase(manager.getUser())) { // 代理权限
			logger.info("---我是代理权限---");
			if (proxyID != 0)
				consume.setProxyId(proxyID);
			if (shopID != 0)
				consume.setShopId(shopID);
		} else if ("user2".equalsIgnoreCase(manager.getUser())) { // 店家权限
			logger.info("---我是店家权限---");
			if (shopID != 0)
				consume.setShopId(shopID);
		}
		return consumedDetailsService.queryPage(consume, page, rows);
	}
}
