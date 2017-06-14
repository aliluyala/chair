package com.chair.manager.service;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chair.manager.bean.EasyUIResult;
import com.chair.manager.mapper.DeviceCommandLogMapper;
import com.chair.manager.pojo.DeviceCommandLog;
import com.chair.manager.pojo.DeviceLog;
import com.github.pagehelper.PageInfo;

@Service
public class DeviceCommandLogService extends BaseService<DeviceCommandLog> {
	private static Logger logger = Logger.getLogger(DeviceCommandLogService.class);
	@Autowired
	private DeviceCommandLogMapper deviceCommandLogMapper;
	
	
	public EasyUIResult queryDeviceCommandListForPage(DeviceCommandLog deviceCommand, Integer page, Integer rows) {
		PageInfo<DeviceCommandLog> pageInfo = super.queryListPage(deviceCommand, page, rows);
		return new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
	}
	
}
