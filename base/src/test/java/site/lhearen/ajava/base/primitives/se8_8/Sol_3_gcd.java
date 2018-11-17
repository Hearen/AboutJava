package site.lhearen.ajava.base.primitives.se8_8;

import java.math.BigInteger;

import org.junit.Test;

import static java.lang.System.out;
import static site.lhearen.ajava.mytools.util.Measure.getRunningTime;

public class Sol_3_gcd {
    @Test
    public void testGcd() {
        int a = -2;
        int b = 5;
        int testCount = 10_000_000;
        out.println(gcdBasic(a, b));
        out.println(gcdBigInteger(a, b));
        out.println(gcdFloorMod(a, b));
        out.println(gcdWithRem(a, b));
        getRunningTime(() -> { gcdBasic(a, b); }, testCount, "Basic");
        getRunningTime(() -> { gcdBigInteger(a, b); }, testCount, "BigInteger");
        getRunningTime(() -> { gcdFloorMod(a, b); }, testCount, "floorMod");
        getRunningTime(() -> { gcdWithRem(a, b); }, testCount, "customRem");
    }

    private int gcdBasic(int a, int b) {
        return a == 0? b : gcdBasic(b % a, a);
    }

    private int gcdBigInteger(int a, int b) {
        return BigInteger.valueOf(a).gcd(BigInteger.valueOf(b)).intValue();
    }

    private int gcdFloorMod(int a, int b) {
        return a == 0 ? b : gcdFloorMod(Math.floorMod(b, a), a);
    }

    private int gcdWithRem(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        return a == 0 ? b : gcdWithRem(rem(b, a), a);
    }

    private int rem(int a, int b) {
        if (a == b) {
            return 0;
        }
        if (a < b) {
            return a;
        }
        return a - (a / b) * b;
    }
}
