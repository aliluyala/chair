package com.chair.manager.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @author yaoyuming
 *
 */
public class RechargeRecordVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String openID;
	
	private String batchNO;
	
	private String phoneNumber;

	private String rechargeAmount;

	private Integer rechargeDuration;

	private String rechargeTime;

	private List<RechargeRecordVo> rechargeList;

	public List<RechargeRecordVo> getRechargeList() {
		return rechargeList;
	}

	public void setRechargeList(List<RechargeRecordVo> rechargeList) {
		this.rechargeList = rechargeList;
	}

	public String getRechargeAmount() {
		return rechargeAmount;
	}

	public void setRechargeAmount(String rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}

	public Integer getRechargeDuration() {
		return rechargeDuration;
	}

	public void setRechargeDuration(Integer rechargeDuration) {
		this.rechargeDuration = rechargeDuration;
	}

	public String getRechargeTime() {
		return rechargeTime;
	}

	public void setRechargeTime(String rechargeTime) {
		this.rechargeTime = rechargeTime;
	}

	public String getOpenID() {
		return openID;
	}

	public void setOpenID(String openID) {
		this.openID = openID;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}