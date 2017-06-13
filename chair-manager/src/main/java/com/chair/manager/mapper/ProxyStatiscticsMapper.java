package com.chair.manager.mapper;


import java.util.List;

import com.chair.manager.mapper.plugin.ChairMapper;
import com.chair.manager.pojo.Manager;
import com.chair.manager.pojo.ProxyStatisctics;

public interface ProxyStatiscticsMapper extends ChairMapper<ProxyStatisctics> {

	
	List<ProxyStatisctics> queryProxyStatisctics(Manager m);
	
}
