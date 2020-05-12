package com.example.demo.data.mapper.main;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {

	@Select("select * from user where token=#{token,jdbcType=VARCHAR}")
	User getUserByToken(@Param("token") String token);
}
