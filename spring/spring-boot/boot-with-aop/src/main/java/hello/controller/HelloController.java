package hello.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import hello.aop.annotation.MyAnnotation;
import hello.service.HelloService;

@RestController
@RequestMapping("/")
public class HelloController {
    @Autowired
    private HelloService helloService;

    @GetMapping("/index")
    @MyAnnotation
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @GetMapping("/hello")
    public String sayHello() {
        return helloService.sayHello("Yo'all, have fun!", 6, Arrays.asList(1, 2, 3));
    }

    @GetMapping("/cache")
    public void testCache() {
        helloService.testCache();
    }

}

