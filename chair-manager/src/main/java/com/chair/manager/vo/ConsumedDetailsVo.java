package com.chair.manager.vo;

import java.io.Serializable;
import java.util.List;

public class ConsumedDetailsVo  implements Serializable {
	private static final long serialVersionUID = 1L;

	private String openID;

	private String phoneNumber;

	private String rechargeAmount;

	private Integer consumedDuration;

	private String consumedTime;
	
	private List<ConsumedDetailsVo> consumedList;

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

	public String getRechargeAmount() {
		return rechargeAmount;
	}

	public void setRechargeAmount(String rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}

	public Integer getConsumedDuration() {
		return consumedDuration;
	}

	public void setConsumedDuration(Integer consumedDuration) {
		this.consumedDuration = consumedDuration;
	}

	public String getConsumedTime() {
		return consumedTime;
	}

	public void setConsumedTime(String consumedTime) {
		this.consumedTime = consumedTime;
	}

	public List<ConsumedDetailsVo> getConsumedList() {
		return consumedList;
	}

	public void setConsumedList(List<ConsumedDetailsVo> consumedList) {
		this.consumedList = consumedList;
	}
}
