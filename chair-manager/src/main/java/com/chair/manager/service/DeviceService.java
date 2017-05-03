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

	public Device findByUnique(Device device) {
		List<Device> devices = deviceMapper.select(device);
		if (devices != null && devices.size() > 0) {
			return devices.get(0);
		} else
			return null;
	}

	public Device queryByDeviceNO(Device d) {
		return deviceMapper.queryByDeviceNO(d);
	}
	
	/**
	 * 判断设备是否可用
	 * @param device
	 * @return true：可用， false：不可用
	 */
	public boolean isUsed(Device device) {
		//1.判断设备是否存在
		if(device == null) return false;
		//2.判断设备是否开启，发消息给硬件
		return true;
	}

}
