package com.chair.manager.mapper;

import com.chair.manager.mapper.plugin.ChairMapper;
import com.chair.manager.pojo.Device;

public interface DeviceMapper extends ChairMapper<Device> {

	Device queryByDeviceNO(Device d);

	void saveOrUpdate(Device device);
}
