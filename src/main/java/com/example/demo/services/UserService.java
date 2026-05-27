package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDto;
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
	
	/*
	 * Register a new user to the application, the user name and the password are inputed by the user
	 * but the Id and the role are set by default (role = user) 
	 */
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
	/*
	 * Login an existing User in the application. 
	 * Only the password and the user name are necessary,
	 * so we use the loginRequest with only those parameters.
	 */
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
	
	public UserDto getUser(String username) {
		User user = userRepository.findByUsername(username);
		return new UserDto(user.getUsername(), user.getPassword(), user.getRole());
	}
	
	public User getUserWithTodos(String username) {
		User user = userRepository.findByUsername(username);
		return user;
	}
	
	public List<UserDto> getAllUsers(){
		List<UserDto> users = userRepository.findAll().stream()
				.map(user -> new UserDto(user.getUsername(), user.getPassword(), user.getRole()))
				.toList();
		return users;
	}
}
