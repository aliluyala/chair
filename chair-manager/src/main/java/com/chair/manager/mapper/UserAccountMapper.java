package com.chair.manager.mapper;

import com.chair.manager.mapper.plugin.ChairMapper;
import com.chair.manager.pojo.UserAccount;

public interface UserAccountMapper extends ChairMapper<UserAccount> {

	void saveOrUpdate(UserAccount userAccount);

}
