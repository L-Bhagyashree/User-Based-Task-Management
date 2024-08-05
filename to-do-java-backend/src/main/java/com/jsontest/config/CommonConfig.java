package com.jsontest.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.bind.annotation.PathVariable;

import com.jsontest.Entity.TaskEntity;
import com.jsontest.Entity.UserEntity;
import com.jsontest.Mapper.UserMapper;

import java.util.Map;

@ComponentScan("com.jsontest")
@Configuration
public class CommonConfig {

	/*
	 * @Bean public DriverManagerDataSource getMySQLDriverManagerDatasource(){
	 * DriverManagerDataSource dataSource = new DriverManagerDataSource();
	 * dataSource.setDriverClassName("org.postgresql.Driver");
	 * dataSource.setPassword("Smart@123");
	 * dataSource.setUrl("jdbc:postgresql://localhost:5432/Project_Test");
	 * dataSource.setUsername("postgres"); return dataSource;
	 */

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://localhost:5432/Project_Test?useSSL=false");
		dataSource.setUsername("postgres");
		dataSource.setPassword("Smart@123");
		System.out.println(dataSource);
		return dataSource;
	}

	@Bean
	public JdbcTemplate jdbcTemplate() {
		JdbcTemplate jdbcTemplate = null;
		try {
			jdbcTemplate = new JdbcTemplate(dataSource(), true);
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println(jdbcTemplate);

		/*
		 * String sql="select * from Employee";
		 * System.out.println(jdbcTemplate.query("select * from Employee", new
		 * EmployeeMapper())); List<EmployeeModel> Employees=jdbcTemplate.query(sql, new
		 * EmployeeMapper());
		 */
		return jdbcTemplate;
	}

	public String saveUserData(UserEntity user) {
		String regex = "^[a-zA-Z0-9_]+$";
		String searchResName = checkUserName(user);
		String username = user.getUser_name();
		boolean checkName = username.matches(regex);

		System.out.println(user);

		if (!(user.getUser_name() == null || user.getUser_name().trim().isEmpty())
				&& !(user.getUser_email() == null || user.getUser_email().trim().isEmpty())
				&& !(user.getUser_password() == null || user.getUser_password().trim().isEmpty())
				&& !(user.getConfirmPasssword() == null || user.getConfirmPasssword().trim().isEmpty())) {
			if (searchResName == null) {

				if (checkName) {

					if (user.getUser_password().equals(user.getConfirmPasssword())) {


						String sql_1 = "INSERT INTO user_details (user_name, user_email,user_password) VALUES (?,?, ?)";
						int rowsAffected = jdbcTemplate().update(sql_1, user.getUser_name(), user.getUser_email(),
								user.getUser_password());
						if (rowsAffected > 0) {
							return "Registered !!!";
						} else {
							return "Something went wrong !!!";
						}
					} else {
						return "User_password and confirm password must be same";
					}
				} else {
					return "Invalid UserName";
				}
			} else {
				return "user already exist";
			}

		} else {
			return "All Fields are mandatory";
		}

	}

	public String checkUserName(UserEntity user2) {

		try {

			String sqlSearch = "select * from user_details where user_name=?";

			/* System.out.println(jdbcTemplate().query(sql, new EmployeeMapper())); */
			UserEntity u2 = jdbcTemplate().queryForObject(sqlSearch, new Object[] { user2.getUser_name() },
					new UserMapper());
			return u2.getUser_name();

		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

	/*
	 * public String getUserDetails(String user_name) {
	 * 
	 * try {
	 * 
	 * String sqlSearch="select user_password from user_details where user_name=?";
	 * 
	 * System.out.println(jdbcTemplate().query(sql, new EmployeeMapper())); String
	 * retrivedPassword=jdbcTemplate().queryForObject(sqlSearch, new
	 * Object[]{user_name}, String.class);
	 * System.out.println("Retrived Password : "+retrivedPassword); return
	 * retrivedPassword;
	 * 
	 * } catch(EmptyResultDataAccessException e) { return null; }
	 * 
	 * }
	 */

	public UserEntity getUserDetail(String user_name) {

		try {

			String sqlSearch = "select * from user_details where user_name=?";

			/* System.out.println(jdbcTemplate().query(sql, new EmployeeMapper())); */
			UserEntity RetrivedUser = jdbcTemplate().queryForObject(sqlSearch,  new Object[]{user_name},
					new BeanPropertyRowMapper<>(UserEntity.class));
			System.out.println("Retrived User : " + RetrivedUser);
			return RetrivedUser;

		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

	public Map<String, Object> loginMessage(String user_name, String user_password) {
		if (user_name == null || user_name.trim().isEmpty()) {
			Map<String, Object> result1 = new HashMap<>();

			result1.put("message", "user name can not be null");
			return result1;
		}
		if (user_password == null || user_password.trim().isEmpty()) {
			Map<String, Object> result1 = new HashMap<>();

			result1.put("message", "user password can not be null");
			return result1;
		}
		UserEntity retrivedUser1 = getUserDetail(user_name);
		
		if (retrivedUser1 != null) {
			String retrivedPswd=retrivedUser1.getUser_password();
			System.out.println("Retrived Password2 : " + retrivedPswd);
			
			if (retrivedPswd.equals(user_password)) {
				UserEntity retrivedUser2 = getUserDetail(user_name);
				Map<String, Object> result = new HashMap<>();
				result.put("Userid", retrivedUser2.getUser_id());
				result.put("message", "Processed successfully");
				result.put("UserName", retrivedUser2.getUser_name());
				return result;
			} else {
				Map<String, Object> result1 = new HashMap<>();

				result1.put("message", "wrong password");
				return result1;

			}
		} else {
			Map<String, Object> result1 = new HashMap<>();

			result1.put("message", "User doesn't exist");
			return result1;

		}

	}

	public List<Map<String, Object>> listEmployees() {
		// TODO Auto-generated method stub

		String sql = "select * from Employee";

		/* System.out.println(jdbcTemplate().query(sql, new EmployeeMapper())); */
		List<Map<String, Object>> Employees = jdbcTemplate().queryForList(sql);
		System.out.println(jdbcTemplate().queryForList(sql));

		return Employees;
	}

	public String deleteData(@PathVariable("id") int user_id) {
		String sqlDelete = "DELETE FROM user_details WHERE id = ?";
		int rowsAffected = jdbcTemplate().update(sqlDelete, user_id);
		if (rowsAffected > 0) {
			return "Data deleted successfully";
		} else {
			return "Data deletion failed";
		}
	}
	
	public  Map<String , Object> FindUserByUserID(int UserId) {
		
		try {

			String sqlSearch = "select * from user_details where user_id=?";

			/* System.out.println(jdbcTemplate().query(sql, new EmployeeMapper())); */
			UserEntity FindUser = jdbcTemplate().queryForObject(sqlSearch,new Object[]{UserId},
					new BeanPropertyRowMapper<>(UserEntity.class));
			Map<String , Object> resultTask=new  HashMap<>();
			resultTask.put("User", FindUser);
			return resultTask;

		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		
			
	}
	
	
	public Map<String , Object>  SaveTask(TaskEntity Task){
		
		if (!(Task.getTask_header() == null || Task.getTask_header().trim().isEmpty())
				&& !(Task.getTask_desc() == null ||Task.getTask_desc().trim().isEmpty())
				&& !(Task.getUser_id() == 0 || Task.getUser_id()==null)) {
			Map<String , Object> getUserMap=FindUserByUserID(Task.getUser_id());
			UserEntity getUserByID=(UserEntity) getUserMap.get("User");
			System.out.println(getUserByID);
			if(getUserByID.getUser_id()==null ||Task.getUser_id() == 0) {
				Map<String , Object> resultTask=new  HashMap<>();
				resultTask.put("message", "User Doesn't Exist!!!");
				return resultTask;
			}
			else {
				
				String sqlSaveTask ="INSERT INTO task (user_id,task_header,task_desc)  VALUES (?,?,?)";
				int rowsAffectedTask = jdbcTemplate().update(sqlSaveTask, Task.getUser_id(), Task.getTask_header(),
						Task.getTask_desc());
				if (rowsAffectedTask > 0) {
					Map<String , Object> resultTask=new  HashMap<>();
					resultTask.put("message","Task added Successfully !!!");
					return resultTask;
				} else {
					Map<String , Object> resultTask=new  HashMap<>();
					resultTask.put("message","Something went wrong !!!");
					return resultTask;
				}
			}
			
		}
		else {
			Map<String , Object> resultTask=new  HashMap<>();
			resultTask.put("message", "Please Fill All the Fields");
			return resultTask;
		}
		
		
	}
	
	public List<Map<String, Object>>  showSavedTask(int user_id){
		 List<Map<String, Object>> tasks = new ArrayList<>();

		try {

			String sqlSearchTask = "select * from task where user_id=?";

			/* System.out.println(jdbcTemplate().query(sql, new EmployeeMapper())); */
			/*
			 * TaskEntity retrivedTask = jdbcTemplate().queryForObject(sqlSearchTask,new
			 * Object[]{user_id}, new BeanPropertyRowMapper<>(TaskEntity.class));
			 */
			
			List<TaskEntity> taskList = jdbcTemplate().query(sqlSearchTask,new Object[]{user_id},
					new BeanPropertyRowMapper<>(TaskEntity.class));
			  for (TaskEntity task : taskList) {
	                Map<String, Object> taskMap = new HashMap<>();
	                taskMap.put("task_id", task.getTask_id());
	                taskMap.put("task_header", task.getTask_header());
	                taskMap.put("task_desc", task.getTask_desc());
	                taskMap.put("user_id", task.getUser_id());
	                // Add other task fields as needed
	                tasks.add(taskMap); }
			
			return tasks;

		} catch (EmptyResultDataAccessException e) {
			
			return null;
		}		
	}
	
	public boolean deleteTaskById(int task_id){
		
		 String sqlDeleteTask = "DELETE FROM task WHERE task_id = ?";
	        int rowsAffected = jdbcTemplate().update(sqlDeleteTask, task_id);
	        return rowsAffected > 0;
	}
	
	public Map<String,Object> editExistingTask(TaskEntity editTask){
		
		if (!(editTask.getTask_header() == null || editTask.getTask_header().trim().isEmpty())
				&& !(editTask.getTask_desc() == null ||editTask.getTask_desc().trim().isEmpty())) {
				System.out.println("header"+editTask.getTask_header());
				System.out.println("Desc"+editTask.getTask_desc());
				System.out.println("TaskID"+editTask.getTask_id());
				String sqlUpdateTask = "UPDATE task SET task_header = ?, task_desc = ? WHERE task_id = ?";
				int rowsAffectedTask = jdbcTemplate().update(sqlUpdateTask, editTask.getTask_header(),
						editTask.getTask_desc(), editTask.getTask_id());
				System.out.println(rowsAffectedTask);
				if (rowsAffectedTask > 0) {
					Map<String , Object> resultTask=new  HashMap<>();
					resultTask.put("message","Task edited Successfully !!!");
					return resultTask;
				} else {
					Map<String , Object> resultTask=new  HashMap<>();
					resultTask.put("message","Something went wrong !!!");
					return resultTask;
				}
			}
		else {
			Map<String , Object> resultTask=new  HashMap<>();
			resultTask.put("message", "Please Fill All the Fields");
			return resultTask;
		}
		
			
		}
		
		
	
		
		
}
