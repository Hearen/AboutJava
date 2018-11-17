package site.lhearen.ajava.base.collection;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.System.out;

public class TestListSort {

    class Car {
        String name;
        public String getName () { return this.name; }
        public Car(String name) {
            this.name = name;
        }
        @Override
        public String toString() { return "{ name: " + name + " }"; }
    }

    public static void main(String...args) {
        TestListSort test = new TestListSort();
        test.testBasicSort();
        out.println("Hi, there!");
    }

    private void testBasicSort() {
        List<String> fruits = Arrays.asList( "apple", "Apricot", "Banana", "mango", "melon", "Pineapple", "peach" );
        fruits.sort(String::compareToIgnoreCase);
        wrap(() -> fruits.forEach(out::println), "plain string list sort ignore case: ");

        Collections.shuffle(fruits);
        wrap(() -> fruits.forEach(out::println), "plain string list shuffled: ");

        fruits.sort(String.CASE_INSENSITIVE_ORDER);
        wrap(() -> fruits.forEach(out::println), "plain string list sort ignore case: ");

        Collections.shuffle(fruits);
        wrap(() -> fruits.forEach(out::println), "plain string list shuffled: ");

        List<String> newFruits = fruits.stream().sorted(String.CASE_INSENSITIVE_ORDER).collect(Collectors.toList());
        Collections.shuffle(fruits);
        wrap(() -> newFruits.forEach(out::println), "new plain string list sorted: ");


        List<Car> cars = new ArrayList<>();
        Stream.iterate(0, i -> i+1).limit(5).forEach((i) -> cars.add(new Car("BMW - " + i)));

        cars.sort(Comparator.comparing(Car::getName));
        wrap(() -> cars.forEach(out::print), "natural order: ");
        shuffle(cars);

        cars.sort(Comparator.comparing(Car::getName).reversed());
        wrap(() -> cars.forEach(out::print), "reversed: ");
        shuffle(cars);

        cars.sort((c0, c1) -> c0.getName().compareToIgnoreCase(c1.getName()));
        wrap(() -> cars.forEach(out::print), "natural order: ");
        shuffle(cars);

        cars.sort(Comparator.comparing(car -> car.getName().toLowerCase()));
        wrap(() -> cars.forEach(out::print), "natural order using comparing: ");
        shuffle(cars);

        cars.sort(Comparator.comparing(Car::getName, (name1, name2) -> name1.compareToIgnoreCase(name2)));
        wrap(() -> cars.forEach(out::print), "natural order using comparing: ");
        shuffle(cars);
    }

    private void wrap(Runnable runnable, String msg) {
        out.println(msg);
        runnable.run();
        out.println();
        out.println();
    }

    private void shuffle(List<Car> list) {
        Collections.shuffle(list);
        out.println("Shuffled: " + list);
        out.println();
    }
}
