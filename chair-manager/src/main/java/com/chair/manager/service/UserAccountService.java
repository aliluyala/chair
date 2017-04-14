package com.chair.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chair.manager.pojo.ConsumePackage;
import com.chair.manager.pojo.UserAccount;

@Service
public class UserAccountService extends BaseService<UserAccount> {
	@Autowired
	private ConsumePackageService consumePackageService;
	
	public Integer recharge(String phoneNumber, Integer packageID) {
		ConsumePackage consumePackage=consumePackageService.findById(packageID);
		consumePackage.getId();
		UserAccount ua=new UserAccount();
		super.save(ua);
		ua.setPhoneNumber(phoneNumber);
		return null;
	}

}
