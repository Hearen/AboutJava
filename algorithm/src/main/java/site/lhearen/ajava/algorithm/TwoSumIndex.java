package site.lhearen.ajava.algorithm;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TwoSumIndex {
    public static void main(String... args) {
        int[] arr = {2, 7, 11, 15, 9, 0, 2, 7};
        System.out.println(findTwoSums(arr, 9));
    }

    private static List<List<Integer>> findTwoSums(int[] arr, int target) {
        Set<Integer> theSet = Arrays.stream(arr).mapToObj(Integer::valueOf).collect(Collectors.toSet());
        List<Integer> arrList = Arrays.stream(arr).mapToObj(Integer::valueOf).collect(Collectors.toList());
        List<Pair<Integer, Integer>> theList = new ArrayList<>();
        List<Integer> added = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            int a = target - arr[i];
            if (theSet.contains(a) && added.indexOf(i) < 0) { // avoid duplicates;
                Integer theOther = arrList.indexOf(a);
                theList.add(new Pair(i, theOther));
                added.add(i);
                added.add(theOther);
            }
        }
        return theList.stream().map(pair -> new ArrayList<>(Arrays.asList(pair.getKey(), pair.getValue())))
                .collect(Collectors.toList());
    }

}
