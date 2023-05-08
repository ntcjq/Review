package com.sea.review.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class LoggerAspect {

    /**
     * 定义切入点：对要拦截的方法进行定义与限制，如包、类
     * <p>
     * 1、execution(public * *(..)) 任意的公共方法
     * 2、execution（* set*（..）） 以set开头的所有的方法
     * 3、execution（* com.sea.annotation.LoggerApply.*（..））com.sea.annotation.LoggerApply这个类里的所有的方法
     * 4、execution（* com.sea.annotation.*.*（..））com.sea.annotation包下的所有的类的所有的方法
     * 5、execution（* com.sea.annotation..*.*（..））com.sea.annotation包及子包下所有的类的所有的方法
     * 6、execution(* com.sea.annotation..*.*(String,?,Long)) com.sea.annotation包及子包下所有的类的有三个参数，第一个参数为String类型，第二个参数为任意类型，第三个参数为Long类型的方法
     * 7、execution(@annotation(com.sea.review.aspect.SeaLog))
     */
    @Pointcut("@annotation(com.sea.review.aspect.SeaLog)")
    private void cutMethod() {

    }

    /**
     * 前置通知：在目标方法执行前调用
     */
    @Before("cutMethod()")
    public void begin() {
        System.out.println("==@Before== sea blog logger : begin");
    }

    /**
     * 后置通知：在目标方法执行后调用，若目标方法出现异常，则不执行
     */
    @AfterReturning("cutMethod()")
    public void afterReturning() {
        System.out.println("==@AfterReturning== sea blog logger : after returning");
    }

    /**
     * 后置/最终通知：无论目标方法在执行过程中是否出现异常，都会执行
     */
    @After("cutMethod()")
    public void after() {
        System.out.println("==@After== sea blog logger : finally returning");
    }

    /**
     * 异常通知：目标方法抛出异常时执行
     */
    @AfterThrowing(pointcut = "cutMethod()", throwing = "e")
    public void afterThrowing(Throwable e) {
        System.out.println("==@AfterThrowing== sea blog logger : after throwing, msg = " + e.getMessage());
    }

    /**
     * 环绕通知：灵活自由的在目标方法中切入代码
     */
    @Around("cutMethod()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取目标方法的名称
        String methodName = joinPoint.getSignature().getName();
        // 获取方法传入参数
        Object[] params = joinPoint.getArgs();
        SeaLog seaLog = getDeclaredAnnotation(joinPoint);
        System.out.println("==@Around Before== sea blog logger --》 method name: " + methodName + " ,args: " + (params.length > 0 ? params[0] : null));
        // 执行源方法
        Object proceed = joinPoint.proceed();
        // 模拟进行验证
        System.out.println("==@Around After== sea blog logger --》 method name: " + methodName);
        return proceed;
    }

    /**
     * 获取方法中声明的注解
     *
     * @param joinPoint
     * @return
     * @throws NoSuchMethodException
     */
    public SeaLog getDeclaredAnnotation(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        // 获取方法名
        String methodName = joinPoint.getSignature().getName();
        // 反射获取目标类
        Class<?> targetClass = joinPoint.getTarget().getClass();
        // 拿到方法对应的参数类型
        Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
        // 根据类、方法、参数类型（重载）获取到方法的具体信息
        Method objMethod = targetClass.getMethod(methodName, parameterTypes);
        // 拿到方法定义的注解信息
        SeaLog annotation = objMethod.getDeclaredAnnotation(SeaLog.class);
        // 返回
        return annotation;
    }

}
