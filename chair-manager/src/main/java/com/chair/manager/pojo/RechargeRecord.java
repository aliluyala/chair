package com.chair.manager.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class RechargeRecord {
    private Integer id;

    private String batchno;

    private String phonenumber;

    private Integer rechargepackageid;

    private BigDecimal rechargeamount;

    private Integer rechargeduration;

    private Date rechargetime;

    private String payaccount;

    private Integer paymethod;

    private Integer paystatus;

    private Date createtime;

    private Date lastupdate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBatchno() {
        return batchno;
    }

    public void setBatchno(String batchno) {
        this.batchno = batchno == null ? null : batchno.trim();
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber == null ? null : phonenumber.trim();
    }

    public Integer getRechargepackageid() {
        return rechargepackageid;
    }

    public void setRechargepackageid(Integer rechargepackageid) {
        this.rechargepackageid = rechargepackageid;
    }

    public BigDecimal getRechargeamount() {
        return rechargeamount;
    }

    public void setRechargeamount(BigDecimal rechargeamount) {
        this.rechargeamount = rechargeamount;
    }

    public Integer getRechargeduration() {
        return rechargeduration;
    }

    public void setRechargeduration(Integer rechargeduration) {
        this.rechargeduration = rechargeduration;
    }

    public Date getRechargetime() {
        return rechargetime;
    }

    public void setRechargetime(Date rechargetime) {
        this.rechargetime = rechargetime;
    }

    public String getPayaccount() {
        return payaccount;
    }

    public void setPayaccount(String payaccount) {
        this.payaccount = payaccount == null ? null : payaccount.trim();
    }

    public Integer getPaymethod() {
        return paymethod;
    }

    public void setPaymethod(Integer paymethod) {
        this.paymethod = paymethod;
    }

    public Integer getPaystatus() {
        return paystatus;
    }

    public void setPaystatus(Integer paystatus) {
        this.paystatus = paystatus;
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