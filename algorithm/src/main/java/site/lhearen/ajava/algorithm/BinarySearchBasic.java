package site.lhearen.ajava.algorithm;

import java.util.Arrays;

public class BinarySearchBasic {
    public static void main(String[] args) {
        BinarySearchBasic bs = new BinarySearchBasic();

        int array[] = {1, 8, 6, 91, 52, 74, 5, 9};
        int length = array.length;
        Arrays.sort(array);
        System.out.println(Arrays.toString(array));
        System.out.println();
        System.out.println(bs.binarySearch(array, 0, length - 1, 5)); // miss;
        System.out.println(bs.binarySearch(array, 0, length - 1, 8)); // hit!
    }

    public int binarySearch(int array[], int low, int high, int desired) {
        int mid = low + (high - low) /2;
        int pivot = array[mid]; // get the middle element;
        if (desired < pivot) {
            binarySearch(array, low, mid - 1, desired); // since it's already smaller, use mid - 1 directly;
        } else if (desired > pivot) {
            binarySearch(array, mid + 1, high, desired); // same reason, use mid + 1 directly to skip mid;
        } else {
            return mid; // bingo!
        }
        return -1; // not found;
    }
}
