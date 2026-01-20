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
	private Long userId;
	private String loginId;
	private String username;
	private LocalDate birthdate;
	private LocalDateTime rdate;
	private String password;
	private String nickname;
	private String phone;
	private String address;
	private String gender;
	
	public String getZipcode() {
	    if (address == null || !address.contains("#")) return "";
	    return address.split("#")[0];
	}

	public String getAddr1() {
	    if (address == null || !address.contains("#")) return "";
	    return address.split("#").length > 1 ? address.split("#")[1] : "";
	}

	public String getAddr2() {
	    if (address == null || !address.contains("#")) return "";
	    return address.split("#").length > 2 ? address.split("#")[2] : "";
	}
}


