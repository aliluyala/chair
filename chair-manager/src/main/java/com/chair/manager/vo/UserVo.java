package com.chair.manager.vo;

import java.io.Serializable;
import java.util.List;

public class UserVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer userID;

	private String phoneNumber;

	private List<ConsumePackageVo> packageList;
	
	private List<ConsumePackageVo> couponList;

	public List<ConsumePackageVo> getCouponList() {
		return couponList;
	}

	public void setCouponList(List<ConsumePackageVo> couponList) {
		this.couponList = couponList;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<ConsumePackageVo> getPackageList() {
		return packageList;
	}

	public void setPackageList(List<ConsumePackageVo> packageList) {
		this.packageList = packageList;
	}

}