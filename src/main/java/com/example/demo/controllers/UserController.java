package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.User;
import com.example.demo.requests.LoginRequest;
import com.example.demo.services.UserService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/register")
	public User register(String username, String password) {
		return userService.register(username, password);
	}
	
	@PostMapping("/login")
	public User login(LoginRequest loginRequest) {
		return userService.login(loginRequest);
	}
	
	@GetMapping("/user")
	public User getUser(String username) {
		return userService.getUser(username);
	}
}
