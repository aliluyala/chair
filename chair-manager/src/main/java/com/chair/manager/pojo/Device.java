package com.chair.manager.pojo;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;


@Table(name="device")
public class Device {
	
//	@Column(name="id")
	@Id
	private Integer id;
	
	private String deviceToken;
	
	private Date onlineTime;

    private String deviceNo;

    private String deviceModel;

    private Integer shopId;

    private String shopLocation;

    private String shopName;

    private Integer proxyId;

    private String proxyName;

    private Integer facrotyId;

    private String factoryName;

    private Integer status;

    private Date createTime;

    private Date lastUpdate;
    
    private String expTime;
    
    
    
    
    

    public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public Date getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(Date onlineTime) {
		this.onlineTime = onlineTime;
	}

	public String getExpTime() {
		return expTime;
	}

	public void setExpTime(String expTime) {
		this.expTime = expTime;
	}

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
        this.deviceNo = deviceNo == null ? null : deviceNo.trim();
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel == null ? null : deviceModel.trim();
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getShopLocation() {
        return shopLocation;
    }

    public void setShopLocation(String shopLocation) {
        this.shopLocation = shopLocation == null ? null : shopLocation.trim();
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName == null ? null : shopName.trim();
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

    public Integer getFacrotyId() {
        return facrotyId;
    }

    public void setFacrotyId(Integer facrotyId) {
        this.facrotyId = facrotyId;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName == null ? null : factoryName.trim();
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

	@Override
	public String toString() {
		return "Device [id=" + id + ", deviceToken=" + deviceToken + ", onlineTime=" + onlineTime + ", deviceNo="
				+ deviceNo + ", deviceModel=" + deviceModel + ", shopId=" + shopId + ", shopLocation=" + shopLocation
				+ ", shopName=" + shopName + ", proxyId=" + proxyId + ", proxyName=" + proxyName + ", facrotyId="
				+ facrotyId + ", factoryName=" + factoryName + ", status=" + status + ", createTime=" + createTime
				+ ", lastUpdate=" + lastUpdate + ", expTime=" + expTime + "]";
	}

	
}