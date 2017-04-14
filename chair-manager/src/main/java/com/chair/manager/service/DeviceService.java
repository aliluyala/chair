package com.chair.manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chair.manager.mapper.DeviceMapper;
import com.chair.manager.pojo.Device;

@Service
public class DeviceService extends BaseService<Device> {
	@Autowired
	private DeviceMapper deviceMapper;
	public Device findByUnique(Device device){
		List<Device> devices=	deviceMapper.select(device);
		if(devices!=null&&devices.size()>0){
			return devices.get(0);
		}
		else
			return null;
	}

}
