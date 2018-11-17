package site.lhearen.ajava.base.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ListListParallelStream {
    public static void main(String... args) {
        for (int i = 0; i < 1; ++i) {
            testFixedThreadPool();
        }
    }

    private static void testFixedThreadPool() {
        try {
            Executors.newFixedThreadPool(4).invokeAll(IntStream.range(0, 8).mapToObj(i -> (Callable<Integer>) () -> {
                System.out.println(Thread.currentThread().getName() + ": " + i);
                return 0;
            }).collect(Collectors.toList()));
        } catch (InterruptedException ignored) {
            ignored.printStackTrace();
        }
    }

    // using the core as the count;
    // since your task is CPU-bound, we can directly use parallelStream;
    private static void testThreadPool(List<Integer> listDir) {
        // if one of the tasks failed, you got isFailed == true;
        boolean isFailed = splitList(listDir, Runtime.getRuntime().availableProcessors()).stream()
                .parallel().map(ListListParallelStream::parallelDeleteOperation).anyMatch(ret -> ret == false);

    }


    // split up the list into "count" lists;
    private static <T> List<List<T>> splitList(List<T> list, int count) {
        List<List<T>> listList = new ArrayList<>();
        for (int i = 0, blockSize = list.size() / count; i < count; ++i) {
            listList.add(list.subList(i * blockSize, Math.min((i + 1) * blockSize, list.size())));
        }
        return listList;
    }

    private static boolean parallelDeleteOperation(List<Integer> list) {
        return System.currentTimeMillis() % 2 == 0;
    }
}
