package com.chair.manager.pojo;

import java.util.Date;

public class Statisctics {
	private Integer id;

	private String deviceNo;

	private String shopName;

	private String shopLocation;

	private String shopContactPhone;

	private String shopContact;

	private Integer totalDevice;

	private Integer totalDuration;

	private Float income;

	private Date createTime;

	private Date lastUpdate;

	private Float totalIncome;
	private Integer totalShop;

	private Float dayIncome;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopLocation() {
		return shopLocation;
	}

	public void setShopLocation(String shopLocation) {
		this.shopLocation = shopLocation;
	}

	public String getShopContactPhone() {
		return shopContactPhone;
	}

	public void setShopContactPhone(String shopContactPhone) {
		this.shopContactPhone = shopContactPhone;
	}

	public String getShopContact() {
		return shopContact;
	}

	public void setShopContact(String shopContact) {
		this.shopContact = shopContact;
	}

	public Integer getTotalDevice() {
		return totalDevice;
	}

	public void setTotalDevice(Integer totalDevice) {
		this.totalDevice = totalDevice;
	}

	public Integer getTotalDuration() {
		return totalDuration;
	}

	public void setTotalDuration(Integer totalDuration) {
		this.totalDuration = totalDuration;
	}

	public Float getIncome() {
		return income;
	}

	public void setIncome(Float income) {
		this.income = income;
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

	public Float getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(Float totalIncome) {
		this.totalIncome = totalIncome;
	}

	public Integer getTotalShop() {
		return totalShop;
	}

	public void setTotalShop(Integer totalShop) {
		this.totalShop = totalShop;
	}

	public Float getDayIncome() {
		return dayIncome;
	}

	public void setDayIncome(Float dayIncome) {
		this.dayIncome = dayIncome;
	}

	@Override
	public String toString() {
		return "ProxyStatisctics [id=" + id + ", deviceNo=" + deviceNo + ", shopName=" + shopName + ", shopLocation="
				+ shopLocation + ", shopContactPhone=" + shopContactPhone + ", shopContact=" + shopContact
				+ ", totalDevice=" + totalDevice + ", totalDuration=" + totalDuration + ", income=" + income
				+ ", createTime=" + createTime + ", lastUpdate=" + lastUpdate + ", totalIncome=" + totalIncome
				+ ", totalShop=" + totalShop + ", dayIncome=" + dayIncome + "]";
	}

}