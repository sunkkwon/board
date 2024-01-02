package com.example.board.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.dao.BoardDao;
import com.example.board.dto.Board;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
	private final BoardDao boardDao;

	@Transactional
	public void addBoard(int userId, String title, String content) {
		// TODO Auto-generated method stub
		boardDao.addBoard(userId, title, content);
	}

	@Transactional(readOnly = true)
	public int getTotalCount() {
		// TODO Auto-generated method stub
		return boardDao.getTotalCount();
	}

	@Transactional(readOnly = true)
	public List<Board> getBoards(int page) {
		// TODO Auto-generated method stub
		return boardDao.getBoards(page);
	}

	@Transactional(readOnly = true)
	public int getPageCount() {
		// TODO Auto-generated method stub
		return boardDao.getPageCount();
	}

	@Transactional
	public Board getBoard(int boardId) {
		// TODO Auto-generated method stub
		return getBoard(boardId, true);
	}

	public Board getBoard(int boardId, boolean updateViewCnt) {
		Board board = boardDao.getBoard(boardId);
		if (updateViewCnt) {
			boardDao.updateViewCnt(boardId);
		}
		return board;
	}

	@Transactional
	public void deleteBoard(int userId, int boardId) {
//		boardDao.deleteBoard(userId, boardId);
		Board board = boardDao.getBoard(boardId);

		if (board.getUserId() == userId) {
			boardDao.deleteBoard(boardId);
		}
	}

	@Transactional
	public void deleteBoard(int boardId) {
		boardDao.deleteBoard(boardId);
	}

	@Transactional
	public void updateBoard(int boardId, String title, String content) {
		boardDao.updateBoard(boardId, title, content);

	}

}
