package com.example.demo.data.mapper.user;


import com.example.demo.dao.BaseDao;
import com.example.demo.entity.UserPayOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author ww
 * @Date 2020-04-22
 */
@Mapper
public interface UserPayOrderMapper extends BaseDao<UserPayOrder,String> {
    
    //自行扩展

    int updateByIdAndPayUserId(UserPayOrder userPayOrder);

    Integer queryStatus(@Param("id") String id,
                        @Param("userPayId") Long userPayId);
    
    UserPayOrder selectByPayTime(UserPayOrder userPayOrder);
}