package com.chair.manager.pojo.dto;

import com.chair.manager.pojo.ConsumedDetails;

public class ConsumedDetailsDto extends ConsumedDetails {
	private String from;

	private String to;

	private Integer totalDuration;

	private Integer dayDuration;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public Integer getTotalDuration() {
		return totalDuration;
	}

	public void setTotalDuration(Integer totalDuration) {
		this.totalDuration = totalDuration;
	}

	public Integer getDayDuration() {
		return dayDuration;
	}

	public void setDayDuration(Integer dayDuration) {
		this.dayDuration = dayDuration;
	}

}