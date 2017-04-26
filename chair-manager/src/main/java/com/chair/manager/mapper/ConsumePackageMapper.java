package com.chair.manager.mapper;

import java.util.List;

import com.chair.manager.mapper.plugin.ChairMapper;
import com.chair.manager.pojo.ConsumePackage;

public interface ConsumePackageMapper extends ChairMapper<ConsumePackage> {

	List<ConsumePackage> queryListByLimit(ConsumePackage consumePackage);
	
}
