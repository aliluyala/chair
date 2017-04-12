package com.chair.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chair.manager.mapper.UsersMapper;
import com.chair.manager.pojo.Users;

@Service
public class UsersService extends BaseService<Users> {
	
	@Autowired
	private UsersMapper usersMapper;
	
	/**
	 * 新增或更新
	 * @param user
	 */
	public void saveOrUpdate(Users user){
		usersMapper.saveOrUpdate(user);
	}
}
