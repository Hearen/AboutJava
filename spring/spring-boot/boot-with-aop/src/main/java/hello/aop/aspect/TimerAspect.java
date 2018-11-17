package hello.aop.aspect;

import static hello.util.MyUtils.cache;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import hello.aop.annotation.MethodTimer;
import hello.util.MyUtils;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Slf4j
@Component
public class TimerAspect {
    @Around("@annotation(methodTimer)")
    public Object logMethodRequests(ProceedingJoinPoint joinPoint, MethodTimer methodTimer)
            throws Throwable {
        Long start = System.currentTimeMillis();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String methodName = method.getName();
        Object[] myArgs = joinPoint.getArgs();
        int size = 0;
        for (Object obj: myArgs) {
            if (obj instanceof List<?>) {
                size = ((List<?>) obj).size();
            }
        }
        Object obj = null;
        try {
            obj = joinPoint.proceed();
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("Retrieving timeCost: {} ms in Method: {}, list size: {}, args: {}", System.currentTimeMillis() - start,
                    methodName, size, Arrays.deepToString(myArgs));
            log.info("Cache hitRate: {}, missRate: {} stats: {}", cache.stats().hitRate(),
                    cache.stats().missRate(), cache.stats());
        }
        return obj;
    }
}
