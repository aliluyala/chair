package com.chair.manager.pojo;

import java.util.Date;

public class Device {
    private Integer id;

    private String deviceno;

    private String devicemodel;

    private Integer shopid;

    private String shoplocation;

    private String shopname;

    private Integer proxyid;

    private String proxyname;

    private Integer facrotyid;

    private String factoryname;

    private Integer status;

    private Date createtime;

    private Date lastupdate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceno() {
        return deviceno;
    }

    public void setDeviceno(String deviceno) {
        this.deviceno = deviceno == null ? null : deviceno.trim();
    }

    public String getDevicemodel() {
        return devicemodel;
    }

    public void setDevicemodel(String devicemodel) {
        this.devicemodel = devicemodel == null ? null : devicemodel.trim();
    }

    public Integer getShopid() {
        return shopid;
    }

    public void setShopid(Integer shopid) {
        this.shopid = shopid;
    }

    public String getShoplocation() {
        return shoplocation;
    }

    public void setShoplocation(String shoplocation) {
        this.shoplocation = shoplocation == null ? null : shoplocation.trim();
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname == null ? null : shopname.trim();
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

    public Integer getFacrotyid() {
        return facrotyid;
    }

    public void setFacrotyid(Integer facrotyid) {
        this.facrotyid = facrotyid;
    }

    public String getFactoryname() {
        return factoryname;
    }

    public void setFactoryname(String factoryname) {
        this.factoryname = factoryname == null ? null : factoryname.trim();
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