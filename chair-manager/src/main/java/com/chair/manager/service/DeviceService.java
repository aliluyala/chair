package com.chair.manager.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.chair.manager.bean.EasyUIResult;
import com.chair.manager.bean.ResponseResult;
import com.chair.manager.controller.UsersController;
import com.chair.manager.exception.ChairException;
import com.chair.manager.mapper.DeviceMapper;
import com.chair.manager.pojo.Device;
import com.chair.manager.pojo.RechargePackage;
import com.chair.manager.vo.DeviceVo;
import com.github.pagehelper.PageInfo;

import redis.clients.jedis.JedisCluster;

@Service
public class DeviceService extends BaseService<Device> {
	private static Logger logger = Logger.getLogger(DeviceService.class);
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
			logger.error("------设备不存在数据库------");
			throw new ChairException("2001", "查询不到设备信息");
		}
		String ipAndPort = jedisCluster.get(device.getDeviceNo());
		//2.判断设备是否开启，发消息给硬件
		if(StringUtils.isEmpty(ipAndPort)){
			logger.error("------通过【"+device.getDeviceNo()+"】在redis中找不到对应的设备IP地址------");
			throw new ChairException("2001", "查询不到设备信息");
		}
		return true;
	}
	
	
	/**
	 * 查询设备列表
	 */
	public DeviceVo queryDeviceList() {
		List<Device> devices =  deviceMapper.select(null);
		if(devices.size() <= 0){
			logger.info("查询不到设备列表");
			return null;
		}
		List<DeviceVo> deviceVos = new ArrayList<DeviceVo>();
		DeviceVo vos = new DeviceVo();
		for (Device device : devices) {
			DeviceVo vo = new DeviceVo();
			vo.setDeviceID(device.getId());
			vo.setDeviceNO(device.getDeviceNo());
			vo.setDeviceModel(device.getDeviceModel());
			vo.setShopID(device.getShopId());
			vo.setShopLocation(device.getShopLocation());
			vo.setShopName(device.getShopName());
			vo.setProxyID(device.getProxyId());
			vo.setProxyName(device.getProxyName());
			vo.setFacrotyID(device.getFacrotyId());
			vo.setFactoryName(device.getFactoryName());
			vo.setStatus(device.getStatus());
			vo.setCreateTime(device.getCreateTime().toString());
			vo.setLastUpdate(device.getLastUpdate().toString());
			deviceVos.add(vo);
		}
		vos.setDeviceList(deviceVos);
		return vos;
	}

	/**
	 * 分页查询设备信息
	 * @param page 页面
	 * @param rows 页面大小
	 * @return
	 */
	public EasyUIResult queryDeviceListForPage(Integer page, Integer rows) {
		PageInfo<Device> pageInfo= super.queryListPage(new Device(), page, rows);
		return new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
	}

}
