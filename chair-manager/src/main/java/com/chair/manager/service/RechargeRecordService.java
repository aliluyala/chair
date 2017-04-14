package com.chair.manager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.chair.manager.bean.EasyUIResult;
import com.chair.manager.pojo.RechargeRecord;

@Service
public class RechargeRecordService extends BaseService<RechargeRecord> {

	/**
	 * 分页查询充值套餐
	 * @param page 页面
	 * @param rows 页面大小
	 * @return
	 */
	public EasyUIResult queryPage(Integer page, Integer rows) {
		// 设置分页参数
        //PageHelper.startPage(page, rows);
		List<RechargeRecord> rInfo=	super.queryList(new RechargeRecord());
		return new EasyUIResult(30L, rInfo);
	}

}
