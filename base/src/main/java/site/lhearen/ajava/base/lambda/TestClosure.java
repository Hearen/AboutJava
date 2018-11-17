package site.lhearen.ajava.base.lambda;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Predicate;

interface Collection2 extends Collection {
   default <T> void forEachIf(Consumer<T> action, Predicate<T> filter) {
       forEach(t -> {
           if (filter.test((T)t)) {
               action.accept((T)t);
           }
       });
   }
}

public class TestClosure {
    public static void main(String... args) {
        String[] names = {"Hearen", "Katherine"};
        for (String name : names) {
            new Thread(() -> System.out.println(name)).start();
        }

        int[] index = new int[1];
        for (index[0] = 0; index[0] < names.length; ++index[0]) {
            new Thread(() -> System.out.println(names[index[0]])).start();
        }
        System.out.println(Thread.currentThread().getName());
        Arrays.sort(names, (String a, String b) -> {
            System.out.println(Thread.currentThread().getName());
            return a.compareToIgnoreCase(b);
        });
        System.out.println(Thread.currentThread().getName());


    }
}
