package com.chair.manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chair.manager.bean.EasyUIResult;
import com.chair.manager.mapper.ConsumePackageMapper;
import com.chair.manager.pojo.ConsumePackage;
import com.github.pagehelper.PageInfo;

@Service
public class ConsumePackageService extends BaseService<ConsumePackage> {
	@Autowired
	private ConsumePackageMapper consumePackageMapper;

	public List<ConsumePackage> queryListByLimit(ConsumePackage consumePackage) {
		return consumePackageMapper.queryListByLimit(consumePackage);
	}

	public EasyUIResult queryPage(Integer page, Integer rows) {
		PageInfo<ConsumePackage> pageInfo=super.queryListPage(new ConsumePackage(), page, rows);
		return new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
	}
	
}
