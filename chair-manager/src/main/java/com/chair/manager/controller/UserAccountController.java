package com.chair.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chair.manager.service.UserAccountService;

@RequestMapping("/account")
@Controller
public class UserAccountController {

	
	private UserAccountService userAccountService;
	
	
	@ResponseBody
	@RequestMapping(value="recharge",method=RequestMethod.POST)
	private Integer recharge(@RequestParam("userID") Integer userID, @RequestParam("phoneNumber") String phoneNumber,
			 @RequestParam("packageID") Integer packageID ){
		return userAccountService.recharge(phoneNumber,packageID);
	}
}
