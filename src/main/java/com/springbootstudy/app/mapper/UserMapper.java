package com.springbootstudy.app.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.springbootstudy.app.domain.Users;

@Mapper
public interface UserMapper {
	
	int checkUserByPhone(@Param("loginId") String loginId, @Param("phone") String phone);
	
	void resetPassword(@Param("loginId") String loginId, @Param("password") String password);
	
	String findLoginId(@Param("username") String username, @Param("phone") String phone);
	
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
