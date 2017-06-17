package com.chair.manager.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chair.manager.bean.EasyUIResult;
import com.chair.manager.mapper.ConsumedDetailsMapper;
import com.chair.manager.pojo.ConsumedDetails;
import com.chair.manager.pojo.dto.ConsumedDetailsDto;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class ConsumedDetailsService extends BaseService<ConsumedDetails> {

	@Autowired
	private ConsumedDetailsMapper detailsMapper;
	
	/**
	 *  分页消费明细记录
	 *	@since 2017年6月15日
	 *	@author yaoym
	 *	@param consume
	 *	@param page 
	 *	@param rows
	 *	@return
	 */
	public EasyUIResult queryPage(ConsumedDetailsDto consume, Integer page, Integer rows) {
		// 设置分页参数
		PageHelper.startPage(page, rows);
		// 查询
		List<ConsumedDetailsDto> list = detailsMapper.queryConsumedDetailsPage(consume);
		PageInfo<ConsumedDetailsDto> pageInfo = new PageInfo<ConsumedDetailsDto>(list);
		return new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
	}

	
	public List<ConsumedDetails> queryConsumedDetailsList(ConsumedDetails details){
		return detailsMapper.queryConsumedDetailsList(details);
	}


	public ConsumedDetailsDto queryConsumedDetailsBaseInfo() {
		return detailsMapper.queryConsumedDetailsBaseInfo();
	}
	
	
	
	public void save2DB(ConsumedDetails consumer){
		detailsMapper.save2DB(consumer);
	}
	
}
