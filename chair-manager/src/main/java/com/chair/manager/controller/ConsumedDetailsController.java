package com.chair.manager.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chair.manager.bean.ResponseResult;
import com.chair.manager.pojo.ConsumedDetails;
import com.chair.manager.service.ConsumedDetailsService;


@RequestMapping("/consumed/details")
@Controller
public class ConsumedDetailsController {

	@Autowired
	private ConsumedDetailsService  consumedDetailsService;
	/**
	 * 根据ID查询
	 * @param pId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/query/{cId}" ,method = RequestMethod.GET)
	public ResponseResult queryOrderById(@PathVariable("cId") Integer cId) {
		return new ResponseResult(consumedDetailsService.findById(cId));
	}

	/**
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/query" ,method = RequestMethod.GET)
	public ResponseResult queryOrderByUserNameAndPage() {
		return new ResponseResult(consumedDetailsService.queryList(new ConsumedDetails()));
	}

	

	/**
	 * 查询消费套餐列表（客户端展示）
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryConsumerList" ,method = RequestMethod.GET)
	public ResponseResult queryConsumerList() {
		return new ResponseResult(consumedDetailsService.queryList(new ConsumedDetails()));
	}
}
