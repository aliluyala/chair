package com.chair.manager.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chair.manager.bean.ReqParam;
import com.chair.manager.bean.ResponseResult;
import com.chair.manager.pojo.ConsumePackage;
import com.chair.manager.pojo.ConsumedDetails;
import com.chair.manager.pojo.Device;
import com.chair.manager.pojo.RechargePackage;
import com.chair.manager.pojo.RechargeRecord;
import com.chair.manager.service.ConsumePackageService;
import com.chair.manager.service.ConsumedDetailsService;
import com.chair.manager.service.DeviceService;
import com.chair.manager.service.RechargePackageService;
import com.chair.manager.service.RechargeRecordService;
import com.chair.manager.service.UserAccountService;
import com.chair.manager.service.UsersService;
import com.chair.manager.vo.ConsumePackageVo;
import com.chair.manager.vo.ConsumedDetailsVo;
import com.chair.manager.vo.RechargePackageVo;
import com.chair.manager.vo.RechargeRecordVo;

import redis.clients.jedis.JedisCluster;

@RequestMapping("/user")
@Controller
public class UsersController {
	private Logger logger = Logger.getLogger(UsersController.class);

	@Autowired
	private RechargeRecordService rechargeRecordService;
	@Autowired
	private ConsumedDetailsService consumedDetailsService;
	@Autowired
	private UsersService usersService;

	@Autowired
	private JedisCluster jedisCluster;

	@Autowired
	private RechargePackageService rechargePackageService;

	@Autowired
	private ConsumePackageService consumePackageService;

	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private DeviceService deviceService;
	
