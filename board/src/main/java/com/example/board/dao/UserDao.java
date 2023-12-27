package com.example.board.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.dto.User;

@Repository
public class UserDao {

	@Transactional
	public User addUser(String email, String name, String password) {
//		User user = new User();

		return null;
	}

	@Transactional
	public void mappingUserRole(int userId) {

	}
}
