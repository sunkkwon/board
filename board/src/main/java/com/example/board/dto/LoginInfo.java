package com.example.board.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
//@AllArgsConstructor
public class LoginInfo {
	private int userId;
	private String name;
	private String email;
	private List<String> roles = new ArrayList<String>();

	public LoginInfo(int userId, String name, String email) {
		super();
		this.userId = userId;
		this.name = name;
		this.email = email;
	}
}
