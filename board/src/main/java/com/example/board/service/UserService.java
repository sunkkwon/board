package com.example.board.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.dao.UserDao;
import com.example.board.dto.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // final 변수는 생성자에서 초기화 필요, lombok이 필드를 초기화하는 생성자를 자동 셍성
public class UserService {
	private final UserDao userDao;

	// 서비스에서는 @Transactional 을 붙여서 하나의 tr 로 처리
	// sprong boot는 tr을 처리해주는 관리자가 있다.(AOP)
	@Transactional
	public User addUser(String email, String name, String password) {
		User user = userDao.addUser(email, name, password);
		userDao.mappingUserRole(user.getUserId());

		return user;
	}
}
