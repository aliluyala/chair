package com.chair.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chair.manager.mapper.DeviceMapper;
import com.chair.manager.pojo.Device;

@Service
public class DeviceService extends BaseService<Device> {
	
	@Autowired
	private DeviceMapper deviceMapper;
	
	
	public Device findByUnique(Device device){
		 return (Device) deviceMapper.select(device);
	}
	
}
