package com.chair.manager.vo;

import java.io.Serializable;
import java.util.List;

public class DeviceVo implements Serializable {

	private Integer deviceID;

	private String deviceNO;

	private String deviceModel;

	private Integer shopID;

	private String shopLocation;

	private String shopName;

	private Integer proxyID;

	private String proxyName;

	private Integer facrotyID;

	private String factoryName;

	private Integer status;

	private String createTime;

	private String lastUpdate;

	private List<DeviceVo> deviceList;

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

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public Integer getShopID() {
		return shopID;
	}

	public void setShopID(Integer shopID) {
		this.shopID = shopID;
	}

	public String getShopLocation() {
		return shopLocation;
	}

	public void setShopLocation(String shopLocation) {
		this.shopLocation = shopLocation;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Integer getProxyID() {
		return proxyID;
	}

	public void setProxyID(Integer proxyID) {
		this.proxyID = proxyID;
	}

	public String getProxyName() {
		return proxyName;
	}

	public void setProxyName(String proxyName) {
		this.proxyName = proxyName;
	}

	public Integer getFacrotyID() {
		return facrotyID;
	}

	public void setFacrotyID(Integer facrotyID) {
		this.facrotyID = facrotyID;
	}

	public String getFactoryName() {
		return factoryName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public List<DeviceVo> getDeviceList() {
		return deviceList;
	}

	public void setDeviceList(List<DeviceVo> deviceList) {
		this.deviceList = deviceList;
	}

}
