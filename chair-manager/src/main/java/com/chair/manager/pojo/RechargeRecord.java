package com.chair.manager.pojo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="recharge_record")
public class RechargeRecord {
	@Id
    private Integer id;

    private String batchNo;
    
    private String openID;

    private String phoneNumbe;

    private Integer rechargePackageId;

    private BigDecimal rechargeAmount;

    private Integer rechargeDuration;

    private Date rechargeTime;

    private Integer payStatu;

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

    public String getOpenID() {
		return openID;
	}

	public void setOpenID(String openID) {
		this.openID = openID;
	}

	public void setBatchNo(String batchNo) {
        this.batchNo = batchNo == null ? null : batchNo.trim();
    }

    public String getPhoneNumbe() {
        return phoneNumbe;
    }

    public void setPhoneNumbe(String phoneNumbe) {
        this.phoneNumbe = phoneNumbe == null ? null : phoneNumbe.trim();
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

    public Integer getPayStatu() {
        return payStatu;
    }

    public void setPayStatu(Integer payStatu) {
        this.payStatu = payStatu;
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
}