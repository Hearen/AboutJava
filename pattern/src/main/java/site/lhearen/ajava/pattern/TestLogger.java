package site.lhearen.ajava.pattern;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestLogger {
    public static void main(String... args) {
        log.debug("This is my first debug level message");
        log.info("This is my first info level message");
        log.warn("This is my first warn level message");
        log.error("This is my first error level message");
    }
}
