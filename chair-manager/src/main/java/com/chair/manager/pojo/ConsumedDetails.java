package com.chair.manager.pojo;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="consumed_details")
public class ConsumedDetails {
	@Override
	public String toString() {
		return "ConsumedDetails [id=" + id + ", openId=" + openId + ", phoneNumber=" + phoneNumber
				+ ", consumedPackageId=" + consumedPackageId + ", consumedDuration=" + consumedDuration + ", factoryId="
				+ factoryId + ", factoryName=" + factoryName + ", proxyId=" + proxyId + ", proxyName=" + proxyName
				+ ", shopId=" + shopId + ", shopName=" + shopName + ", shopLocation=" + shopLocation + ", deviceId="
				+ deviceId + ", consumedTime=" + consumedTime + ", status=" + status + ", createTime=" + createTime
				+ ", lastUpdate=" + lastUpdate + "]";
	}

	@Id
    private Integer id;

    private String openId;
	
    private String phoneNumber;

    private Integer consumedPackageId;

    private Integer consumedDuration;

    private Integer factoryId;

    private String factoryName;

    private Integer proxyId;

    private String proxyName;

    private Integer shopId;

    private String shopName;

    private String shopLocation;

    private Integer deviceId;
    
    private Date consumedTime;
    
    private Integer status;

    private Date createTime;

    private Date lastUpdate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
    }

    public Integer getConsumedPackageId() {
        return consumedPackageId;
    }

    public void setConsumedPackageId(Integer consumedPackageId) {
        this.consumedPackageId = consumedPackageId;
    }

    public Integer getConsumedDuration() {
        return consumedDuration;
    }

    public void setConsumedDuration(Integer consumedDuration) {
        this.consumedDuration = consumedDuration;
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
        this.factoryName = factoryName == null ? null : factoryName.trim();
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
        this.proxyName = proxyName == null ? null : proxyName.trim();
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
        this.shopName = shopName == null ? null : shopName.trim();
    }

    public String getShopLocation() {
        return shopLocation;
    }

    public void setShopLocation(String shopLocation) {
        this.shopLocation = shopLocation == null ? null : shopLocation.trim();
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    
    
    public Date getConsumedTime() {
		return consumedTime;
	}

	public void setConsumedTime(Date consumedTime) {
		this.consumedTime = consumedTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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