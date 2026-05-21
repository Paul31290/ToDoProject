package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.requests.LoginRequest;

@Service
public class UserService{
	
	@Autowired
	private UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User register(String username, String password) {
		User userToRegister = new User();
		userToRegister.setUsername(username);
		if(userRepository.findByUsername(userToRegister.getUsername()) != null) {
			throw new RuntimeException("The user with the name "+ userToRegister.getUsername() +" already exists. Please input another name");
		}
		userToRegister.setPassword(password);
		userToRegister.setRole("user");
		return userRepository.save(userToRegister);
	}
	
	public User login(LoginRequest loginRequest) {
		
		String username = loginRequest.getUsername();
		String password = loginRequest.getPassword();
		
		User user = userRepository.findByUsername(username);
		if (user != null) {
			if(user.getPassword().equals(password)) {
				return user;
			} else {
				throw new RuntimeException("Invalid password, try again.");
			}
		} else {
			throw new RuntimeException("Invalid username, try again.");
		}
	}
	
	public User getUser(String username) {
		return userRepository.findByUsername(username);
	}
}