	/**
	 * 查询当前消费明细状态
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryCurConsumeStatus", method = RequestMethod.POST)
	private ResponseResult queryCurConsumeStatus(@RequestBody ReqParam param) {
		logger.info("------【查询当前消费明细状态】---参数>>>" + param);
		return new ResponseResult(consumedDetailsService.findById(param.getConsumerID()));
	}
	
	

	/**
	 * 查看用户注册状态
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryUserRegStatus", method = RequestMethod.POST)
	private ResponseResult queryUserStatus(@RequestBody ReqParam param) {
		logger.info("------【查询用户状态】---参数>>>" + param);
		return new ResponseResult(usersService.queryUserRegStatus(param.getOpenID()));
	}

	/**
	 * 发送用户验证码
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "sendCode", method = RequestMethod.POST)
	private ResponseResult sendCode(@RequestBody ReqParam param) {
		logger.info("------【发送验证码】---参数>>>" + param);
		usersService.sendCode(param.getPhoneNumber());
		return new ResponseResult(null);
	}

	
	/**
	 * 用户登录
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "login", method = RequestMethod.POST)
	private ResponseResult userLogin(@RequestBody ReqParam param) {
		logger.info("------【注册/登陆】---参数>>>" + param);
		return new ResponseResult(usersService.login(param.getOpenID(), param.getPhoneNumber(), param.getIdentCode()));
	}
	
	/**
	 * 查看用户充值明细
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryRechargeDetails", method = RequestMethod.POST)
	private ResponseResult queryRechargeDetails(@RequestBody ReqParam param) {
		logger.info("------【根据openID查询充值明细】---参数>>>" + param);
		RechargeRecord rr = new RechargeRecord();
		rr.setOpenId(param.getOpenID());
//		rr.setPhoneNumber(param.getPhoneNumber());
		rr.setPayStatus(2); // 已支付
		List<RechargeRecord> rechargeRecordList = rechargeRecordService.queryRechargeRecordList(rr);
		List<RechargeRecordVo> voList = new ArrayList<RechargeRecordVo>();
		for (int i = 0; i < rechargeRecordList.size(); i++) {
			if (rechargeRecordList.get(i).getPayStatus() != 2)
				continue;
			RechargeRecordVo rrv = new RechargeRecordVo();
			rrv.setRechargeAmount(rechargeRecordList.get(i).getRechargeAmount().toString());
			rrv.setRechargeDuration(rechargeRecordList.get(i).getRechargeDuration());
			rrv.setRechargeTime(
					new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rechargeRecordList.get(i).getCreateTime()));
			voList.add(rrv);
		}
		RechargeRecordVo res = new RechargeRecordVo();
		res.setOpenID(param.getOpenID());
		res.setPhoneNumber(param.getPhoneNumber());
		res.setRechargeList(voList);
		return new ResponseResult(res);
	}
	
	
	
	/**
	 * 查看用户消费明细
	 * @param param
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping(value = "queryConsumedDetails", method = RequestMethod.POST)
	private ResponseResult queryConsumedDetails(@RequestBody ReqParam param) {
		logger.info("------【查询消费明细】---参数>>>" + param);
		ConsumedDetails cd = new ConsumedDetails();
		cd.setOpenId(param.getOpenID());
		cd.setStatus(2);	//已消费
//		cd.setPhoneNumber(param.getPhoneNumber());
		List<ConsumedDetails> rs = consumedDetailsService.queryConsumedDetailsList(cd);
		ConsumedDetailsVo vo = new ConsumedDetailsVo();
		List<ConsumedDetailsVo> vos = new ArrayList<ConsumedDetailsVo>();
		for (ConsumedDetails consumed : rs) {
			ConsumedDetailsVo c = new ConsumedDetailsVo();
			c.setConsumedDuration(consumed.getConsumedDuration());
			c.setConsumedTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(consumed.getCreateTime()));
			vos.add(c);
		}
		vo.setOpenID(param.getOpenID());
		vo.setPhoneNumber(param.getPhoneNumber());
		vo.setConsumedList(vos);
		return new ResponseResult(vo);
	}

	
	/**
	 * 查询充值套餐列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryPackageList", method = RequestMethod.POST)
	private ResponseResult queryPackageList() {
		logger.info("------【查询充值套餐列表】------");
		List<RechargePackage> rechargePackages = rechargePackageService.queryRechargeListByLimit(new RechargePackage());
		List<RechargePackageVo> vos = new ArrayList<RechargePackageVo>();
		RechargePackageVo vors = new RechargePackageVo();
		for (int i = 0; i < rechargePackages.size(); i++) {
			RechargePackage rechargePackage = rechargePackages.get(i);
			RechargePackageVo vo = new RechargePackageVo();
			vo.setPackageID(rechargePackage.getId());
			vo.setPackageName(rechargePackage.getPackageName());
			vo.setPackageName(rechargePackage.getPackageName());
			vo.setRechargeAmount(rechargePackage.getRechargeAmoun().toString());
			vo.setRechargeDuration(rechargePackage.getRechargeDuration().toString());
			vos.add(vo);
		}
		vors.setPackageList(vos);
		return new ResponseResult(vors);
	}



	/**
	 * 扫描二维码QRCode查询设备信息
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryDevice", method = RequestMethod.POST)
	private ResponseResult queryDevice(@RequestBody ReqParam param) {
		logger.info("------【查询设备信息】---参数>>>" + param);
		Device d = new Device();
		d.setDeviceNo(param.getDeviceNO());
		Device device = deviceService.judgeDeviceIsUsed(d);
		return new ResponseResult(device);
	}
	
	/**
	 * 查询消费套餐列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryConsumerPackageList", method = RequestMethod.POST)
	private ResponseResult queryConsumerPackageList() {
		logger.info("------【查询消费套餐列表】------");
		List<ConsumePackage> consumePackages = consumePackageService.queryListByLimit(new ConsumePackage());
		ConsumePackageVo rsvo = new ConsumePackageVo();
		List<ConsumePackageVo> vos = new ArrayList<ConsumePackageVo>();
		System.err.println("-----消费套餐列表----" + consumePackages.size());
		for (ConsumePackage consumePackage : consumePackages) {
			ConsumePackageVo vo = new ConsumePackageVo();
			vo.setConsumedPackageID(consumePackage.getId());
			vo.setConsumedPackageName(consumePackage.getPackageName());
			vo.setConsumedPackageDuration(consumePackage.getConsumedDuration());
			vos.add(vo);
		}
		rsvo.setPackageList(vos);
		return new ResponseResult(rsvo);
	}

	/**
	 * 查询账户信息
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryAccountInfo", method = RequestMethod.POST)
	private ResponseResult queryAccountInfo(@RequestBody ReqParam param) {
		logger.info("------【查询账户信息】---参数>>>" + param);
		return new ResponseResult(userAccountService.queryAccountInfo(param.getOpenID(), param.getPhoneNumber()));
	}

	
	
	//TEST
	@ResponseBody
	@RequestMapping(value = "testRedis", method = RequestMethod.POST)
	private ResponseResult TestRedis(@RequestBody ReqParam param) {
		System.out.println("----" + param);
		String rs1 = jedisCluster.setex("bbb", 30, "12345555");
		String rs2 = jedisCluster.get("bbb");
		// String rs2 = redisService.set("13", "4321", 10);
		System.err.println("---rs1---" + rs1);
		System.err.println("---rs2---" + rs2);
		return new ResponseResult(null);
	}

}
