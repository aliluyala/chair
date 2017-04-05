package com.chair.manager.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.chair.manager.pojo.Pattern;

@Service
public class PatternService extends BaseService<Pattern> {

	public Pattern findById(Long id) {
		return super.findById(id);
	}

	public List<Pattern> queryAll() {
		return super.queryList(new Pattern());
	}

}
