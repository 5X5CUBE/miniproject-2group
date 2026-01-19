package com.springbootstudy.bbs.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.springbootstudy.bbs.domain.Users;

@Mapper
public interface UserMapper {
	
	Users getUserByNickname(String nickname);
	
	public Users getUser(String id);
	
	public void addUser(Users user);
	
	Users getUserById(String userId); 
}
