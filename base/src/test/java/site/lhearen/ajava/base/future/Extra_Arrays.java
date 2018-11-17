package site.lhearen.ajava.base.future;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import static java.lang.System.out;

public class Extra_Arrays {
    @Test
    public void testArrays() {
        Object[] nestedArr = new Object[1];
        nestedArr[0] = nestedArr;
        out.println(Arrays.deepToString(nestedArr));

        int[][] nestedArr0 = { { 1 }, { 2 }};
        int[][] nestedArr1 = { { 1 }, { 2 }};
        int[][] nestedArr2 = { { 1 }, { 3 }};
        Assert.assertTrue(Arrays.deepEquals(nestedArr0, nestedArr1));
        Assert.assertFalse(Arrays.deepEquals(nestedArr0, nestedArr2));
    }
}
