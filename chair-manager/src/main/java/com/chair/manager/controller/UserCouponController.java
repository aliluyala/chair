package com.chair.manager.controller;

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
import com.chair.manager.pojo.UserCoupon;
import com.chair.manager.service.UserCouponService;



@RequestMapping("/coupon")
@Controller
public class UserCouponController {
	private Logger logger = Logger.getLogger(UserCouponController.class);

	@Autowired
	private UserCouponService couponService;
	/**
	 * 查询优惠券列表
	 * @param openID 用户ID
	 * @param phoneNumber 用户手机号
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="queryCouponList",method=RequestMethod.POST)
	private ResponseResult queryCouponList(@RequestBody ReqParam param){
		logger.info("------【查询用户优惠券列表】---参数>>>"+param);
		UserCoupon coupon = new UserCoupon();
		coupon.setOpenId(param.getOpenID());
		List<UserCoupon> coupons = couponService.queryCouponList(coupon);
		return new ResponseResult(coupons);
	}
	
}
