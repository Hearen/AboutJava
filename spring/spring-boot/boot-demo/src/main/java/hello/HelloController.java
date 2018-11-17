package hello;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class HelloController {

    @RequestMapping("/sayHi")
    public String index() {
        log.debug("sayHi in debug level: {}", "are you serious?");
        log.info("sayHi in info level: {}", "are you serious?");
        log.warn("sayHi in warn level: {}", "are you serious?");
        log.error("sayHi in error level: {}", "are you serious?");
        log.error("Get the logger name: {}", log.getName());
        return "Greetings from Spring Boot!";
    }


    @GetMapping("/string/{names}")
    public String getInfo(@PathVariable List<String> names) {
        // http://localhost:8888/Tom,Jack
        // request by list of strings
        System.out.println(names.toString());
        names.stream().forEach(System.out::println);
        return "Done";
    }

    @GetMapping("/int/{numbers}")
    public String getNumbers(@PathVariable List<Integer> numbers) {
        // http://localhost:8888/Tom,Jack
        // request by list of strings
        System.out.println(numbers.toString());
        numbers.stream().forEach(System.out::println);
        return "Done";
    }
}

