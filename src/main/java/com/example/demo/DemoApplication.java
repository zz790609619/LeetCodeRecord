package com.example.demo;

import com.example.demo.util.ApplicationContextUtil;
import org.apache.shardingsphere.shardingjdbc.spring.boot.SpringBootConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(exclude = {SpringBootConfiguration.class})
@ServletComponentScan
public class DemoApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext=SpringApplication.run(DemoApplication.class, args);
		ApplicationContextUtil.setApplicationContextUtil(applicationContext);
	}

}
