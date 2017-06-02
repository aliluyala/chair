package com.chair.manager.pojo;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "card")
public class Card {
	@Id
	private Integer id;

	private Integer duration;

	private Integer status;

	private Date createTime;

	private Date lastUpdate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
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
		return "card [id=" + id + ", duration=" + duration + ", status=" + status + ", createTime=" + createTime
				+ ", lastUpdate=" + lastUpdate + "]";
	}

}