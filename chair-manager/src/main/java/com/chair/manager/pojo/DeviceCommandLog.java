package com.chair.manager.pojo;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 设备命令跟踪日志
 * 
 * @author yaoym
 * @since 2017-06-14
 */
@Table(name = "device_command_log")
public class DeviceCommandLog {

	@Id
	private Integer id;

	private String deviceNo;

	private Integer commandType;

	private String commandDesc;

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

	public Integer getCommandType() {
		return commandType;
	}

	public void setCommandType(Integer commandType) {
		this.commandType = commandType;
	}

	public String getCommandDesc() {
		return commandDesc;
	}

	public void setCommandDesc(String commandDesc) {
		this.commandDesc = commandDesc;
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
		return "DeviceCommandLog [id=" + id + ", deviceNo=" + deviceNo + ", commandType=" + commandType
				+ ", commandDesc=" + commandDesc + ", createTime=" + createTime + ", lastUpdate=" + lastUpdate + "]";
	}

}