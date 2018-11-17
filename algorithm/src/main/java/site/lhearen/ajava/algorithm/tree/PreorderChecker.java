package site.lhearen.ajava.algorithm.tree;

public class PreorderChecker {
    public static void main(String... args) {
        int[] arr = {40, 30, 35, 80, 100};
        System.out.println(isPreorder(arr));
        arr = new int[]{2};
        System.out.println(isPreorder(arr));
        arr = new int[]{2, 1};
        System.out.println(isPreorder(arr));
        arr = new int[]{2, 3};
        System.out.println(isPreorder(arr));
        arr = new int[]{2, 1, 3};
        System.out.println(isPreorder(arr));
        arr = new int[]{Integer.MIN_VALUE + 1, Integer.MIN_VALUE, Integer.MAX_VALUE};
        System.out.println(isPreorder(arr));
        arr = new int[]{5, 3, 2, 4, 8, 6, 9};
        System.out.println(isPreorder(arr));

        arr = new int[]{2, 3, 1};
        System.out.println(isPreorder(arr));
    }

    // rootVal is the root between [startIndex, endIndex];
    private static boolean checkHelper(int[] arr, int rootVal, int startIndex, int endIndex) {
        if (startIndex >= endIndex) return true;
        int firstIndex = startIndex; // the first bigger index;
        while (firstIndex < endIndex) {
            if (arr[++firstIndex] > rootVal) {
                break;
            }
        }
        boolean left = checkHelper(arr, arr[startIndex+1], startIndex + 1, firstIndex - 1);
        for (int i = firstIndex + 1; i <= endIndex; ++i) {
            if (arr[i] < rootVal) return false;
        }
        return left && checkHelper(arr, arr[firstIndex], firstIndex, endIndex);
    }

    private static boolean isPreorder(int[] arr) {
        return checkHelper(arr, arr[0], 0,arr.length - 1);
    }
}
