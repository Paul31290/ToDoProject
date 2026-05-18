package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService{
	
	@Autowired
	private UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User register(User user) {
		if(userRepository.findByUsername(user.getUsername()) == null) {
			return userRepository.save(user);
		} else {
			throw new RuntimeException("The user with the name "+ user.getUsername() +" already exists. Please input another name");
		}
	}
	
	public User login(String username, String password) {
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
