package com.chair.manager.vo;

import java.io.Serializable;
import java.util.List;

public class ConsumePackageVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer consumedPackageID;

	private String consumedPackageName;

	private Integer consumedPackageDuration;
	
	private List<ConsumePackageVo> packageList;
	

	public Integer getConsumedPackageID() {
		return consumedPackageID;
	}

	public void setConsumedPackageID(Integer consumedPackageID) {
		this.consumedPackageID = consumedPackageID;
	}

	public String getConsumedPackageName() {
		return consumedPackageName;
	}

	public void setConsumedPackageName(String consumedPackageName) {
		this.consumedPackageName = consumedPackageName;
	}

	public Integer getConsumedPackageDuration() {
		return consumedPackageDuration;
	}

	public void setConsumedPackageDuration(Integer consumedPackageDuration) {
		this.consumedPackageDuration = consumedPackageDuration;
	}

	public List<ConsumePackageVo> getPackageList() {
		return packageList;
	}

	public void setPackageList(List<ConsumePackageVo> packageList) {
		this.packageList = packageList;
	}
	

}