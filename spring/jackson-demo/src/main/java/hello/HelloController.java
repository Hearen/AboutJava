package hello;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {
    @Autowired
    private HelloService helloService;

    @RequestMapping("/")
    public String index() throws IOException {
        helloService.testJackson();
        return "Greetings from Spring Boot!";
    }

}

