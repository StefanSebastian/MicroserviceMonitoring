package com.example.client;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author stefansebii@gmail.com
 */
@Aspect
@Component
public class TimingAspect {
    @Around("@annotation(requestMapping)")
    public Object measureExecutionTime(ProceedingJoinPoint thisJoinPoint, RequestMapping requestMapping) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = thisJoinPoint.proceed();
        System.out.println(thisJoinPoint + " ---> " + (System.currentTimeMillis() - startTime) + " ms");
        return result;
    }
}
