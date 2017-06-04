package com.chair.manager.mapper;

import java.util.List;

import com.chair.manager.mapper.plugin.ChairMapper;
import com.chair.manager.pojo.ConsumedDetails;

public interface ConsumedDetailsMapper extends ChairMapper<ConsumedDetails> {

	List<ConsumedDetails> queryConsumedDetailsList(ConsumedDetails details);

}
