package com.chair.manager.pojo;

import java.util.Date;

public class ProxyStatisctics {
	/*
	 * <th data-options="field:'ck',checkbox:true"></th> <th
	 * data-options="field:'id',width:60">ID</th> <th
	 * data-options="field:'shopName',width:100">商铺</th> <th
	 * data-options="field:'shopLocation',width:150">地址</th> <th
	 * data-options="field:'shopPhone',width:100">电话</th> <th
	 * data-options="field:'shopContacts',width:100">联系人</th> <th
	 * data-options="field:'totalDevice',width:100">设备数</th> <th
	 * data-options="field:'totalDuration',width:100">消费时长</th> <th
	 * data-options="field:'income',width:100">收益</th>
	 */

	private Integer id;

	private String shopName;

	private String shopLocation;

	private String shopContactPhone;

	private String shopContact;

	private Integer totalDevice;

	private Integer totalDuration;

	private Float income;

	private Date createTime;

	private Date lastUpdate;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "ProxyStatisctics [id=" + id + ", shopName=" + shopName + ", shopLocation=" + shopLocation
				+ ", shopContactPhone=" + shopContactPhone + ", shopContact=" + shopContact + ", totalDevice="
				+ totalDevice + ", totalDuration=" + totalDuration + ", income=" + income + ", createTime=" + createTime
				+ ", lastUpdate=" + lastUpdate + "]";
	}

}