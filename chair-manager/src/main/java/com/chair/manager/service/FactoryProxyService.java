package com.chair.manager.service;

import org.springframework.stereotype.Service;

import com.chair.manager.bean.EasyUIResult;
import com.chair.manager.pojo.FactoryProxy;
import com.github.pagehelper.PageInfo;

@Service
public class FactoryProxyService extends BaseService<FactoryProxy>{

	public EasyUIResult queryProxyListForPage(Integer page, Integer rows) {
		PageInfo<FactoryProxy> pageInfo= super.queryListPage(new FactoryProxy(), page, rows);
		return new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
	}
}
