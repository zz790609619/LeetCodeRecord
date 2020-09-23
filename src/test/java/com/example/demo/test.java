package com.example.demo;

import org.apache.ibatis.session.Configuration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class test {
	private static Configuration configuration;
	@Test
	public void contextLoads(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar beforeTime = Calendar.getInstance();
		beforeTime.add(Calendar.DATE, -1);;// 3分钟之前的时间
		Date beforeD = beforeTime.getTime();
		String createDate = sdf.format(beforeD);
		System.out.println(createDate);
	}



}
