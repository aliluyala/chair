package com.chair.manager.mapper;


import java.util.List;

import com.chair.manager.mapper.plugin.ChairMapper;
import com.chair.manager.pojo.Manager;
import com.chair.manager.pojo.Statisctics;

public interface StatiscticsMapper extends ChairMapper<Statisctics> {

	
	List<Statisctics> queryProxyStatisctics(Manager m);
	
	Statisctics queryBaseStatisctics(Manager m);

	List<Statisctics> queryShopStatisctics(Manager m);

	Statisctics queryShopBaseStatisctics(Manager m);
	
	
}
