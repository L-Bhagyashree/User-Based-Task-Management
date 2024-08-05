package com.jsontest.config;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper implements RowMapper<EmployeeModel> {

	@Override
	public EmployeeModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		
		EmployeeModel employee=new EmployeeModel();
		employee.setEmployeename(rs.getString("employeename"));
		employee.setRegistraion_id(rs.getInt("registraion_id"));
		System.out.println(employee);
		System.out.println("HI");
		return employee;
	}

}
