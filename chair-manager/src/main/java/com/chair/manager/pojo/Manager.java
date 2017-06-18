package com.chair.manager.pojo;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "manager")
public class Manager {
	@Id
	private Integer id;
	private String user;
	private String password;
	private Integer type;
	private Integer factoryId;
	private String factoryName;
	private Integer proxyId;
	private String proxyName;
	private String proxyPhone;
	private String proxyLocation;
	private Date proxyDate;
	private Integer proxyPercent;
	private Integer shopId;
	private String shopName;
	private String shopLocation;
	private Date shopDate;
	private String shopContact;
	private String shopContactPhone;
	private Integer shopPercent;
	private Date createTime;
	private Date lastUpdate;

	public String getProxyPhone() {
		return proxyPhone;
	}

	public void setProxyPhone(String proxyPhone) {
		this.proxyPhone = proxyPhone;
	}

	public String getProxyLocation() {
		return proxyLocation;
	}

	public void setProxyLocation(String proxyLocation) {
		this.proxyLocation = proxyLocation;
	}

	public Date getProxyDate() {
		return proxyDate;
	}

	public void setProxyDate(Date proxyDate) {
		this.proxyDate = proxyDate;
	}

	public Integer getProxyPercent() {
		return proxyPercent;
	}

	public void setProxyPercent(Integer proxyPercent) {
		this.proxyPercent = proxyPercent;
	}

	public Date getShopDate() {
		return shopDate;
	}

	public void setShopDate(Date shopDate) {
		this.shopDate = shopDate;
	}

	public String getShopContact() {
		return shopContact;
	}

	public void setShopContact(String shopContact) {
		this.shopContact = shopContact;
	}

	public String getShopContactPhone() {
		return shopContactPhone;
	}

	public void setShopContactPhone(String shopContactPhone) {
		this.shopContactPhone = shopContactPhone;
	}

	public Integer getShopPercent() {
		return shopPercent;
	}

	public void setShopPercent(Integer shopPercent) {
		this.shopPercent = shopPercent;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getFactoryId() {
		return factoryId;
	}

	public void setFactoryId(Integer factoryId) {
		this.factoryId = factoryId;
	}

	public String getFactoryName() {
		return factoryName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	public Integer getProxyId() {
		return proxyId;
	}

	public void setProxyId(Integer proxyId) {
		this.proxyId = proxyId;
	}

	public String getProxyName() {
		return proxyName;
	}

	public void setProxyName(String proxyName) {
		this.proxyName = proxyName;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
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

	@Override
	public String toString() {
		return "Manager [id=" + id + ", user=" + user + ", password=" + password + ", type=" + type + ", factoryId="
				+ factoryId + ", factoryName=" + factoryName + ", proxyId=" + proxyId + ", proxyName=" + proxyName
				+ ", shopId=" + shopId + ", shopName=" + shopName + ", shopLocation=" + shopLocation + "]";
	}

}
