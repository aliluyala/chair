package com.chair.manager.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chair.manager.exception.ChairException;
import com.chair.manager.mapper.UsersMapper;
import com.chair.manager.pojo.ConsumePackage;
import com.chair.manager.pojo.Users;
import com.chair.manager.sms.BatchPublishSMSMessage;
import com.chair.manager.vo.ConsumePackageVo;
import com.chair.manager.vo.UserVo;

import redis.clients.jedis.JedisCluster;

@Service
public class UsersService extends BaseService<Users> {
	private Logger logger = Logger.getLogger(UsersService.class);

	@Autowired
	private UsersMapper usersMapper;
	@Autowired
	private ConsumePackageService consumePackageService;

	@Autowired
	private JedisCluster jedisCluster;

	private static final int EXPIRE = 5 * 60;// 短信验证码失效时间

	/**
	 * 新增或更新
	 * 
	 * @param user
	 */
	public void saveOrUpdate(Users user) {
		usersMapper.saveOrUpdate(user);
	}

	/**
	 * 发送短信验证码
	 * 
	 * @param phoneNumber
	 */
	public boolean sendCode(String phoneNumber) {
		boolean b = true;
		// 1.生成四位数验证码
		String code = createCode();
		// TODO 调用短信接口发送验证码
		Map<String, String> templateMap = new HashMap<String, String>();
		templateMap.put("code", code);
		templateMap.put("product", "H5555-TEST");
		List<String> recevierList = new ArrayList<String>();
		recevierList.add(phoneNumber);
		BatchPublishSMSMessage sms= new BatchPublishSMSMessage();
		sms.sendSMS(templateMap, recevierList);
		// 3.redis存储手机号和验证码，5分钟失效
		String rs = jedisCluster.setex(phoneNumber, EXPIRE, code);
		logger.info("---redis集群保存" + phoneNumber + "的验证码" + code + "结果：" + rs);
		return b;
	}

	/**
	 * 生成验证码
	 * 
	 * @return
	 */
	private String createCode() {
		String code = "";
		Random random = new Random();
		for (int i = 0; i < 4; i++) {
			code += random.nextInt(9) + "";
		}
		return code;
	}

	/**
	 * 用户登录
	 * 
	 * @param phoneNumber
	 * @param identCode
	 */
	public UserVo login(String phoneNumber, Integer identCode) {
		// 1.验证登陆信息
		if (!identCode.toString().equals(jedisCluster.get(phoneNumber))) {
			throw new ChairException("1000", "验证码验证失败");
		}
		// 2.查询存在则更新，不存在则新增
		Users user = new Users();
		user.setPhoneNumber(phoneNumber);
		user.setCreateTime(new Date());
		user.setLastUpdate(new Date());
		logger.debug("---添加或者更新用户表【前】--：" + user);
		this.saveOrUpdate(user);
		logger.debug("---添加或者更新用户表【后】--：" + user);

		List<ConsumePackageVo> ulist = new ArrayList<ConsumePackageVo>();
		// 3.查询消费套餐列表
		List<ConsumePackage> consumePackages = consumePackageService.queryList(new ConsumePackage());
		System.err.println("-----消费套餐列表----" + consumePackages.size());
		for (ConsumePackage consumePackage : consumePackages) {
			ConsumePackageVo cpvo = new ConsumePackageVo();
			cpvo.setConsumedPackageID(consumePackage.getId());
			cpvo.setConsumedPackageName(consumePackage.getPackageName());
			cpvo.setConsumedPackageDuration(consumePackage.getConsumedDuration());
			ulist.add(cpvo);
		}

		UserVo uvo = new UserVo();
		uvo.setPhoneNumber(phoneNumber);
		uvo.setUserID(user.getId());
		uvo.setPackageList(ulist);

		return uvo;

	}

}
