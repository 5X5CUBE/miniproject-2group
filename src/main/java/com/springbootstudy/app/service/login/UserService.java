package com.springbootstudy.app.service.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springbootstudy.app.domain.Users;
import com.springbootstudy.app.mapper.UserMapper;

@Service
public class UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public boolean checkNickname(String nickname) {
	    Users user = userMapper.getUserByNickname(nickname);
	    return user == null; 
	}
	
	public int login(String id, String pass) {
		int result = -1;
		Users u = userMapper.getUser(id);
		if(u == null) {
		return result;
		}
		if(passwordEncoder.matches(pass, u.getPassword())) {
		result = 1;
		} else { // 비밀번호가 틀리면 : 0
		result = 0;
		}
		return result;
		}
		// 회원 ID에 해당하는 회원 정보를 읽어와 반환하는 메서드
		public Users getMember(String id) {
		return userMapper.getUser(id);
		}
		
	
	public boolean checkId(String userId) {
	  
	    Users user = userMapper.getUserById(userId); 
	    
	    return user == null; 
	}
	
	public void addUser(Users user) {

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		userMapper.addUser(user);
	
	}
}
