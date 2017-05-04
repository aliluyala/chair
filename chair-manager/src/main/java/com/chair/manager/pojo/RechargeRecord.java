package com.chair.manager.pojo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "recharge_record")
public class RechargeRecord {
	@Id
	private Integer id;

	private String batchNo;
	
	private String transactionId;

	private String openId;

	private String phoneNumber;

	private Integer rechargePackageId;

	private BigDecimal rechargeAmount;

	private Integer rechargeDuration;

	private Date rechargeTime;

	private Integer payStatus;

	private Date createTime;

	private Date lastUpdate;
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBatchNo() {
		return batchNo;
	}

	
	
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo == null ? null : batchNo.trim();
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Integer getRechargePackageId() {
		return rechargePackageId;
	}

	public void setRechargePackageId(Integer rechargePackageId) {
		this.rechargePackageId = rechargePackageId;
	}

	public BigDecimal getRechargeAmount() {
		return rechargeAmount;
	}

	public void setRechargeAmount(BigDecimal rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}

	public Integer getRechargeDuration() {
		return rechargeDuration;
	}

	public void setRechargeDuration(Integer rechargeDuration) {
		this.rechargeDuration = rechargeDuration;
	}

	public Date getRechargeTime() {
		return rechargeTime;
	}

	public void setRechargeTime(Date rechargeTime) {
		this.rechargeTime = rechargeTime;
	}


	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@Override
	public String toString() {
		return "RechargeRecord [id=" + id + ", batchNo=" + batchNo + ", transactionId=" + transactionId + ", openId="
				+ openId + ", phoneNumber=" + phoneNumber + ", rechargePackageId=" + rechargePackageId
				+ ", rechargeAmount=" + rechargeAmount + ", rechargeDuration=" + rechargeDuration + ", rechargeTime="
				+ rechargeTime + ", payStatus=" + payStatus + ", createTime=" + createTime + ", lastUpdate="
				+ lastUpdate + "]";
	}

}