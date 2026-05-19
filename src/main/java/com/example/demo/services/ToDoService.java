package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.ToDo;
import com.example.demo.entities.User;
import com.example.demo.repositories.ToDoRepository;
import com.example.demo.repositories.UserRepository;

@Service
public class ToDoService {

	@Autowired
	private ToDoRepository toDoRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public List<ToDo> getAllToDos(Long userId){
		User user = userRepository.findById(userId).orElse(null);
		if(user != null) {
			return user.getTodos();
		} else {
			throw new RuntimeException("User does not exist with this id");
		}
	}
	
	public ToDo createTodoItem(long userId, ToDo toDoItem) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            toDoItem.setUser(user);
            return toDoRepository.save(toDoItem);
        }
        else {
            throw new RuntimeException("User not found");
        }
    }
	
	public ToDo updateTodoItem(Long todoId, ToDo updatedToDo) {
        ToDo existingToDo = toDoRepository.findById(todoId).orElse(null);

        if (existingToDo != null) {


            existingToDo.setTitle(updatedToDo.getTitle());
            existingToDo.setDescription(updatedToDo.getDescription());
            existingToDo.setDone(updatedToDo.isDone());

            return toDoRepository.save(existingToDo);
        } else {
            throw new RuntimeException("ToDo does not exist with this id");
        }
    }
	
	public void deleteTodoItem(Long toDoId) {
        ToDo existingToDo = toDoRepository.findById(toDoId).orElse(null);


        if (existingToDo != null) {
            User user = existingToDo.getUser();

            user.removeToDo(existingToDo);;
            userRepository.save(user);

             toDoRepository.delete(existingToDo);
        } else {
            throw new RuntimeException("ToDo does not exist with this id");
        }
    }
}
