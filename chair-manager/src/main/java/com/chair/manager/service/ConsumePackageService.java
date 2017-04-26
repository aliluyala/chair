package com.chair.manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chair.manager.mapper.ConsumePackageMapper;
import com.chair.manager.pojo.ConsumePackage;

@Service
public class ConsumePackageService extends BaseService<ConsumePackage> {
	@Autowired
	private ConsumePackageMapper consumePackageMapper;

	public List<ConsumePackage> queryListByLimit(ConsumePackage consumePackage) {
		return consumePackageMapper.queryListByLimit(consumePackage);
	}
	
}
