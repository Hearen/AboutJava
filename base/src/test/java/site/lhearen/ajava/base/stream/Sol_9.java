package site.lhearen.ajava.base.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;

import static java.lang.System.out;
import static java.util.stream.Collectors.toList;

public class Sol_9 {
    @Test
    public void testListMerge() {
        List<List<Integer>> listList = new ArrayList<>();
        listList.add(new ArrayList<>(Arrays.asList(1, 2, 3, 4)));
        listList.add(new ArrayList<>(Arrays.asList(5, 6, 7)));
        mergeOne(listList.stream()).forEach(out::print);
        out.println("****************");
        mergeTwo(listList.stream()).forEach(out::print);
        out.println("****************");
        mergeThree(listList.stream()).forEach(out::print);
        out.println("****************");
        mergeFour(listList.stream()).forEach(out::print);
        out.println("****************");
        mergeFive(listList.stream()).forEach(out::print);
        out.println("****************");
    }

    private <T> List<T> mergeOne(Stream<List<T>> listStream) {
        return listStream.flatMap(List::stream).collect(toList());
    }

    private <T> List<T> mergeTwo(Stream<List<T>> listStream) {
        List<T> result = new ArrayList<>();
        listStream.forEach(result::addAll);
        return result;
    }

    private <T> List<T> mergeThree(Stream<List<T>> listStream) {
        return listStream.reduce(new ArrayList<>(), (l1, l2) -> {
            l1.addAll(l2);
            return l1;
        });
    }

    private <T> List<T> mergeFour(Stream<List<T>> listStream) {
        return listStream.reduce((l1, l2) -> {
            List<T> l = new ArrayList<>(l1);
            l.addAll(l2);
            return l;
        }).orElse(new ArrayList<>());
    }

    private <T> List<T> mergeFive(Stream<List<T>> listStream) {
        return listStream.collect(ArrayList::new, List::addAll, List::addAll);
    }

}
