package com.chair.manager.vo;

import java.io.Serializable;
import java.util.List;

public class RechargePackageVo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private int packageID;
  private String packageName;
  private String rechargeAmount;
  private String rechargeDuration;
  private List<RechargePackageVo> packageList;

  public int getPackageID()
  {
    return this.packageID;
  }

  public void setPackageID(int packageID) {
    this.packageID = packageID;
  }

  public String getPackageName() {
    return this.packageName;
  }

  public void setPackageName(String packageName) {
    this.packageName = packageName;
  }

  public String getRechargeAmount() {
    return this.rechargeAmount;
  }

  public void setRechargeAmount(String rechargeAmount) {
    this.rechargeAmount = rechargeAmount;
  }

  public String getRechargeDuration() {
    return this.rechargeDuration;
  }

  public void setRechargeDuration(String rechargeDuration) {
    this.rechargeDuration = rechargeDuration;
  }

  public List<RechargePackageVo> getPackageList() {
    return this.packageList;
  }

  public void setPackageList(List<RechargePackageVo> packageList) {
    this.packageList = packageList;
  }
}