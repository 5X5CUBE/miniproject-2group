package com.springbootstudy.app.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users {
	private String userId;
	private String username;
	private LocalDate birthdate;
	private LocalDateTime rdate;
	private String password;
	private String nickname;
	private String phone;
	private String address;
	private String gender;
}


