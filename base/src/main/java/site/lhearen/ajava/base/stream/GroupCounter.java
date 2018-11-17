package site.lhearen.ajava.base.stream;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class GroupCounter {
    public static void main(String... args) {
        String[][] array = {
                { "red", "value1", "alpha"  },
                { "blue", "value2", "alpha"  },
                { "green", "value3", "Alpha"  },
                { "black", "value4", "Alpha" },
                { "grey", "value1", "beta"  },
                { "white", "value2", "Beta"  },
        };

        Map<String, Long> countMap = Arrays.stream(array)
                .collect(Collectors.groupingBy(arr -> arr[2].toLowerCase(), Collectors.counting()));
        System.out.println(countMap);
    }
}
