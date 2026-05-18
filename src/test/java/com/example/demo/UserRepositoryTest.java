package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace;
import org.springframework.test.annotation.Rollback;

import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;
import jakarta.persistence.EntityManager;

import org.junit.jupiter.api.Test;


@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
class UserRepositoryTest {

	@Autowired
	private EntityManager entityManager;
	
    @Autowired
    private UserRepository userRepository;
    
    
	@Test
	public void testCreateUser() {
		User user = new User();
		user.setUsername("toto");
		user.setPassword("12345");
		user.setRole("User");
		
		User savedUser = userRepository.save(user);
		
		User existUser = entityManager.find(User.class, savedUser.getUserId());
		
		assertEquals(user.getUsername(), existUser.getUsername());
	}

}
