package com.chair.manager.mapper;

import java.util.List;

import com.chair.manager.mapper.plugin.ChairMapper;
import com.chair.manager.pojo.RechargeRecord;

public interface RechargeRecordMapper extends ChairMapper<RechargeRecord> {

	List<RechargeRecord> queryRechargeRecordList(RechargeRecord record);

}
