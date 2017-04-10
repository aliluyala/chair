package com.chair.manager.pojo;

import java.util.Date;

public class ConsumePackage {
    private Integer id;

    private String packagename;

    private Integer consumedduration;

    private Integer status;

    private Date createtime;

    private Date lastupdate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename == null ? null : packagename.trim();
    }

    public Integer getConsumedduration() {
        return consumedduration;
    }

    public void setConsumedduration(Integer consumedduration) {
        this.consumedduration = consumedduration;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
}