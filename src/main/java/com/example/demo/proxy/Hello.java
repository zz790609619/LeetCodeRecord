package com.example.demo.proxy;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

/**
 * @author ww
 * @version 1.0
 * @date 2020/9/14 16:08
 */
public class Hello {
    public static void main(String[] args) {
        //动态代理模式
        //      编译         类装载            转译
        //.java ---> .class ------> jvm指令码 ---->  机器语言
        // 动态生成 ----> .class
        // (将没有实现类的接口里的class字节码动态编译成.class)
        // javassist or cglib or asm（字节码编辑库）
        System.out.println("xxx");
        try {
            ITestService proxy=createProxy();
            proxy.sayHello("xxxxxxx");
            ITestService2 proxy2=createProxy2(ITestService2.class, "{System.out.println(\"hello:\"+$1);}");
            proxy2.sayHello2("ww");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 最基础的动态代理 需要代理的接口和接口实现方法都需要手动写入
     * @return
     * @version 1
     * @throws Exception
     */
    public static ITestService createProxy()throws Exception{
        // 1.初始化javassist 工具类
        ClassPool classPool=new ClassPool();
        // 2.类装载
        classPool.appendSystemPath();
        // 3.创建一个类作为实现类
        CtClass ctClass=classPool.makeClass("TestServiceImpl");
        // 4.给实现类增加接口
        ctClass.addInterface(classPool.get(ITestService.class.getName()));
        // 5.给实现类新增方法
        CtMethod ctMethod= CtNewMethod.make(CtClass.voidType,
                "sayHello",
                new CtClass[]{classPool.get(String.class.getName())},new CtClass[0],"{System.out.println(\"hello:\"+$1);}",ctClass);
        ctClass.addMethod(ctMethod);
        // 6.实例化对象
        Class a=classPool.toClass(ctClass);
        return (ITestService) a.newInstance();

    }

    /**
     * 没有实现类的接口方法
     * @version 1
     */
    public interface  ITestService{
        void sayHello(String name);
    }

    /**
     * 最基础的动态代理 需要代理的接口和接口实现方法作为参数传入 多方法不支持
     * @return
     * @version 2
     * @throws Exception
     */
    public static <T>T createProxy2(Class<T> t,String method)throws Exception{
        // 1.初始化javassist 工具类
        ClassPool classPool=new ClassPool();
        // 2.类装载
        classPool.appendSystemPath();
        // 3.创建一个类作为实现类
        CtClass ctClass=classPool.makeClass("TestServiceImpls");
        // 4.给实现类增加接口
        ctClass.addInterface(classPool.get(t.getName()));
        // 5.给实现类新增方法
        CtMethod ctMethod= CtNewMethod.make(CtClass.voidType,
                "sayHello2",
                new CtClass[]{classPool.get(String.class.getName())},new CtClass[0],method,ctClass);
        ctClass.addMethod(ctMethod);
        // 6.实例化对象
        Class a=classPool.toClass(ctClass);
        return (T) a.newInstance();

    }

    /**
     * 没有实现类的接口方法
     * @version 2
     */
    public interface  ITestService2{
        void sayHello2(String name);
    }

}
