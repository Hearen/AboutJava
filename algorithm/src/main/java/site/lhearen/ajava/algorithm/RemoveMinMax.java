package site.lhearen.ajava.algorithm;

import java.util.Arrays;

public class RemoveMinMax {
    public static void main(String... args) {
        Double[] weights = {39.5, 34.8, 22.6, 38.7, 25.4, 30.1, 41.8, 33.6, 26.2, 27.3};
        Double[] ret = removeMinMax(weights);
        System.out.println(Arrays.toString(ret));
    }

    private static Double[] removeMinMax(Double[] arr) {
        if (arr == null || arr.length == 0) throw new IllegalArgumentException();
        // a trick to be used avoid lambda `effectively final` check;
        Double[] minMax = {arr[0], arr[0]};
        for (int i = 0; i < arr.length; ++i) {
            if (minMax[0].compareTo(arr[i]) > 0) minMax[0] = arr[i];
            if (minMax[1].compareTo(arr[i]) < 0) minMax[1] = arr[i];
        }
        return Arrays.stream(arr)
                .filter(a -> a.compareTo(minMax[0]) != 0 && a.compareTo(minMax[1]) != 0)
                .toArray(Double[]::new);
    }

    // Fixme: why comparable cannot be used in generics, so how it should be used?
//    private static <T extends Comparable<T>> T[] removeMinMax(T[] arr) {
//        if (arr == null || arr.length == 0) throw new IllegalArgumentException();
//        T minVal = arr[0];
//        T maxVal = arr[0];
//        for (int i = 0; i < arr.length; ++i) {
//            if (minVal.compareTo(arr[i]) > 0) minVal = arr[i];
//            if (maxVal.compareTo(arr[i]) < 0) maxVal = arr[i];
//        }
//        T minV = minVal, maxV = maxVal;
//        T[] ret = (T[]) Arrays.stream(arr)
//                .filter(a -> a.compareTo(minV) != 0 && a.compareTo(maxV) != 0)
//                .toArray(Comparable[]::new);
//        return ret;
////        List<T> ret = Arrays.stream(arr)
////                .filter(a -> a.compareTo(minV) != 0 && a.compareTo(maxV) != 0)
////                .collect(Collectors.toList());
////        return (T[]) ret.toArray();
//    }
//
}
