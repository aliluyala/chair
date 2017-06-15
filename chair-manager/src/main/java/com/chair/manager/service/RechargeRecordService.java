package com.chair.manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chair.manager.bean.EasyUIResult;
import com.chair.manager.mapper.RechargeRecordMapper;
import com.chair.manager.pojo.RechargeRecord;
import com.chair.manager.pojo.dto.RechargeRecordDto;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class RechargeRecordService extends BaseService<RechargeRecord> {

	@Autowired
	private RechargeRecordMapper recordMapper;

	/**
	 *  分页查询充值记录
	 *	@since 2017年6月15日
	 *	@author yaoym
	 *	@param record
	 *	@param page
	 *	@param rows
	 *	@return
	 */
	public EasyUIResult queryPage(RechargeRecordDto record, Integer page, Integer rows) {
		// 设置分页参数
		PageHelper.startPage(page, rows);
		// 查询
		List<RechargeRecordDto> list = recordMapper.queryRechargeRecordPage(record);
		PageInfo<RechargeRecordDto> pageInfo = new PageInfo<RechargeRecordDto>(list);
		return new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
	}

	public List<RechargeRecord> queryRechargeRecordList(RechargeRecord record) {
		return recordMapper.queryRechargeRecordList(record);
	}
}
