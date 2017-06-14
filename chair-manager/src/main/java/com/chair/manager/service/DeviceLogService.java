package com.chair.manager.service;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chair.manager.bean.EasyUIResult;
import com.chair.manager.mapper.DeviceLogMapper;
import com.chair.manager.pojo.DeviceLog;
import com.github.pagehelper.PageInfo;

@Service
public class DeviceLogService extends BaseService<DeviceLog> {
	private static Logger logger = Logger.getLogger(DeviceLogService.class);
	@Autowired
	private DeviceLogMapper deviceLogMapper;
	
	
	/**
	 * 分页查询设备日志信息
	 * @param page 页面
	 * @param rows 页面大小
	 * @return
	 */
	public EasyUIResult queryDeviceLogListForPage(DeviceLog deviceLog ,Integer page, Integer rows) {
		PageInfo<DeviceLog> pageInfo = super.queryListPage(deviceLog, page, rows);
		return new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
	}
	
}
