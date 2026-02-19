package com.aashi.saas.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
	public UserDto(long id, String username, String email, String role) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.role = role;
	}
	private long id;
	private String username;
	private String email;
    private String role;
}
