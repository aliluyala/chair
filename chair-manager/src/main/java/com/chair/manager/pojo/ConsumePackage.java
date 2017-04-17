package com.chair.manager.pojo;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "consumed_package")
public class ConsumePackage {
	@Id
	private Integer id;

	private String packageName;

	private Integer consumedDuration;

	private Integer status;

	private Date createTime;

	private Date lastUpdate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName == null ? null : packageName.trim();
	}

	public Integer getConsumedDuration() {
		return consumedDuration;
	}

	public void setConsumedDuration(Integer consumedDuration) {
		this.consumedDuration = consumedDuration;
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
		return "ConsumePackage [id=" + id + ", packageName=" + packageName + ", consumedDuration=" + consumedDuration
				+ ", status=" + status + ", createTime=" + createTime + ", lastUpdate=" + lastUpdate + "]";
	}

}