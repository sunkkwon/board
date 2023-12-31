package com.example.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginInfo {
	private int userId;
	private String name;
	private String email;
}
