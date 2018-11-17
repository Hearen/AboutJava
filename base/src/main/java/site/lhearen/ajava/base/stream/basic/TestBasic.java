package site.lhearen.ajava.base.stream.basic;

import static java.lang.System.out;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.averagingInt;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.summarizingInt;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.BinaryOperator;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class TestBasic {

    List<Dish> menu = Dish.menu;

    public static void main(String... args) {
//        testFlatMap();
        testFib();
    }

    private static void testFlatMap() {
        String[] arrayOfWords = {"Hello", "World"};
        List<String> uniqueS = Arrays.stream(arrayOfWords)
                .flatMap(word -> Arrays.stream(word.split("")))
                .distinct()
                .collect(toList());
        out.println(uniqueS);

        List<Integer> num1 = Arrays.asList(1, 2, 3);
        List<Integer> num2 = Arrays.asList(4, 5);
        List<int[]> pairs = num1.stream()
                .flatMap(n1 -> num2.stream().map(n2 -> new int[]{n1, n2}))
                .collect(toList());
        pairs.stream().forEach(pair -> out.println(pair[0] + ": " + pair[1]));

        List<int[]> triples = IntStream.rangeClosed(1, 100).boxed()
                .flatMap(a ->
                        IntStream.rangeClosed(1, 100)
                                .filter(b -> a <= b && Math.sqrt(a * a + b * b) % 1 == 0).boxed()
                                .map(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)}))
                .collect(toList());

        triples.stream().forEach(triple -> out.println("a: " + triple[0] + " b: " + triple[1] + " c: " + triple[2]));
    }

    public static void testFib() {
        Stream.iterate(new int[]{0, 1},
                t -> new int[] {t[1], t[0] + t[1]})
                .limit(10)
                .map(t -> t[0])
                .forEach(out::println);
//                .forEach(t -> out.println(t[0] + ":" + t[1]));

        IntSupplier fibSupplier = new IntSupplier() {
            private int prev = 0;
            private int cur = 1;
            @Override
            public int getAsInt() {
                int old = this.prev;
                int next = this.prev + this.cur;
                this.prev = this.cur;
                this.cur = next;
                return old;
            }
        };
        IntStream.generate(fibSupplier).limit(10).forEach(out::println);
    }


    public void testBasic() {
        List<Dish> menu = Dish.menu;
        List<String> lowCalorieDishes = menu.stream()
                .filter(d -> d.getCalories() < 400)
                .sorted(comparing(Dish::getCalories))
                .map(Dish::getName)
                .collect(toList());
        lowCalorieDishes.forEach(System.out::println);
    }

    public void testCalculation() {
        List<Integer> numbers = Arrays.asList(3, 4, 5, 1, 2);
        int sum = numbers.stream().reduce(0, (a, b) -> a + b);
        System.out.println(sum);

        int sum2 = numbers.stream().reduce(0, Integer::sum);
        System.out.println(sum2);

        int max = numbers.stream().reduce(0, (a, b) -> Integer.max(a, b));
        System.out.println(max);

        Optional<Integer> min = numbers.stream().reduce(Integer::min);
        min.ifPresent(System.out::println);

        List<Dish> menu = Dish.menu;
        int calories = menu.stream()
                .map(Dish::getCalories)
                .reduce(0, Integer::sum);
//        calories = menu.stream().mapToInt(Dish::getCalories).sum();
        // mapToInt or boxed to transform between specific stream (IntStream) and generalized Stream;
        System.out.println("Number of calories:" + calories);
    }

    public void testIntStream() { // hard to use;
        List<Dish> menu = Dish.menu;
        // max and OptionalInt
        OptionalInt maxCalories = menu.stream()
                .mapToInt(Dish::getCalories)
                .max();
        System.out.println("MaxCalories: " + maxCalories);

        int calories = menu.stream()
                .mapToInt(Dish::getCalories)
                .sum();
        System.out.println("Number of calories:" + calories);


        Stream<int[]> pythagoreanTriples =
                IntStream.rangeClosed(1, 100).boxed()
                        .flatMap(a -> IntStream.rangeClosed(a, 100)
                                .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0).boxed()
                                .map(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)}));

        pythagoreanTriples.forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));
    }

    public void testStream() {
        List<String> names = Arrays.asList("Java8", "Lambdas", "In", "Action");
        Stream<String> s = names.stream();
        s.forEach(System.out::println);
        // uncommenting this line will result in an IllegalStateException
        // because streams can be consumed only once
//        s.forEach(System.out::println);

        // Stream.of
        Stream<String> stream = Stream.of("Java 8", "Lambdas", "In", "Action");
        stream.map(String::toUpperCase).forEach(System.out::println);

        // Stream.empty
        Stream<String> emptyStream = Stream.empty();

        // Arrays.stream
        int[] numbers = {2, 3, 5, 7, 11, 13};
        System.out.println(Arrays.stream(numbers).sum());

        // Stream.iterate
        Stream.iterate(0, n -> n + 2)
                .limit(10)
                .forEach(System.out::println);

        // fibonnaci with iterate
        Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]})
                .limit(10)
                .forEach(t -> System.out.println("(" + t[0] + ", " + t[1] + ")"));

        Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]})
                .limit(10)
                .map(t -> t[0])
                .forEach(System.out::println);

        // random stream of doubles with Stream.generate
        Stream.generate(Math::random)
                .limit(10)
                .forEach(System.out::println);

        // stream of 1s with Stream.generate
        IntStream.generate(() -> 1)
                .limit(5)
                .forEach(System.out::println);

        IntStream.generate(new IntSupplier() {
            public int getAsInt() {
                return 2;
            }
        }).limit(5)
                .forEach(System.out::println);


        IntSupplier fib = new IntSupplier() {
            private int previous = 0;
            private int current = 1;

            public int getAsInt() {
                int nextValue = this.previous + this.current;
                this.previous = this.current;
                this.current = nextValue;
                return this.previous;
            }
        };
        IntStream.generate(fib).limit(10).forEach(System.out::println);

        List<String> words = Arrays.asList("Hello", "World");
        List<Integer> wordLengths = words.stream()
                .map(String::length)
                .collect(toList());
        wordLengths.stream().forEach(System.out::println);

        // flatMap
        List<Integer> numbers1 = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> numbers2 = Arrays.asList(6, 7, 8);
        List<int[]> pairs =
                numbers1.stream()
                        .flatMap(i -> numbers2.stream().map(j -> new int[]{i, j}))
                        .filter(pair -> (pair[0] + pair[1]) % 3 == 0)
                        .collect(toList());
        pairs.forEach(pair -> System.out.println("(" + pair[0] + ", " + pair[1] + ")"));

        try {
            long uniqueWords = Files.lines(Paths.get("./src/TestComparator.java"), Charset.defaultCharset())
                    .flatMap(line -> Arrays.stream(line.split(" ")))
                    .distinct()
                    .count();
            System.out.println("There are " + uniqueWords + " unique words in TestComparator.java");
        } catch (IOException ignored) {
            ignored.printStackTrace();
            System.out.println(ignored.toString());
        }
    }

    public void testSet() {
        List<String> names = Arrays.asList("Java8", "Lambdas", "In", "In", "Action");
        names.stream().forEach(System.out::print);
        System.out.println();
        names.stream().collect(Collectors.toSet()).stream().forEach(System.out::print);
        System.out.println();
        names.stream().distinct().forEach(System.out::print);
        System.out.println();
    }

    public void testReducing() {
        System.out.println("Total calories in menu: " + calculateTotalCalories());
        System.out.println("Total calories in menu: " + calculateTotalCaloriesWithMethodReference());
        System.out.println("Total calories in menu: " + calculateTotalCaloriesWithoutCollectors());
        System.out.println("Total calories in menu: " + calculateTotalCaloriesUsingSum());
        System.out.println("Nr. of dishes: " + howManyDishes());
        System.out.println("The most caloric dish is: " + findMostCaloricDish());
        System.out.println("The most caloric dish is: " + findMostCaloricDishUsingComparator());
        System.out.println("Total calories in menu: " + calculateTotalCalories());
        System.out.println("Average calories in menu: " + calculateAverageCalories());
        System.out.println("Menu statistics: " + calculateMenuStatistics());
        System.out.println("Short menu: " + getShortMenu());
        System.out.println("Short menu comma separated: " + getShortMenuCommaSeparated());
    }

    private int calculateTotalCaloriesWithMethodReference() {
        return menu.stream().collect(reducing(0, Dish::getCalories, Integer::sum));
    }

    private int calculateTotalCaloriesWithoutCollectors() {
        return menu.stream().map(Dish::getCalories).reduce(Integer::sum).get();
    }

    private int calculateTotalCaloriesUsingSum() {
        return menu.stream().mapToInt(Dish::getCalories).sum();
    }

    private long howManyDishes() {
        return menu.stream().collect(counting());
    }

    private Dish findMostCaloricDish() {
        return menu.stream().collect(reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2)).get();
    }

    private Dish findMostCaloricDishUsingComparator() {
        Comparator<Dish> dishCaloriesComparator = Comparator.comparingInt(Dish::getCalories);
        BinaryOperator<Dish> moreCaloricOf = BinaryOperator.maxBy(dishCaloriesComparator);
        return menu.stream().collect(reducing(moreCaloricOf)).get();
    }

    private int calculateTotalCalories() {
        return menu.stream().collect(summingInt(Dish::getCalories));
    }

    private Double calculateAverageCalories() {
        return menu.stream().collect(averagingInt(Dish::getCalories));
    }

    private IntSummaryStatistics calculateMenuStatistics() {
        return menu.stream().collect(summarizingInt(Dish::getCalories));
    }

    private String getShortMenu() {
        return menu.stream().map(Dish::getName).collect(joining());
    }

    private String getShortMenuCommaSeparated() {
        return menu.stream().map(Dish::getName).collect(joining(", "));
    }

}

