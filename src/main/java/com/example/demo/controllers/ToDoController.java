package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.ToDo;
import com.example.demo.requests.ToDoUpdateRequest;
import com.example.demo.services.ToDoService;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "http://localhost:3000")
public class ToDoController {

	@Autowired
	private ToDoService toDoService;

	public ToDoController(ToDoService toDoService) {
		this.toDoService = toDoService;
	}
	
	@GetMapping("/user/{user_id}")
	public List<ToDo> getTodosByUser(@PathVariable Long user_id){
		return toDoService.getAllToDos(user_id);
	}
	
	@PostMapping("/user/{user_id}")
	public ToDo createToDo(@PathVariable Long user_id, String title, String description) {
		return toDoService.createTodoItem(user_id, title, description);
	}
	
	@PutMapping("/{todo_id}")
	public ToDo updateToDo(@PathVariable Long todo_id, @RequestBody ToDoUpdateRequest toDoUpdateRequest) {
		return toDoService.updateTodoItem(todo_id, toDoUpdateRequest);
	}
	
	@DeleteMapping("/{todo_id}")
	public void deleteToDo(@PathVariable Long todo_id) {
		toDoService.deleteTodoItem(todo_id);
	}
}
