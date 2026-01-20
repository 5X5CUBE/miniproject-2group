package com.springbootstudy.app.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.springbootstudy.app.domain.Users;

@Mapper
public interface UserMapper {
	
	Users getUserByNickname(String nickname);
	
	public Users getUser(String id);
	
	public void addUser(Users user);
	
	Users getUserById(String loginId); 
}
