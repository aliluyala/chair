package com.chair.manager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.chair.manager.bean.EasyUIResult;
import com.chair.manager.pojo.RechargePackage;
import com.github.pagehelper.PageInfo;

@Service
public class RechargePackageService extends BaseService<RechargePackage> {
	/**
	 * 分页查询充值套餐
	 * @param page 页面
	 * @param rows 页面大小
	 * @return
	 */
	public EasyUIResult queryPage(Integer page, Integer rows) {
		PageInfo<RechargePackage> pageInfo=super.queryListPage(new RechargePackage(), page, rows);
		return new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
	}

}
