package com.chair.manager.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chair.manager.bean.EasyUIResult;
import com.chair.manager.mapper.RechargeRecordMapper;
import com.chair.manager.pojo.RechargeRecord;
import com.github.pagehelper.PageInfo;

@Service
public class RechargeRecordService extends BaseService<RechargeRecord> {
	
	@Autowired
	private RechargeRecordMapper recordMapper;
	
	/**
	 * 分页查询充值记录
	 * @param page 页面
	 * @param rows 页面大小
	 * @return
	 */
	public EasyUIResult queryPage(RechargeRecord record, Integer page, Integer rows) {
		PageInfo<RechargeRecord> pageInfo=super.queryListPage(record, page, rows);
		return new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
	}
	
	public List<RechargeRecord> queryRechargeRechargeList(RechargeRecord record){
		return recordMapper.queryRechargeRechargeList(record);
	}
}
