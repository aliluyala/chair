package com.chair.manager.service;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chair.manager.mapper.DeviceCommandLogMapper;
import com.chair.manager.pojo.DeviceCommandLog;

@Service
public class DeviceCommandLogService extends BaseService<DeviceCommandLog> {
	private static Logger logger = Logger.getLogger(DeviceCommandLogService.class);
	@Autowired
	private DeviceCommandLogMapper deviceCommandLogMapper;
	
}
