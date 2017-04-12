package com.chair.manager.bean;

import java.io.Serializable;

public class ReqParam implements  Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5030123507774610563L;
	/**
	 * //用户id
	 */
	private Integer userID;
	/**
	 * 1订单支付  2账单支付 3充值
	 */
	private Integer type;
	/**
	 * type=1订单NO， type=2账单NO， type=3 充值批次编号
	 */
	private String businessNO;
	/**
	 * 用户手机号
	 */
	private String phoneNumber;
	/**
	 * 套餐ID
	 */
	private Integer packageID;
	public Integer getUserID() {
		return userID;
	}
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getBusinessNO() {
		return businessNO;
	}
	public void setBusinessNO(String businessNO) {
		this.businessNO = businessNO;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Integer getPackageID() {
		return packageID;
	}
	public void setPackageID(Integer packageID) {
		this.packageID = packageID;
	}
	
}
