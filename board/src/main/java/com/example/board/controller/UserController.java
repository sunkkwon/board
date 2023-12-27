package com.example.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class UserController {
	// classpath:/templates/userRegForm.html을 보여준다
	@GetMapping("/userRegForm")
	public String userRegForm() {
		return "userRegForm";
	}

	@PostMapping("/userReg")
	public String userReg(@RequestParam("name") String name, @RequestParam("email") String email,
			@RequestParam("password") String password) {

//		System.out.println("name:" + name);
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
	public String login(@RequestParam("email") String email, @RequestParam("password") String passowrd) {
		//TODO: process POST request
		System.out.println("email: "+email);
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout() {
		return "redirect:/";
	}
	
	

}
