package com.example.demo.config.aop.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Aspect 作用是把当前类标识为一个切面供容器读取
 * @Order 义Spring容器加载Bean的顺序
 * @Component 将类实例化到Spring容器中，交给Spring管理
 */
@Aspect
@Order
@Component
public class AnnoClassAspect {
    /**
     * @Pointcut：Pointcut是植入Advice的触发条件描述或定义一个切入点,切入点的定义需要遵循spring中指定的表达式规范。每个Pointcut的定义包括2部分，一是表达式，二是方法签名。
     * @Around：环绕增强，相当于MethodInterceptor,在此方法中可以添加扩展业务逻辑，可以调用下一个切面对象或目标方法
     * @AfterReturning：后置增强，相当于AfterReturningAdvice，方法正常退出时执行
     * @Before：标识一个前置增强方法，相当于BeforeAdvice的功能，相似功能的还有
     * @AfterThrowing：异常抛出增强，相当于ThrowsAdvice
     * @After: final增强，不管是抛出异常或者正常退出都会执行
     */
    @Pointcut("@annotation(annoClass)")
    public void servicePointCut(AnnoClass annoClass){
        //方法签名必须是public及void型。可以将Pointcut中的方法看作是一个被Advice引用的助记符，因为表达式不直观，因此我们可以通过方法签名的方式为此表达式命名。因此Pointcut中的方法只需要方法签名，而不需要在方法体内编写实际代码。
    }
    @Around(value="servicePointCut(annoClass)")
    public String aroundMethod(ProceedingJoinPoint point, AnnoClass annoClass) throws Exception{
        System.out.println("AnnoClassAspect");
        //ProceedingJoinPoint:表示目标类连接点对象
        String result="AnnoClassAspect";
        try{
            result+=(String)point.proceed();
        }catch (Throwable ex){

        }
        return result;

    }

}
