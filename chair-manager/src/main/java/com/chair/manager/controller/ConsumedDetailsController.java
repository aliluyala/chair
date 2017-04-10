package com.chair.manager.controller;


import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chair.manager.pojo.ConsumedDetails;
import com.chair.manager.service.ConsumedDetailsService;


@RequestMapping("/consumed/details")
@Controller
public class ConsumedDetailsController {
	
	private ConsumedDetailsService  consumedDetailsService;
	/**
	 * 根据ID查询
	 * @param pId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/query/{cId}" ,method = RequestMethod.GET)
	public ConsumedDetails queryOrderById(@PathVariable("cId") Long cId) {
		return consumedDetailsService.findById(cId);
	}

	/**
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/query" ,method = RequestMethod.GET)
	public List<ConsumedDetails> queryOrderByUserNameAndPage() {
		return consumedDetailsService.queryList(new ConsumedDetails());
	}
	
	
}
