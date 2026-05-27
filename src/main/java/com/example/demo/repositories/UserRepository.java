package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
	
}
