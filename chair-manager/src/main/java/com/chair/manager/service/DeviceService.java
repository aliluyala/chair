package com.chair.manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.chair.manager.bean.ResponseResult;
import com.chair.manager.mapper.DeviceMapper;
import com.chair.manager.pojo.Device;

import redis.clients.jedis.JedisCluster;

@Service
public class DeviceService extends BaseService<Device> {
	@Autowired
	private DeviceMapper deviceMapper;
	
	/**
	 * 根据唯一约束查询设备
	 * @param device
	 * @return
	 */
	public Device findByUnique(Device device) {
		List<Device> devices = deviceMapper.select(device);
		if (devices != null && devices.size() > 0) {
			return devices.get(0);
		} else
			return null;
	}
	
	/**
	 * 根据设备编号查询设备
	 * @param d
	 * @return
	 */
	public Device queryByDeviceNO(Device d) {
		return deviceMapper.queryByDeviceNO(d);
	}
	
	/**
	 * 新增或更新设备
	 * @param device
	 * @return
	 */
	public void saveOrUpdate(Device device){
		deviceMapper.saveOrUpdate(device);
	}
	
	
	/**
	 * 判断设备是否可用
	 * @param device
	 * @return true：可用， false：不可用
	 */
	public boolean isUsed(JedisCluster jedisCluster, Device device) {
		if("00000000000000000001".equals(device.getDeviceNo()) || "00000000000000000002".equals(device.getDeviceNo())  ){
			System.out.println("---测试数据---");
			return true;
		}
		//1.判断设备是否存在
		if(device == null) {
			System.out.println("------设备不存在数据库------");
			return false;
		}
		String ipAndPort = jedisCluster.get(device.getDeviceNo());
		//2.判断设备是否开启，发消息给硬件
		if(StringUtils.isEmpty(ipAndPort)){
			System.out.println("------通过【"+device.getDeviceNo()+"】在redis中找不到对应的设备IP地址------");
			return false;
		}
		return true;
	}

}
