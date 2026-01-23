package com.springbootstudy.app.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.springbootstudy.app.domain.Users;

@Mapper
public interface UserMapper {
	
	void deleteUser(String loginId);
	
	void updatePhone(Users user);
	
	void updatePassword(Users user);
	
	void updateAddress(Users user);
	
	void updateNickname(Users user);
	
	Users getUserByNickname(String nickname);
	
	public Users getUser(String id);
	
	public void addUser(Users user);
	
	Users getUserById(String loginId); 
}
