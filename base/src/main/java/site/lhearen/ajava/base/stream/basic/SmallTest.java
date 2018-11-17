package site.lhearen.ajava.base.stream.basic;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.out;

public class SmallTest {
    public static void main(String...args) {
        List<Integer> list = Arrays.asList(1, 2, 3);
        List<List<Integer>> cSet = new ArrayList<>();
        cSet.add(new ArrayList<>());
        out.println(Arrays.deepToString(completeSet(list, 0, cSet).toArray()));
    }

    private static List<List<Integer>> completeSet(List<Integer> list, int i, List<List<Integer>> cSet) {
        if (i >= list.size()) {
            return cSet;
        }
        List<List<Integer>> newSet = new ArrayList<>(cSet);
        for (List<Integer> integerList : cSet) {
            List<Integer> added = new ArrayList<>(integerList);
            added.add(list.get(i));
            newSet.add(added);
        }
        return completeSet(list, i+1, newSet);
    }
}
