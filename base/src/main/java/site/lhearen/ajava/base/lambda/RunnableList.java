package site.lhearen.ajava.base.lambda;

import java.util.ArrayList;
import java.util.List;

public class RunnableList {
    public static void main(String... args) {
        int N = 5;
        List<Runnable> list = new ArrayList<>();
        for (int i = 0; i < N; ++i) {
            list.add(getRunnable(i));
        }

        list.forEach(Runnable::run);
    }

    private static Runnable getRunnable(int i) {
        return () -> {
            System.out.println(i);
        };
    }
}
