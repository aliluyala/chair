package com.chair.manager.service;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chair.manager.mapper.DeviceLogMapper;
import com.chair.manager.pojo.DeviceLog;

@Service
public class DeviceLogService extends BaseService<DeviceLog> {
	private static Logger logger = Logger.getLogger(DeviceLogService.class);
	@Autowired
	private DeviceLogMapper deviceLogMapper;
	
}
