package com.chair.manager.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chair.manager.bean.ReqParam;
import com.chair.manager.bean.ResponseResult;
import com.chair.manager.exception.ChairException;
import com.chair.manager.keepalive.Server;
import com.chair.manager.keepalive.Server.SocketAction;
import com.chair.manager.pojo.ConsumePackage;
import com.chair.manager.pojo.ConsumedDetails;
import com.chair.manager.pojo.Device;
import com.chair.manager.pojo.DeviceLog;
import com.chair.manager.pojo.UserAccount;
import com.chair.manager.pojo.dto.TempDto;
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
			logger.info("------------------------deviceCommandLogService-------------------------------------------------------"+deviceCommandLogService);
			sendSuccess = socketAction.send(deviceCommandLogService, device.getDeviceNo(), toMessage);
		}
		if(!sendSuccess && !"123".equals(device.getDeviceNo())){
			throw new ChairException("2003", "消息发送不成功");
		}
		
		
		
		
		/*
		Vector obj = MyVector.getVector();
		System.err.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"-----线程阻塞开始0----");
		logger.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"-----线程阻塞开始0----");
		
		//TODO 线程阻塞
		synchronized (obj){
			try {
                if (obj.size() == 0) {
                    obj.wait();
                }
                String respsStatus = (String) obj.get(0);
                if("1".equals(respsStatus)){
        			throw new ChairException("2004", "设备启动失败");
                }
                obj.remove(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
		}
		
		System.err.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"-----线程阻塞结束----");
		logger.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"-----线程阻塞结束----");
		*/
		// 新增消费明细（待消费）
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
		
		//将数据保存到内存
		Map<String, TempDto> map = MyVector.getMap();
		TempDto tempDto = map.get(device.getDeviceNo());
		
		if(tempDto != null){
			if(new Date().getTime() > ( tempDto.getDelayTime() + 60 * 1000)){
				map.remove(device.getDeviceNo());
				tempDto = new TempDto();
				tempDto.setAccountID(userAccount.getId());
				tempDto.setConsumerID(rs);
				tempDto.setDelayTime(new Date().getTime());
				
			}else{
				throw new ChairException("2005", "请稍后再试");
			}
		}else{
			tempDto = new TempDto();
			tempDto.setAccountID(userAccount.getId());
			tempDto.setConsumerID(rs);
			tempDto.setDelayTime(new Date().getTime());
		}
		
		map.put(device.getDeviceNo(), tempDto);
		
		// 更新账户信息
		/*userAccount.setUsedDuration(userAccount.getUsedDuration() + consumePackage.getConsumedDuration());
		userAccount.setRestDuration(userAccount.getRestDuration() - consumePackage.getConsumedDuration());
		userAccount.setLastUpdate(new Date());
		userAccountService.updateSelective(userAccount);
		logger.info("---保存消费明细结果--->>>" + rs);*/
		return new ResponseResult(null);
	}

}
