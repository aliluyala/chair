package com.chair.manager.controller;

import java.util.Date;

import org.apache.log4j.Logger;
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
	private Logger logger = Logger.getLogger(ConsumePackageController.class);

	@Autowired
	private ConsumePackageService consumePackageService;

	/**
	 * 查询消费套餐列表，分页（管理台前端）
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "listForPage", method = RequestMethod.POST)
	@ResponseBody
	public EasyUIResult rechargeList(@RequestParam("page") Integer page, @RequestParam("rows") Integer rows) {
		return consumePackageService.queryPage(page, rows);
	}

	/**
	 * 新增消费套餐（管理台前端）
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public EasyUIResult saveRecharge(ConsumePackage cp) {
		cp.setCreateTime(new Date());
		cp.setLastUpdate(new Date());
		cp.setStatus(1);
		consumePackageService.save(cp);
		EasyUIResult rs = new EasyUIResult();
		return rs;

	}

	/**
	 * 批量删除消费套餐（管理台前端）
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "batDel", method = RequestMethod.POST)
	private Integer delProxy(@RequestParam("ids") Integer[] ids) {
		logger.info("---将要删除的消费套餐ids--->>>" + ids);
		return consumePackageService.deleteByIds(ids);
	}

}
