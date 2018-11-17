package site.lhearen.ajava.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class CutArrayToMatrix {
    public static void main(String... args) {
        test(CutArrayToMatrix::matrixizeByCopy);
        test(CutArrayToMatrix::matrixize);
    }

    private static void test(BiConsumer<Integer, Object[]> biConsumer) {
        int testTimes = 50;
        List<Integer> list = new ArrayList<>();
        Long start;
        List<Long> timer = new ArrayList<>();
        for (int i = 0; i < testTimes; ++i) {
            int N = new Random().nextInt(500) + 100_000;
            for (int j = 0; j < N; ++j) {
                list.add(new Random().nextInt());
            }
            Object[][] oo1 = matrixize(30, list.toArray());
            Object[][] oo2 = matrixizeByCopy(30, list.toArray());
            assert oo1.length == oo2.length : "length not equal";
            assert oo1[oo1.length - 1].length == oo2[oo2.length - 1].length : "last not equal";
            start = System.nanoTime();
            biConsumer.accept(30, list.toArray());
            timer.add(System.nanoTime() - start);
        }
        System.out.println(timer.stream().collect(Collectors.summarizingLong(Long::longValue)));
    }

    public static Object[][] matrixizeByCopy(Integer cutat, Object... data) {
        int rows = (int) Math.ceil(data.length / (double) cutat);
        Object[][] matrix = new Object[rows][cutat];
        int i = 0;
        int rowIndex = 0;
        while (i < data.length) {
            for (int j = 0; j < cutat; ++j) {
                matrix[rowIndex][j] = data[i++];
                if (i == data.length) return matrix;
            }
            rowIndex++;
        }
        return matrix;
    }

    public static Object[][] matrixize(int cutat, Object... data) {
        int rows = (int) Math.ceil(data.length / (double) cutat);
        Object[][] matrix = new Object[rows][];
        for (int i = 0; i < rows; i++) {
            matrix[i] = Arrays.copyOfRange(data, cutat * i, Math.min(data.length, cutat * (i + 1)));
        }
        return matrix;
    }
}
