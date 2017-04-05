package com.chair.manager.controller;


import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chair.manager.pojo.Pattern;
import com.chair.manager.service.PatternService;


@RequestMapping("/pattern")
@Controller
public class PatternController {

	
	private PatternService  testService;
	/**
	 * 根据ID查询
	 * @param pId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/query/{pId}" ,method = RequestMethod.GET)
	public Pattern queryOrderById(@PathVariable("pId") Long pId) {
		return testService.findById(pId);
	}

	/**
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/query" ,method = RequestMethod.GET)
	public List<Pattern> queryOrderByUserNameAndPage() {
		return testService.queryAll();
	}
	
	
}
