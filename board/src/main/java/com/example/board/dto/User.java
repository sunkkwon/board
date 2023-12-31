package com.example.board.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

//@Setter
//@Getter
@NoArgsConstructor
@Data
public class User {
	private int userId;
	private String name;
	private String email;
	private String password;
	private LocalDateTime regdate;
}
