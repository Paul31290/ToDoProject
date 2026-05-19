package com.example.demo.entities;


import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name="user", schema = "public")
public class User{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", nullable = false)
	private Long userId;
	
	@Column(name = "username", nullable = false)
	private String username;
	
	@Column(name = "password", nullable = false)
    private String password;
	
	@Column(name = "role", nullable = false)
    private String role;
	
	@JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<ToDo> todos = new ArrayList<>();
    
	public Long getUserId() {
		return userId;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getRole() {
		return role;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	public List<ToDo> getTodos() {
        return todos;
    }

    public void addToDo(ToDo todo) {
        todos.add(todo);
        todo.setUser(this);
    }

    public void removeToDo(ToDo todo) {
        todos.remove(todo);
        todo.setUser(null);
    }
    
}
