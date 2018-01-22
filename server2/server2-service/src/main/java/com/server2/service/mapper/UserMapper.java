package com.server2.service.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.server2.service.model.User;

public interface UserMapper {

	@Select("select * from user where id=#{id}")
	public User selectById(@Param("id")Integer id);
}
