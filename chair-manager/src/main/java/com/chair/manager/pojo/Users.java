package com.chair.manager.pojo;

import java.util.Date;

import javax.persistence.Table;

@Table(name="users")
public class Users {
    private Integer id;

    private String phoneNumber;

    private Date createTime;

    private Date lastUpdate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
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
		return "Users [id=" + id + ", phoneNumber=" + phoneNumber + ", createTime=" + createTime + ", lastUpdate="
				+ lastUpdate + "]";
	}
    
    
}