package com.chair.manager.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chair.manager.mapper.userCouponMapper;
import com.chair.manager.pojo.UserCoupon;

@Service
public class UserCouponService extends BaseService<UserCoupon> {
	private Logger logger = Logger.getLogger(UserCouponService.class);

	@Autowired
	private userCouponMapper couponMapper;

	/**
	 * 查询优惠券列表
	 * @param coupon
	 * @return
	 */
	public List<UserCoupon> queryCouponList(UserCoupon coupon) {
		return couponMapper.select(coupon);
	}

}
