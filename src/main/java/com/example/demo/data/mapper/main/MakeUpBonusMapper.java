package com.example.demo.data.mapper.main;

import com.example.demo.entity.MakeUpBonus;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MakeUpBonusMapper {
	@Insert("INSERT INTO make_up_bonus(orderId,userId,merchantId,payType,makeUpFlag) VALUES(#{orderId},#{userId},#{merchantId},#{payType},#{makeUpFlag})")
	void insertMakeUpBonus(MakeUpBonus mub);


	@Select("select * from make_up_bonus")
	List<MakeUpBonus> ss();
}
