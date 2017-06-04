package com.chair.manager.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chair.manager.bean.EasyUIResult;
import com.chair.manager.mapper.ConsumedDetailsMapper;
import com.chair.manager.pojo.ConsumedDetails;
import com.github.pagehelper.PageInfo;

@Service
public class ConsumedDetailsService extends BaseService<ConsumedDetails> {

	@Autowired
	private ConsumedDetailsMapper detailsMapper;
	
	/**
	 * 分页消费充值记录
	 * @param page 页面
	 * @param rows 页面大小
	 * @return
	 */
	public EasyUIResult queryPage(ConsumedDetails consume, Integer page, Integer rows) {
		PageInfo<ConsumedDetails> pageInfo=super.queryListPage(consume, page, rows);
		return new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
	}

	
	public List<ConsumedDetails> queryRechargeRechargeList(ConsumedDetails details){
		return detailsMapper.queryConsumedDetailsList(details);
	}
	
	
}
