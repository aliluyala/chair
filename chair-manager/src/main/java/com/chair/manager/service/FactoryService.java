package com.chair.manager.service;

import org.springframework.stereotype.Service;

import com.chair.manager.bean.EasyUIResult;
import com.chair.manager.pojo.Factory;
import com.github.pagehelper.PageInfo;

@Service
public class FactoryService extends BaseService<Factory> {

	public EasyUIResult queryDeviceListForPage(Integer page, Integer rows) {
		PageInfo<Factory> pageInfo= super.queryListPage(new Factory(), page, rows);
		return new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
	}

}
