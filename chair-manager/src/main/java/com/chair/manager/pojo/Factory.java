package com.chair.manager.pojo;

import java.util.Date;

public class Factory {
    private Integer id;

    private String factoryName;

    private Date createTime;

    private Date lastUpdat;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName == null ? null : factoryName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdat() {
        return lastUpdat;
    }

    public void setLastUpdat(Date lastUpdat) {
        this.lastUpdat = lastUpdat;
    }
}