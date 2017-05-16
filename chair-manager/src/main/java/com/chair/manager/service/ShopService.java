package com.chair.manager.service;

import org.springframework.stereotype.Service;

import com.chair.manager.bean.EasyUIResult;
import com.chair.manager.pojo.Shop;
import com.github.pagehelper.PageInfo;

@Service
public class ShopService extends BaseService<Shop> {

	public EasyUIResult queryShopListForPage(Integer page, Integer rows) {
		PageInfo<Shop> pageInfo= super.queryListPage(new Shop(), page, rows);
		return new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
	}

}
