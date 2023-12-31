package com.example.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.board.dto.LoginInfo;
import com.example.board.dto.User;
import com.example.board.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	// classpath:/templates/userRegForm.html을 보여준다
	@GetMapping("/userRegForm")
	public String userRegForm() {
		return "userRegForm";
	}

	@PostMapping("/userReg")
	public String userReg(@RequestParam("name") String name, @RequestParam("email") String email,
			@RequestParam("password") String password) {

//		System.out.println("name:" + name);
		userService.addUser(name, email, password);

		return "redirect:/welcome"; // browser에게 자동으로 이동
	}

	@GetMapping("/welcome")
	public String welcome() {
		return "welcome";
	}

	@GetMapping("/loginform")
	public String loginform() {
		return "loginform";
	}

	@PostMapping("/login")
	public String login(@RequestParam("email") String email, 
			            @RequestParam("password") String passowrd,
			            HttpSession httpSession	// spring 이 자동으로 세션객체를 넣어준다.
			            ) {
		// TODO: process POST request
//		System.out.println("email: "+email);
		try {
			User user = userService.getUser(email);
//			System.out.println(user);
			if(user.getPassword().equals(passowrd)) {
				System.out.println("암호 정상");
				LoginInfo loginInfo = new LoginInfo(user.getUserId(), user.getName(), user.getEmail());
				httpSession.setAttribute("loginInfo", loginInfo);	// session에 로그인 정보가ㄴ 저장
			} else {
				throw new RuntimeException("암호 불일치");
			}
		} catch (Exception e) {
			// TODO: handle exception
			return "redirect:/loginform?error=true";
		}
		return "redirect:/";

	}

	@GetMapping("/logout")
	public String logout(HttpSession httpSession) {
		httpSession.removeAttribute("loginInfo");
		return "redirect:/";
	}

}
