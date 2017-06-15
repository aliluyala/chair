package com.chair.manager.mapper;

import java.util.List;

import com.chair.manager.mapper.plugin.ChairMapper;
import com.chair.manager.pojo.RechargeRecord;
import com.chair.manager.pojo.dto.RechargeRecordDto;

public interface RechargeRecordMapper extends ChairMapper<RechargeRecord> {

	List<RechargeRecord> queryRechargeRecordList(RechargeRecord record);

	List<RechargeRecordDto> queryRechargeRecordPage(RechargeRecordDto record);

}
