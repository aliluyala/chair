package com.chair.manager.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class UserAccount {
    private Integer id;

    private String phoneNumber;

    private BigDecimal amount;

    private Integer totalDuration;

    private Integer usedDuration;

    private Integer restDuration;

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(Integer totalDuration) {
        this.totalDuration = totalDuration;
    }

    public Integer getUsedDuration() {
        return usedDuration;
    }

    public void setUsedDuration(Integer usedDuration) {
        this.usedDuration = usedDuration;
    }

    public Integer getRestDuration() {
        return restDuration;
    }

    public void setRestDuration(Integer restDuration) {
        this.restDuration = restDuration;
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