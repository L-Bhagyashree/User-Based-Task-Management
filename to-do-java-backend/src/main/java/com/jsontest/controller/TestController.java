package com.jsontest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jsontest.config.CommonConfig;
import com.jsontest.config.EmployeeModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsontest.Entity.TaskEntity;
import com.jsontest.Entity.UserEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
public class TestController {

	@Autowired
	public CommonConfig comnConfig;

	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping("/") public String Hello() { return
	 * "Hello,Welcome to our website"; }
	 * 
	 * @RequestMapping("/intro") public String intro() { return "hello"; }
	 */

	/*
	 * @PostMapping(value="/register", consumes = {
	 * MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE })
	 * 
	 * @ResponseBody public String receiveData(@RequestBody UserEntity userData) {
	 * String res= comnConfig.saveUserData(userData); return res; }
	 */

	
	
	//For Registration  of user
	@PostMapping(value = "/register", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE })
	@ResponseBody
	public String receiveData(@RequestBody String user) throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		UserEntity newUser = objectMapper.readValue(user, UserEntity.class);
		System.out.print(user);
		System.out.print(newUser);
		System.out.println("To be registered User Name: " + newUser.getUser_name());
		System.out.println("To be registered User Email: " + newUser.getUser_email());
		System.out.println("To be registered User Password: " + newUser.getUser_password());
		System.out.println("To be registered Confirm Password: " + newUser.getConfirmPasssword());
		String res = comnConfig.saveUserData(newUser);
		return res;
	}
	

	//For login purpose
	@PostMapping(value = "/login", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE })
	@ResponseBody
	public Map<String, Object> login(@RequestBody String user2) throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		UserEntity loginUser = objectMapper.readValue(user2, UserEntity.class);
		String loginUserName = loginUser.getUser_name();
		String loginUserPassword = loginUser.getUser_password();
		System.out.println("Login User Name:" + loginUser.getUser_name());
		System.out.println("Login User password:" + loginUser.getUser_password());

		Map<String, Object> message = comnConfig.loginMessage(loginUserName, loginUserPassword);

		return message;
	}

	/*
	 * @DeleteMapping("/data/{id}")
	 * 
	 * @ResponseBody public String deleteData(@RequestBody int user_id) { String
	 * resDelete = comnConfig.deleteData(user_id); return resDelete; }
	 */
	
	//for saving Tasks
	@PostMapping(value = "/task", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE })
	@ResponseBody
	public Map<String, Object> saveReceivedTask(@RequestBody String task)
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		TaskEntity newTask = objectMapper.readValue(task, TaskEntity.class);
		System.out.print(task);
		System.out.print(newTask);

		Map<String, Object> Task1 = comnConfig.SaveTask(newTask);
		return Task1;
	}

	//for showing Tasks
	@PostMapping(value = "/showtask", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE })
	@ResponseBody
	public List<Map<String, Object>> showTask(@RequestBody String input)
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		Integer userId = null;
		System.out.println(input);
		try {
			Map<String, String> request = objectMapper.readValue(input, Map.class);

			userId = Integer.parseInt(request.get("user_id"));
			System.out.println(userId);
		} catch (JsonProcessingException e) {
			// If JSON parsing fails, try to parse input as plain text
			try {
				userId = Integer.parseInt(input.trim());
			} catch (NumberFormatException nfe) {
				throw new IllegalArgumentException("Invalid user_id format", nfe);
			}
		}

		List<Map<String, Object>> Task = comnConfig.showSavedTask(userId);

		return Task;
	}
	
	//for deleting tasks
	@PostMapping(value = "/deletetask", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE })
	@ResponseBody
	public Map<String , Object> deleteTask(@RequestBody String input) throws JsonMappingException, JsonProcessingException {

		ObjectMapper objectMapper = new ObjectMapper();
		Integer taskId = null;
		System.out.println(input);
		 try {
	            Map<String, Object> request = objectMapper.readValue(input, Map.class);

	            Object taskIdObj = request.get("task_id");
	            if (taskIdObj instanceof Integer) {
	                taskId = (Integer) taskIdObj;
	            } else if (taskIdObj instanceof String) {
	                taskId = Integer.parseInt((String) taskIdObj);
	            } else {
	                throw new IllegalArgumentException("Invalid task_id format");
	            }
	            System.out.println(taskId);
	        } catch (JsonProcessingException e) {
	            // If JSON parsing fails, try to parse input as plain text
	            try {
	                taskId = Integer.parseInt(input.trim());
	            } catch (NumberFormatException nfe) {
	                throw new IllegalArgumentException("Invalid task_id format", nfe);
	            }
	        }

	        boolean success = comnConfig.deleteTaskById(taskId);
	        if (success) {
	        	Map<String , Object> resultDelete=new  HashMap<>();
	        	resultDelete.put("message","Task deleted successfully");
	            return resultDelete;
	        } else {
	        	Map<String , Object> resultDelete=new  HashMap<>();
	        	resultDelete.put("message","Task not found");
	            return resultDelete;
	        }
	}
	
	
	
	@PostMapping(value = "/edittask", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE })
	@ResponseBody
	public Map<String, Object> editTask(@RequestBody String input)
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		TaskEntity newTask = objectMapper.readValue(input, TaskEntity.class);
		
		System.out.println(input);
		System.out.println(newTask);
		
			Map<String, Object> Task2 = comnConfig.editExistingTask(newTask);
			return Task2;
			
	}
	
	
	
	
	
	
	
	
	
	
	
}
