package com.jsontest.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jsontest.Entity.TaskEntity;


@Component
public class TaskMapper implements RowMapper<TaskEntity> {

	@Override
	public TaskEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
		TaskEntity taskSet=new TaskEntity();
		taskSet.setUser_id(rs.getInt("user_id"));
		taskSet.setTask_header(rs.getString("task_header"));
		taskSet.setTask_desc(rs.getString("task_desc"));;
		return taskSet;
	}	

}
