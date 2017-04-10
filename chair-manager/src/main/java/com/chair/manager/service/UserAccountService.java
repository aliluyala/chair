package com.chair.manager.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.chair.manager.pojo.ConsumePackage;
import com.chair.manager.pojo.UserAccount;

public class UserAccountService extends BaseService<UserAccount> {
	@Autowired
	private ConsumePackageService consumePackageService;
	
	public Integer recharge(String phoneNumber, Integer packageID) {
		ConsumePackage consumePackage=consumePackageService.findById(packageID);
		return null;
	}

}
