package com.chair.manager.pojo;

import java.util.Date;

public class Users {
    private Integer id;

    private String phonenumber;

    private Date createtime;

    private Date lastupdate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber == null ? null : phonenumber.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(Date lastupdate) {
        this.lastupdate = lastupdate;
    }

	@Override
	public String toString() {
		return "Users [id=" + id + ", phonenumber=" + phonenumber + ", createtime=" + createtime + ", lastupdate="
				+ lastupdate + "]";
	}
    
}