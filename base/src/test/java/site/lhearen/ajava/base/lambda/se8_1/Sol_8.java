package site.lhearen.ajava.base.lambda.se8_1;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static site.lhearen.ajava.mytools.constants.Constants.NAMES;
import static java.lang.System.out;

public class Sol_8 {
    @Test
    public void testClosure() {
        String[] names = NAMES.stream().toArray(String[]::new);
        List<Runnable> runnableList = new ArrayList<>();
        for (String name : names) {
            runnableList.add(() -> {
                out.println(name);
            });
        }
        runnableList.stream().forEach(Runnable::run);

        runnableList.clear();
        for (int i = 0; i < names.length; i++) { // exactly the same as the previous using temporary variables;
            String name = names[i];
            runnableList.add(() -> {
                out.println(name); // variables used in lambda must be effectively final;
            });
        }
        runnableList.stream().forEach(Runnable::run);

        runnableList.clear();
        int[] index = {0};
        for (int i = 0; i < names.length; ++i) {
            runnableList.add(() -> out.println(names[index[0]++]));
        }
        runnableList.stream().forEach(Runnable::run);

        runnableList.clear();
        String[] tmp = {""};
        for (int i = 0; i < names.length; ++i) {
            tmp[0] = names[i];
            runnableList.add(() -> out.println(tmp[0])); // binding to the same variable now;
        }
        runnableList.stream().forEach(Runnable::run);
    }
}
