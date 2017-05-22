package com.chair.manager.mapper;

import java.util.List;

import com.chair.manager.mapper.plugin.ChairMapper;
import com.chair.manager.pojo.RechargePackage;

public interface RechargePackageMapper extends ChairMapper<RechargePackage> {

	List<RechargePackage> queryRechargeListByLimit(RechargePackage rechargePackage);

}
