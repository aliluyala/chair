package com.chair.manager.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class RechargePackage {
    private Integer id;

    private String packageName;

    private BigDecimal rechargeAmoun;

    private Integer rechargeDuration;

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

    public BigDecimal getRechargeAmoun() {
        return rechargeAmoun;
    }

    public void setRechargeAmoun(BigDecimal rechargeAmoun) {
        this.rechargeAmoun = rechargeAmoun;
    }

    public Integer getRechargeDuration() {
        return rechargeDuration;
    }

    public void setRechargeDuration(Integer rechargeDuration) {
        this.rechargeDuration = rechargeDuration;
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
}