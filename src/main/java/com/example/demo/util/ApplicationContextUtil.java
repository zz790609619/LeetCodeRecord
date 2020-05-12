package com.example.demo.util;


import org.springframework.context.ApplicationContext;

public class ApplicationContextUtil {
    private static ApplicationContext applicationContext;

    public static void setApplicationContextUtil(ApplicationContext context){
        applicationContext=context;
    }

    public static Object getBean(String beanId) {
        return applicationContext.getBean(beanId);
    }
    public static <T>T getBean(Class<T> clazz){
        return applicationContext.getBean(clazz);

    }
}
