package com.example.demo.config.aop.annotation;

import java.lang.annotation.*;

/**
 * @Target 描述了注解修饰的对象范围
 * method ：方法
 * PACKAGE：包
 * PARAMETER：变量
 * type：类、接口、enum类型
 * @Retention 注解保留时间长短
 * source：源文件有效，编译中忽略
 * class： 编译存在，运行时忽略
 * runtime：运行时候有效
 * @Inherited 该注解会被子类继承，注意，仅针对类(成员属性、方法并不受此注释的影响)
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface  AnnoClass {

}
