package site.lhearen.ajava.algorithm;

public class TestWhetherMaxHeap {
    public static void main(String... args) {
        int[] A = {50, 45, 40, 35, 20, 25, 20};
        int[] B = {45, 50, 40, 35, 20, 25, 20};
        System.out.println(isMaxHeap(A));
        System.out.println(isMaxHeap(B));
    }

    private static boolean isMaxHeap(int[] arr) {
        int N = arr.length;
        for (int i = (N - 2) / 2; i > -1; --i) { // start from the first internal node who has children;
            int j = 2 * i + 1; // the left child;
            if (j < N - 1 && arr[i] < arr[j+1]) j++; // select the bigger child;
            if (arr[i] < arr[j]) return false; // if parent is smaller than the child;
        }
        return true;
    }
}
