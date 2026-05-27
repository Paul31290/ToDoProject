package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ToDoDto;
import com.example.demo.entities.ToDo;
import com.example.demo.entities.User;
import com.example.demo.repositories.ToDoRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.requests.ToDoUpdateRequest;

@Service
public class ToDoService {

	@Autowired
	private ToDoRepository toDoRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	/*
	 * Get all of the To Dos, after the login, or the creation, the update or the deletion of a To Do  
	 */
	public List<ToDoDto> getAllToDos(Long userId){
		User user = userRepository.findById(userId).orElse(null);
		if(user != null) {
			 List<ToDoDto> todoDto = user.getTodos().stream()
					 .map(todo -> new ToDoDto( todo.getTodoId(), todo.getTitle(), todo.getDescription(), todo.isDone()))
					 .toList();
			 return todoDto;
		} else {
			throw new RuntimeException("User does not exist with this id");
		}
	}
	
	public ToDoDto getToDoFromUser(Long userId, Long todoId){
		User user = userRepository.findById(userId).orElse(null);
		if(user != null) {
			 ToDo todo = toDoRepository.findById(todoId).orElse(null);
			 if (todo != null) {
				 return new ToDoDto(todo.getTodoId(), todo.getTitle(), todo.getDescription(), todo.isDone());
			 } else {
				 throw new RuntimeException("ToDo does not exist with this id");
			 }
		} else {
			throw new RuntimeException("User does not exist with this id");
		}
	}
	
	/*
	 * Create a To Do element. The title and the description are inputed via the user, 
	 * but the Id and the state are set by default. (isDone = false)
	 */
	public ToDoDto createTodoItem(long userId, String title, String description) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            ToDo createdToDo = new ToDo();
            createdToDo.setUser(user);
            createdToDo.setTitle(title);
            createdToDo.setDescription(description);
            createdToDo.setDone(false);
            user.addToDo(createdToDo);
            toDoRepository.save(createdToDo);
            return new ToDoDto(createdToDo.getTodoId(), createdToDo.getTitle(), createdToDo.getDescription(), createdToDo.isDone());
        }
        else {
            throw new RuntimeException("User not found");
        }
    }
	
	/*
	 * Update a To Do element from the Id fetched. 
	 * Can only update the title and the description, as the isDone will be handled differently.
	 */
	public ToDoDto updateTodoItem(Long todoId, ToDoUpdateRequest toDoUpdateRequest) {
        ToDo existingToDo = toDoRepository.findById(todoId).orElse(null);

        if (existingToDo != null) {
        	
            existingToDo.setTitle(toDoUpdateRequest.getTitle());
            existingToDo.setDescription(toDoUpdateRequest.getDescription());
            toDoRepository.save(existingToDo);
            
            return new ToDoDto(existingToDo.getTodoId(), existingToDo.getTitle(), existingToDo.getDescription(), existingToDo.isDone());
        } else {
            throw new RuntimeException("ToDo does not exist with this id");
        }
    }
	
	/*
	 * Delete a To Do element from the Id fetched. 
	 */
	public void deleteTodoItem(Long toDoId) {
        ToDo existingToDo = toDoRepository.findById(toDoId).orElse(null);


        if (existingToDo != null) {
            User user = existingToDo.getUser();

            user.removeToDo(existingToDo);
            userRepository.save(user);

            toDoRepository.delete(existingToDo);
        } else {
            throw new RuntimeException("ToDo does not exist with this id");
        }
    }
	
	/*
	 * Updates the isDone parameter for the ToDo
	 * true if false, false if true
	 */
	public ToDoDto checkIsDone(Long toDoId) {
		
		ToDo existingToDo = toDoRepository.findById(toDoId).orElse(null);

        if (existingToDo != null) {
        	if (!existingToDo.isDone()) {
        		existingToDo.setDone(true);
        	} else {
        		existingToDo.setDone(false);
        	}
            toDoRepository.save(existingToDo);
        	return new ToDoDto(existingToDo.getTodoId(), existingToDo.getTitle(), existingToDo.getDescription(), existingToDo.isDone());
        } else {
            throw new RuntimeException("ToDo does not exist with this id");
        }
	}
}
