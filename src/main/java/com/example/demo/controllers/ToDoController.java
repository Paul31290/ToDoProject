package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ToDoDto;
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
	public ResponseEntity<List<ToDoDto>> getTodosByUser(@PathVariable Long user_id){
		return ResponseEntity.ok(toDoService.getAllToDos(user_id));
	}
	
	@GetMapping("user/{user_id}/todo/{todo_id}")
	public ResponseEntity<ToDoDto> getTodoFromUser(@PathVariable Long user_id,@PathVariable Long todo_id){
		return ResponseEntity.ok(toDoService.getToDoFromUser(user_id, todo_id));
	}
	
	@PostMapping("/user/{user_id}")
	public ResponseEntity<ToDoDto> createToDo(@PathVariable Long user_id, String title, String description) {
		return ResponseEntity.ok(toDoService.createTodoItem(user_id, title, description));
	}
	
	@PutMapping("/{todo_id}")
	public ResponseEntity<ToDoDto> updateToDo(@PathVariable Long todo_id, @RequestBody ToDoUpdateRequest toDoUpdateRequest) {
		return ResponseEntity.ok(toDoService.updateTodoItem(todo_id, toDoUpdateRequest));
	}
	
	@PutMapping("/{todo_id}/checked")
	public ResponseEntity<ToDoDto> updateIsDone(@PathVariable Long todo_id) {
		return ResponseEntity.ok(toDoService.checkIsDone(todo_id));
	}
	
	@DeleteMapping("/{todo_id}")
	public void deleteToDo(@PathVariable Long todo_id) {
		toDoService.deleteTodoItem(todo_id);
	}
}
