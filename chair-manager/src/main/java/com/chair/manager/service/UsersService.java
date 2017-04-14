package com.chair.manager.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chair.manager.mapper.UsersMapper;
import com.chair.manager.pojo.ConsumePackage;
import com.chair.manager.pojo.Users;
import com.chair.manager.vo.ConsumePackageVo;
import com.chair.manager.vo.UserVo;

@Service
public class UsersService extends BaseService<Users> {
	private Logger logger = Logger.getLogger(UsersService.class);

	
	@Autowired
	private UsersMapper usersMapper;
	@Autowired
	private ConsumePackageService  consumePackageService;
	
	/**
	 * 新增或更新
	 * @param user
	 */
	public void saveOrUpdate(Users user){
		usersMapper.saveOrUpdate(user);
	}

	/**
	 * 发送短信验证码
	 * @param phoneNumber
	 */
	public String sendCode(String phoneNumber) {
		//生成四位数验证码
		String code = createCode();
		//TODO 调用短信接口发送验证码
		return "success";
	}
	
	/**
	 * 生成验证码
	 * @return
	 */
	private String createCode() {
		String code = "";
		Random random = new Random();
		for (int i = 0; i < 4; i++) {
			code += random.nextInt(9)+"";
		}
		return code;
	}

	
	/**
	 * 用户登录
	 * @param phoneNumber
	 * @param identCode
	 */
	public UserVo login(String phoneNumber, Integer identCode) {
		//TODO 1.验证登陆信息
		String  sendResult = sendCode(phoneNumber);
		if(!"success".equalsIgnoreCase(sendResult)){
			logger.error("短信发送失败。");
			return null;
		}
		
		//2.查询存在则更新，不存在则新增
		Users user=new Users();
		user.setPhoneNumber(phoneNumber);
		user.setCreateTime(new Date());
		user.setLastUpdate(new Date());
		logger.debug("---添加或者更新用户表【前】--："+user);
		this.saveOrUpdate(user);
		logger.debug("---添加或者更新用户表【后】--："+user);
		
		List<ConsumePackageVo> ulist = new ArrayList<ConsumePackageVo>();
		//3.查询消费套餐列表
		List<ConsumePackage> consumePackages = consumePackageService.queryList(new ConsumePackage());
		System.err.println("-----消费套餐列表----"+consumePackages.size());
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
