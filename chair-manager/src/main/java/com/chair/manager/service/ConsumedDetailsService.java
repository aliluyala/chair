package com.chair.manager.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.chair.manager.pojo.ConsumedDetails;
import com.github.pagehelper.PageInfo;

@Service
public class ConsumedDetailsService extends BaseService<ConsumedDetails> {
	
	@Override
	public List<ConsumedDetails> queryList(ConsumedDetails t) {
		// TODO Auto-generated method stub
		return super.queryList(t);
	}

	@Override
	public PageInfo<ConsumedDetails> queryListPage(ConsumedDetails t,
			Integer page, Integer rows) {
		// TODO Auto-generated method stub
		return super.queryListPage(t, page, rows);
	}

	@Override
	public Integer queryCount(ConsumedDetails t) {
		// TODO Auto-generated method stub
		return super.queryCount(t);
	}

	@Override
	public Integer saveSelective(ConsumedDetails t) {
		// TODO Auto-generated method stub
		return super.saveSelective(t);
	}

	@Override
	public Integer update(ConsumedDetails t) {
		// TODO Auto-generated method stub
		return super.update(t);
	}

	@Override
	public Integer updateSelective(ConsumedDetails t) {
		// TODO Auto-generated method stub
		return super.updateSelective(t);
	}

	@Override
	public Integer deleteById(Long id) {
		// TODO Auto-generated method stub
		return super.deleteById(id);
	}

	@Override
	public Integer deleteByIds(Long[] ids) {
		// TODO Auto-generated method stub
		return super.deleteByIds(ids);
	}
	
	

}
