package com.chair.manager.controller;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chair.manager.bean.ReqParam;
import com.chair.manager.bean.ResponseResult;
import com.chair.manager.exception.ChairException;
import com.chair.manager.keepalive.Server;
import com.chair.manager.keepalive.Server.ConnWatchDog;
import com.chair.manager.keepalive.Server.SocketAction;
import com.chair.manager.pojo.ConsumePackage;
import com.chair.manager.pojo.ConsumedDetails;
import com.chair.manager.pojo.Device;
import com.chair.manager.pojo.UserAccount;
import com.chair.manager.service.ConsumePackageService;
import com.chair.manager.service.ConsumedDetailsService;
import com.chair.manager.service.DeviceService;
import com.chair.manager.service.UserAccountService;

import redis.clients.jedis.JedisCluster;

@RequestMapping("/account")
@Controller
public class UserAccountController {
	private static Logger logger = Logger.getLogger(UsersController.class);

	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private ConsumePackageService consumePackageService;
	@Autowired
	private ConsumedDetailsService consumedDetailsService;
	@Autowired
	private JedisCluster jedisCluster;

	/**
	 * 充值
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "recharge", method = RequestMethod.POST)
	private ResponseResult recharge(@RequestBody ReqParam param) {
		System.err.println("------【充值】参数------" + param);
		return new ResponseResult(userAccountService.recharge(param.getPhoneNumber(), param.getPackageID(),
				param.getOpenID(), param.getBatchNO()));
	}

	/**
	 * 查询预支付信息
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryPrepayInfo", method = RequestMethod.POST)
	private ResponseResult queryPrepayInfo(@RequestBody ReqParam param) {
		System.err.println("------【查询预支付信息】参数------" + param);
		return new ResponseResult(userAccountService.queryPrepayInfo(param.getPhoneNumber(), param.getBatchNO()));
	}

	/**
	 * 用户启用设备，选择消费套餐
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "choosePackage", method = RequestMethod.POST)
	private ResponseResult choosePackage(@RequestBody ReqParam param) {
		logger.info("---【用户启用设备，选择对应的消费套餐】，入参为--->>>" + param);
		// 根据设备ID查询设备信息
		Device d = new Device();
		d.setDeviceNo(param.getDeviceNO());
		Device device = deviceService.queryByDeviceNO(d);
		logger.info("---根据设备NO：【" + param.getDeviceNO() + "】查询设备信息--->>>" + device);
		boolean useAble = deviceService.isUsed(jedisCluster, device);
		System.out.println("---useAble---" + useAble);
		if (!useAble)
			return new ResponseResult("2001", "设备" + param.getDeviceNO() + "找不到设备", null);
		// 查询套餐信息
		ConsumePackage consumePackage = consumePackageService.findById(param.getConsumedPackageID());
		logger.info("---根据用户选择的消费套餐ID：【" + param.getConsumedPackageID() + "】查询消费套餐信息--->>>" + consumePackage);
		if (device == null || consumePackage == null) {
			throw new ChairException("1000", "找不到设备或者消费套餐");
		}
		// 测试数据00000000000000000001和00000000000000000002，不需要推送消息
		if (!"00000000000000000001".equals(device.getDeviceNo())
				&& !"00000000000000000002".equals(device.getDeviceNo())) {
			// 发送消息给硬件启动设备， 同步任务
			Server server = new Server();
			SocketAction socketAction = server.new SocketAction();
			String toMessage = "*T0,001," + consumePackage.getConsumedDuration() + "#";
			String token = jedisCluster.get(device.getDeviceNo());
			if (StringUtils.isEmpty(token)) {
				return new ResponseResult("2001", "根据设备编号" + param.getDeviceNO() + "找不到启动的设备。", null);
			}
			String ipAndPort = jedisCluster.get(token);
			System.out.println("------ipAndPort----" + ipAndPort);
			String[] ips = ipAndPort.split(":");
			System.err.println("------向客户端 " + ipAndPort + " 发送消息------" + toMessage);
			socketAction.send(ips[0], Integer.parseInt(ips[1]), toMessage);
		}

		// 查询账户信息
		UserAccount userAccount = userAccountService.queryAccountInfo(param.getOpenID(), param.getPhoneNumber());
		int rest = userAccount.getRestDuration().intValue() - consumePackage.getConsumedDuration().intValue();
		if (rest < 0) {
			return new ResponseResult("1011", "账户余额不足", null);
		}
		// 设置消费明细对象
		ConsumedDetails consumedDetails = new ConsumedDetails();
		consumedDetails.setPhoneNumber(param.getPhoneNumber());
		consumedDetails.setConsumedPackageId(consumePackage.getId());
		consumedDetails.setConsumedDuration(consumePackage.getConsumedDuration());
		consumedDetails.setFactoryId(device.getFacrotyId());
		consumedDetails.setFactoryName(device.getFactoryName());
		consumedDetails.setProxyId(device.getProxyId());
		consumedDetails.setProxyName(device.getProxyName());
		consumedDetails.setShopId(device.getShopId());
		consumedDetails.setShopLocation(device.getShopLocation());
		consumedDetails.setShopName(device.getShopName());
		consumedDetails.setDeviceId(device.getId());
		consumedDetails.setConsumedTime(new Date());
		consumedDetails.setCreateTime(new Date());
		consumedDetails.setLastUpdate(new Date());
		// 保存消费明细信息
		int rs = consumedDetailsService.save(consumedDetails);

		// 更新账户信息
		userAccount.setUsedDuration(userAccount.getUsedDuration() + consumePackage.getConsumedDuration());
		userAccount.setRestDuration(userAccount.getRestDuration() - consumePackage.getConsumedDuration());
		
		userAccountService.updateSelective(userAccount);
		
		
		
		logger.info("---保存消费明细结果--->>>" + rs);
		return new ResponseResult(null);
	}

}
