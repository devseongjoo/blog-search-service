package com.example.blogsearchservice.config;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class AspectHandler {

    @Before("execution(* com.devkuma.spring.aop.SampleAopBean.*(..))")
    public void before() {
        System.out.println("before:");
    }

    @After("execution(* com.devkuma.spring.aop.SampleAopBean.*(..))")
    public void after() {
        System.out.println("after:");
    }
}
