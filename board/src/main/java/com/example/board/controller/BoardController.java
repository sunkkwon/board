package com.example.board.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.board.dto.Board;
import com.example.board.dto.LoginInfo;
import com.example.board.service.BoardService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

// HTTP 요청을 받아서 응답하는 component, spring boot가 자동으로 bean 으로 생성한다.
@Controller
@RequiredArgsConstructor
public class BoardController {
	private final BoardService boardService;

	// 게시물 목록을 보여준다
	// controller의 method가 return하는 문자열은 template 이름이다.
	// resources/templates/list.html 파일
	@GetMapping("/")
	public String list(@RequestParam(name = "page", defaultValue = "1") int page, HttpSession session,
			Model model) {
		LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");

		model.addAttribute("loginInfo", loginInfo);

		int totalCount = boardService.getTotalCount();	// 게시물의 전체 건수
		int pageCount = (int) Math.ceil(totalCount / (double) 10);	// 페이지당 10 건으로의 필요한 페이지 수
//		int pageCount = boardService.getPageCount();	// db 에서 계산시
		List<Board> list = boardService.getBoards(page);	// 게시물 목록

		int currentPage = page;

		model.addAttribute("list", list);
		model.addAttribute("pageCount", pageCount);
//		model.addAttribute("currentPage", currentPage);

//		System.out.println(pageCount);
//		System.out.println(totalCount);
//		System.out.println(paString);
//		for (Board board : list) {
//			System.out.println(board);
//		}
//
		return "list";
	}

	@GetMapping("/board")
	public String board(@RequestParam("boardId") int boardId, Model model) {
//		System.out.println("id: " + id);

		// id에 해당하는 게시물을 읽는다
		// id에 해당하는 게시물의 조회수를 1 증가한다.
		Board board = boardService.getBoard(boardId);
		model.addAttribute("board", board);

		return "board";
	}

	@GetMapping("/writeForm")
	public String writeForm(HttpSession httpSession, Model model) {
		// login한 사용자만 글을 쓴다
		// 세션에서 로그인한 사용자 정보를 읽어 들인다. 로그인을 하지 않았으면 리스트로 이동 시킨다
		LoginInfo loginInfo = (LoginInfo) httpSession.getAttribute("loginInfo");
		if (loginInfo == null) { // 세션에 로그인 정보가 없으면
			return "redirect:/loginForm";
		}

		model.addAttribute("loginInfo", loginInfo);

		return "writeForm";
	}

	@PostMapping("/write")
	public String write(@RequestParam("title") String title, @RequestParam("content") String content,
			HttpSession session) {
		LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
		if (loginInfo == null) { // 세션에 로그인 정보가 없으면
			return "redirect:/loginForm";
		}
		// TODO: process POST request
		// login한 사용자만 글을 쓴다
		// 세션에서 로그인한 사용자 정보를 읽어 들인다. 로그인을 하지 않았으면 리스트로 이동 시킨다
		// 로그인한 회원정보 + 제목 + 내용을 저장
		// 글을쓴후에는 리스트 보기로 이동
//		System.out.println("[title: " + title + "]");
		boardService.addBoard(loginInfo.getUserId(), title, content);

		return "redirect:/";
	}

	@GetMapping("/delete")
	public String delete(@RequestParam("boardId") int boardId, HttpSession session) {

		LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
		if (loginInfo == null) {
			return "redirect:/loginForm";
		}

		// 사용자가 쓴 게시물만 삭제 처리
		List<String> roles = loginInfo.getRoles();
		if (roles.contains("ROLE_ADMIN")) {
			boardService.deleteBoard(boardId);
		} else {
			boardService.deleteBoard(loginInfo.getUserId(), boardId);
		}
		return "redirect:/";
	}

	@GetMapping("/updateForm")
	public String updateForm(@RequestParam("boardId") int boardId, Model model, HttpSession session) {
		LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
		if (loginInfo == null) {
			return "redirect:/loginForm";
		}
		// boardId에 해당하는 게시물을 읽어온다
		Board board = boardService.getBoard(boardId, false);
		model.addAttribute("board", board);
		model.addAttribute("loginInfo", loginInfo);

		return "updateForm";
	}

	@PostMapping("/update")
	public String update(@RequestParam("boardId") int boardId, @RequestParam("title") String title,
			@RequestParam("content") String content, HttpSession session) {
		LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
		if (loginInfo == null) {
			return "redirect:/loginForm";
		}

		Board board = boardService.getBoard(boardId, false);
		if (board.getUserId() != loginInfo.getUserId()) {
			return "redirect:/board?boardId=" + boardId;
		}

		boardService.updateBoard(boardId, title, content);

		return "redirect:/board?boardId=" + boardId;
	}

}
