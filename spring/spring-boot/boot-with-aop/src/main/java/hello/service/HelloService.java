package hello.service;

import java.util.List;

import org.springframework.stereotype.Service;

import hello.aop.annotation.MethodTimer;
import hello.aop.annotation.MyAnnotation;
import hello.util.MyUtils;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HelloService {
    @MyAnnotation(message = "HelloService")
    @MethodTimer
    public String sayHello(String msg, int a, List<Integer> messages) {
        String message = "This is HelloService, nice to meet you!";
        System.out.println(message);
        log.info("Msg: {} and value: {}, messages: {}", msg, a, messages);
        return message;
    }

    private int testFinally() {
        int a = 0;
        try {
            a = Integer.parseInt("23");
            log.info("a parsed result: {}", a);
            return a;
        } catch (Exception e) {
            log.info("");
        } finally {
            a = 11;
            return a;
        }
    }

    @MethodTimer
    public void testCache() {
        MyUtils.testBasicCache();
        log.info("testFinally: {}", testFinally());
    }
}
