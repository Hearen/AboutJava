package site.lhearen.ajava.mytools.util;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomGenerator {
    public static int[] generateArrays(int minSize, int maxSize, int low, int high, boolean isUnique) {
        Random random = new Random(System.currentTimeMillis());
        int N = random.nextInt(maxSize - minSize + 1) + minSize;
        if (isUnique) {
            Set<Integer> intSet = new HashSet<>();
            while (intSet.size() < N) {
                intSet.add(random.nextInt(high - low) + low);
            }
            return intSet.stream().mapToInt(Integer::intValue).toArray();
        } else {
            int[] arr = new int[N];
            for (int i = 0; i < N; ++i) {
                arr[i] = random.nextInt(high - low) + low;
            }
            return arr;
        }
    }
}
