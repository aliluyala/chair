package com.chair.manager.pojo;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 设备状态跟踪日志
 * @author yaoym
 * @since 2017-06-14
 */
@Table(name = "device_log")
public class DeviceLog {

	@Id
	private Integer id;

	private String deviceNo;

	private Integer deviceStatus;

	private String deviceStatusDesc;

	private Date createTime;

	private Date lastUpdate;

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

	public Integer getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(Integer deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	public String getDeviceStatusDesc() {
		return deviceStatusDesc;
	}

	public void setDeviceStatusDesc(String deviceStatusDesc) {
		this.deviceStatusDesc = deviceStatusDesc;
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
		return "DeviceLog [id=" + id + ", deviceNo=" + deviceNo + ", deviceStatus=" + deviceStatus
				+ ", deviceStatusDesc=" + deviceStatusDesc + ", createTime=" + createTime + ", lastUpdate=" + lastUpdate
				+ "]";
	}

}