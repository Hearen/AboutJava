package site.lhearen.ajava.spring.demos.annotation;

import java.lang.annotation.*;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class Sol_12_repeatable_annotation {

    @TestCase(params = 4, expected = 24)
    @TestCase(params = 0, expected = 1)
    public static int factorial(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }
        return factorial(n - 1) * n;
    }

    @Test
    public void testAnnotationArr() {
        try {
            TestCase[] testCases = Sol_12_repeatable_annotation.class.getMethod("factorial", int.class).getAnnotationsByType(TestCase.class);
            assertEquals(testCases.length, 2);
            for (TestCase testCase : testCases) {
                assertEquals(testCase.expected(), factorial(testCase.params()));
            }
        } catch (ReflectiveOperationException ignored) {
            ignored.printStackTrace();
        }
    }

    @Repeatable(TestCases.class)
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface TestCase {
        int params();

        int expected();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface TestCases {
        TestCase[] value();
    }
}
