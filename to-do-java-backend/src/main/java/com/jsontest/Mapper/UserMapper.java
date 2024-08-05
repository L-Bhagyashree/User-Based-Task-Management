package com.jsontest.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jsontest.Entity.UserEntity;
@Component
public class UserMapper implements RowMapper<UserEntity> {

	@Override
	public UserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		UserEntity u1=new UserEntity();
		u1.setUser_name(rs.getString("user_name"));
		u1.setUser_email(rs.getString("user_email"));
		u1.setUser_password(rs.getString("user_password"));
		return u1;
	}

}
