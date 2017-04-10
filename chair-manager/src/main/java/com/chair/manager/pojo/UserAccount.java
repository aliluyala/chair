package com.chair.manager.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class UserAccount {
    private Integer id;

    private String phonenumber;

    private BigDecimal amount;

    private Integer totalduration;

    private Integer usedduration;

    private Integer restduration;

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getTotalduration() {
        return totalduration;
    }

    public void setTotalduration(Integer totalduration) {
        this.totalduration = totalduration;
    }

    public Integer getUsedduration() {
        return usedduration;
    }

    public void setUsedduration(Integer usedduration) {
        this.usedduration = usedduration;
    }

    public Integer getRestduration() {
        return restduration;
    }

    public void setRestduration(Integer restduration) {
        this.restduration = restduration;
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