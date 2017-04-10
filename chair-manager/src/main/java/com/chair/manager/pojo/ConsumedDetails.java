package com.chair.manager.pojo;

import java.util.Date;

public class ConsumedDetails {
    private Integer id;

    private String phonenumber;

    private Integer consumedpackageid;

    private Integer consumedduration;

    private Integer factoryid;

    private String factoryname;

    private Integer proxyid;

    private String proxyname;

    private Integer shopid;

    private String shopname;

    private String shoplocation;

    private Integer deviceid;

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

    public Integer getConsumedpackageid() {
        return consumedpackageid;
    }

    public void setConsumedpackageid(Integer consumedpackageid) {
        this.consumedpackageid = consumedpackageid;
    }

    public Integer getConsumedduration() {
        return consumedduration;
    }

    public void setConsumedduration(Integer consumedduration) {
        this.consumedduration = consumedduration;
    }

    public Integer getFactoryid() {
        return factoryid;
    }

    public void setFactoryid(Integer factoryid) {
        this.factoryid = factoryid;
    }

    public String getFactoryname() {
        return factoryname;
    }

    public void setFactoryname(String factoryname) {
        this.factoryname = factoryname == null ? null : factoryname.trim();
    }

    public Integer getProxyid() {
        return proxyid;
    }

    public void setProxyid(Integer proxyid) {
        this.proxyid = proxyid;
    }

    public String getProxyname() {
        return proxyname;
    }

    public void setProxyname(String proxyname) {
        this.proxyname = proxyname == null ? null : proxyname.trim();
    }

    public Integer getShopid() {
        return shopid;
    }

    public void setShopid(Integer shopid) {
        this.shopid = shopid;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname == null ? null : shopname.trim();
    }

    public String getShoplocation() {
        return shoplocation;
    }

    public void setShoplocation(String shoplocation) {
        this.shoplocation = shoplocation == null ? null : shoplocation.trim();
    }

    public Integer getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(Integer deviceid) {
        this.deviceid = deviceid;
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