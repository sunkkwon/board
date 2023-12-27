package com.example.board.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Setter
//@Getter
@NoArgsConstructor
@Data
public class User {
	private int userId;
	private String email;
	private String name;
	private String password;
	private String regdate;
}
