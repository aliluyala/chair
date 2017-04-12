package com.chair.manager.mapper;

import com.chair.manager.mapper.plugin.ChairMapper;
import com.chair.manager.pojo.Users;

public interface UsersMapper extends ChairMapper<Users> {

	Users saveOrUpdate(Users u);

}
