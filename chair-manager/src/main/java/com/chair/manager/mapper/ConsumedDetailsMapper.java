package com.chair.manager.mapper;

import java.util.List;

import com.chair.manager.mapper.plugin.ChairMapper;
import com.chair.manager.pojo.ConsumedDetails;
import com.chair.manager.pojo.dto.ConsumedDetailsDto;

public interface ConsumedDetailsMapper extends ChairMapper<ConsumedDetails> {

	List<ConsumedDetails> queryConsumedDetailsList(ConsumedDetails details);

	List<ConsumedDetailsDto> queryConsumedDetailsPage(ConsumedDetailsDto consume);

	ConsumedDetailsDto queryConsumedDetailsBaseInfo();

}
