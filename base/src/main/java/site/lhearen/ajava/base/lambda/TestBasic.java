package site.lhearen.ajava.base.lambda;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import static java.lang.System.out;

public class TestBasic {
    private static class Basic {
        public static void sayHi(int i) {
            System.out.println("Say Hi, World! Times: " + i);
        }
    }

    public static void test() {
        // ignoring the parentheses;
        IntStream.rangeClosed(0, 10).forEach(i -> Basic.sayHi(i));
    }


    class Car {
        public String name;
        public String getName() {
            return name;
        }
    }

    public void testLocal() {
        int theLocal = 6;
        Car bmw = new Car();
        bmw.name = "BMW";
        Stream.iterate(0, i -> i + 2).limit(2)
        .forEach(i -> {
//            bmw = new Car();
            bmw.name = "BMW NEW";
            System.out.println("Testing local variables: " + (theLocal + i));

        });

        Stream.generate(bmw::getName).limit(2).forEach(out::println);
        Stream.iterate(0, i -> i + 1).limit(2).forEach(Basic::sayHi);
        // have to comment this to ensure it's `effectively final`;
//        theLocal = 2;
    }

    public static void testSorter(){
        List<Integer> numbers = Arrays.asList(3, 5, 1, 2, 6);
        // sort is a default method
        // naturalOrder is a static method
        numbers.sort(Comparator.naturalOrder());
        System.out.println(numbers);
        numbers.sort(Comparator.reverseOrder());
        System.out.println(numbers);
    }

    public static String addHeader(String text){
        return "From Raoul, Mario and Alan:" + text;
    }

    public static String addFooter(String text){
        return text + "Kind regards";
    }

    public static String checkSpelling(String text){
        return text.replaceAll("C\\+\\+", "**Censored**");
    }


    public static void testFunctionChain(){
        Function<String, String> addHeader = TestBasic::addHeader;
        Function<String, String> transformationPipeline
                = addHeader.andThen(TestBasic::checkSpelling)
                .andThen(TestBasic::addFooter);

        System.out.println(transformationPipeline.apply("C++ stay away from me!"));
    }
}
