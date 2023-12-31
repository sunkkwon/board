package com.example.board.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Board {
	private int boardId;
	private String title;
	private String content;
	private int userId;
	private LocalDateTime regDate;
	private int viewCnt;
	private String name;
}
