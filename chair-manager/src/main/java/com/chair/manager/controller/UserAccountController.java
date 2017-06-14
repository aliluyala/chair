package com.chair.manager.controller;

import java.text.ParseException;
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
import com.chair.manager.pojo.DeviceLog;
import com.chair.manager.pojo.UserAccount;
import com.chair.manager.service.ConsumePackageService;
import com.chair.manager.service.ConsumedDetailsService;
import com.chair.manager.service.DeviceCommandLogService;
import com.chair.manager.service.DeviceLogService;
import com.chair.manager.service.DeviceService;
import com.chair.manager.service.UserAccountService;
import com.chair.manager.service.UsersService;

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
	@Autowired
	private UsersService userService;

	@Autowired
	private DeviceLogService deviceLogService;
	
	@Autowired
	private DeviceCommandLogService deviceCommandLogService;

	/**
	 * 充值
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "recharge", method = RequestMethod.POST)
	private ResponseResult recharge(@RequestBody ReqParam param) {
		logger.info("------【充值】参数------" + param);
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
		logger.info("------【查询预支付信息】参数------" + param);
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
		Device d = new Device();
		d.setDeviceNo(param.getDeviceNO());
		Device device = deviceService.judgeDeviceIsUsed(d);
		ConsumePackage consumePackage = consumePackageService.findById(param.getConsumedPackageID());
		if (consumePackage == null) {
			throw new ChairException("1000", "找不到设备或者消费套餐");
		}
		// 查询账户信息
		UserAccount userAccount = userAccountService.queryAccountInfo(param.getOpenID(), param.getPhoneNumber());
		int rest = userAccount.getRestDuration().intValue() - consumePackage.getConsumedDuration().intValue();
		if (rest < 0) {
			logger.error("账户【"+userAccount.getId()+"】余额不足");
			throw new ChairException("1011", "账户余额不足");
		}
		// 测试数据00000000000000000001和00000000000000000002，不需要推送消息
		boolean sendSuccess = false;
		if (!"00000000000000000001".equals(device.getDeviceNo())
				&& !"123".equals(device.getDeviceNo())) {
			// 发送消息给硬件启动设备， 同步任务
			Server server = new Server();
			SocketAction socketAction = server.new SocketAction();
			String toMessage = "*T0,001," + consumePackage.getConsumedDuration() + "#";
//			String ipAndPort = jedisCluster.get(device.getDeviceNo());
//			System.out.println("------【根据设备编号"+device.getDeviceNo()+"从redis.get()中获取ip】-----向客户端【" + ipAndPort+"】发送消息------" + toMessage);
//			String[] ips = ipAndPort.split(":");
//			sendSuccess = socketAction.send(ips[0], Integer.parseInt(ips[1]), device.getDeviceNo(), toMessage);
			sendSuccess = socketAction.send(device.getDeviceNo(), toMessage);
		}
		if(!sendSuccess && !"123".equals(device.getDeviceNo())){
			throw new ChairException("2003", "消息发送不成功");
		}
		
		//设备日志跟踪
		DeviceLog deviceLog = new DeviceLog();
		deviceLog.setDeviceNo(device.getDeviceNo());
		deviceLog.setDeviceStatus(3);	//正在使用
		deviceLog.setDeviceStatusDesc("正在使用");
		deviceLog.setCreateTime(new Date());
		deviceLog.setLastUpdate(new Date());
		deviceLogService.save(deviceLog);
		
		
		Date date = DateUtils.addMinute(new Date(), consumePackage.getConsumedDuration());
		String expTime = "";
		try {
			expTime = DateUtils.parseToFormatString(date,"yyyy-MM-dd HH:mm:ss");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 将过期时间写入设备表
		Device updateDevice = new Device();
		updateDevice.setId(device.getId());
		updateDevice.setExpTime(expTime);
		updateDevice.setStatus(3);	//设置为正在使用
		updateDevice.setLastUpdate(new Date());
		deviceService.updateSelective(updateDevice);
		
		// 新增消费明细（未消费）
		ConsumedDetails consumedDetails = new ConsumedDetails();
		consumedDetails.setOpenId(param.getOpenID());
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
		consumedDetails.setStatus(1);	//未消费
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
