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
import com.chair.manager.pojo.RechargePackage;
import com.chair.manager.service.RechargePackageService;


@RequestMapping("/recharge")
@Controller
public class RechargePackageControler {
	private Logger logger = Logger.getLogger(RechargePackageControler.class);
	

	@Autowired
	private RechargePackageService rechargePackageService;


	/**
	 * 查询充值套餐列表，分页（管理台前端）
	 * @param param
	 * @return
	 */
	@RequestMapping(value="listForPage",method=RequestMethod.POST)
	@ResponseBody
	public EasyUIResult rechargeList(@RequestParam("page") Integer page, @RequestParam("rows") Integer rows){
		return  rechargePackageService.queryPage(page,rows);
	}
	
	/**
	 * 新增充值套餐（管理台前端）
	 * @param param
	 * @return
	 */
	@RequestMapping(value="save",method=RequestMethod.POST)
	@ResponseBody
	public EasyUIResult saveRecharge(RechargePackage rp){
		rp.setCreateTime(new Date());
		rp.setLastUpdate(new Date());
		rp.setStatus(1);
		rechargePackageService.save(rp);
		EasyUIResult rs= new EasyUIResult();
		return rs;

	}
	

	/**
	 * 删除充值套餐（管理台前端）
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="batDel",method=RequestMethod.POST)
	private Integer delDevice(@RequestParam("ids") Integer[] ids){
		logger.info("---将要删除的充值套餐ids--->>>"+ids);
		return rechargePackageService.deleteByIds(ids);
	}
	

}
