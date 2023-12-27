package com.example.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// HTTP 요청을 받아서 응답하는 component, spring boot가 자동으로 bean 으,로 생성한다.
@Controller
public class BoardController {
	// 게시물 목록을 보여준다
	// controller의 method가 return하는 문자열은 template 이름이다.
	// resources/templates/list.html 파일
	@GetMapping("/")
	public String list() {
		return "list";
	}

	@GetMapping("/board")
	public String board(@RequestParam("id") int id) {
		System.out.println("id: " + id);

		// id에 해당하는 게시물을 읽는다
		// id에 해당하는 게시물의 조회수를 1 증가한다.
		return "board";
	}

	@GetMapping("/writeForm")
	public String writeForm() {
		// login한 사용자만 글을 쓴다
		// 세션에서 로그인한 사용자 정보를 읽어 들인다. 로그인을 하지 않았으면 리스트로 이동 시킨다
		return "writeForm";
	}

	@PostMapping("/write")
	public String write(@RequestParam("title") String title, @RequestParam("content") String content) {
		// TODO: process POST request
		// login한 사용자만 글을 쓴다
		// 세션에서 로그인한 사용자 정보를 읽어 들인다. 로그인을 하지 않았으면 리스트로 이동 시킨다
		// 로그인한 회원정보 + 제목 + 내용을 저장
		// 글을쓴후에는 리스트 보기로 이동
		System.out.println("[title: " + title + "]");

		return "redirect:/";
	}

}
