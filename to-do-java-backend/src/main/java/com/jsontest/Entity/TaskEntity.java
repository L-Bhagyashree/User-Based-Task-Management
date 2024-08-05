package com.jsontest.Entity;

public class TaskEntity {
	private Integer user_id;
	private Integer task_id;
	private String task_header;
	private String task_desc;
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Integer getTask_id() {
		return task_id;
	}
	public void setTask_id(Integer task_id) {
		this.task_id = task_id;
	}
	public String getTask_header() {
		return task_header;
	}
	public void setTask_header(String task_header) {
		this.task_header = task_header;
	}
	public String getTask_desc() {
		return task_desc;
	}
	public void setTask_desc(String task_desc) {
		this.task_desc = task_desc;
	}
	@Override
	public String toString() {
		return "TaskEntity [user_id=" + user_id + ", task_id=" + task_id + ", task_header=" + task_header
				+ ", task_desc=" + task_desc + "]";
	}
	
	
	
}
