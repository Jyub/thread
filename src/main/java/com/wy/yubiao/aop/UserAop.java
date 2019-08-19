package com.wy.yubiao.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/8/19 16:33
 * @description: TODO
 */
@Aspect
@Configuration
public class UserAop {

    @Pointcut("execution(* com.wy.yubiao.aop.User.setId(..))")
    public void aspect(){}

    @Around("aspect()")
    public void around(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("开始拦截");
        jp.proceed(jp.getArgs());
        System.out.println("拦截结束");
    }

}
