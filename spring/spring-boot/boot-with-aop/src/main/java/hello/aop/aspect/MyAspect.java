package hello.aop.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import hello.aop.annotation.MyAnnotation;

@Aspect
@Component("MyAspect")
public class MyAspect {
    // reference: http://www.baeldung.com/spring-aop-annotation
    @Around("@annotation(hello.aop.annotation.MyAnnotation)")
    public Object testEmptyAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Class<?> classInstance = method.getDeclaringClass();
        System.out.println(String.format("%s method invoked in %s", method.getName(), classInstance.getName()));
        return joinPoint.proceed();
    }

    @Around("@annotation(myAnnotation)")
    public Object testAnnotationWithMsg(ProceedingJoinPoint joinPoint, MyAnnotation myAnnotation) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Class<?> classInstance = method.getDeclaringClass();
        System.out.println(String.format("%s method with message %s invoked in %s", method.getName(),
                myAnnotation.message(), classInstance.getName()));
        return joinPoint.proceed();
    }
}

