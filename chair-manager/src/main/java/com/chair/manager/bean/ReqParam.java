package com.chair.manager.bean;

import java.io.Serializable;

public class ReqParam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5030123507774610563L;
	
	private Integer consumerID;
	
	public Integer getConsumerID() {
		return consumerID;
	}

	public void setConsumerID(Integer consumerID) {
		this.consumerID = consumerID;
	}

	/**
	 * //用户id
	 */
	private Integer userID;
	/**
	 * 1订单支付 2账单支付 3充值
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

	/**
	 * 验证码
	 */
	private Integer identCode;

	/**
	 * 设备ID
	 * 
	 * @return
	 */
	private Integer deviceID;

	/**
	 * 设备编号
	 */
	private String deviceNO;
	/**
	 * 消费套餐ID
	 */
	private Integer consumedPackageID;
	/**
	 * 充值批次号
	 */
	private String batchNO;
	/**
	 * 微信唯一标识
	 */
	private String openID;
	
	/**
	 * 微信订单编号
	 */
	private String transactionID;
	
	private Object params;

	
	
	public Object getParams() {
		return params;
	}

	public void setParams(Object params) {
		this.params = params;
	}

	public String getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}

	public String getOpenID() {
		return openID;
	}

	public void setOpenID(String openID) {
		this.openID = openID;
	}

	public Integer getConsumedPackageID() {
		return consumedPackageID;
	}

	public void setConsumedPackageID(Integer consumedPackageID) {
		this.consumedPackageID = consumedPackageID;
	}

	public Integer getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(Integer deviceID) {
		this.deviceID = deviceID;
	}

	public String getDeviceNO() {
		return deviceNO;
	}

	public void setDeviceNO(String deviceNO) {
		this.deviceNO = deviceNO;
	}

	public Integer getIdentCode() {
		return identCode;
	}

	public void setIdentCode(Integer identCode) {
		this.identCode = identCode;
	}

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
	

	public String getBatchNO() {
		return batchNO;
	}

	public void setBatchNO(String batchNO) {
		this.batchNO = batchNO;
	}

	@Override
	public String toString() {
		return "ReqParam [consumerID=" + consumerID + ", userID=" + userID + ", type=" + type + ", businessNO="
				+ businessNO + ", phoneNumber=" + phoneNumber + ", packageID=" + packageID + ", identCode=" + identCode
				+ ", deviceID=" + deviceID + ", deviceNO=" + deviceNO + ", consumedPackageID=" + consumedPackageID
				+ ", batchNO=" + batchNO + ", openID=" + openID + ", transactionID=" + transactionID + ", params="
				+ params + "]";
	}

}
