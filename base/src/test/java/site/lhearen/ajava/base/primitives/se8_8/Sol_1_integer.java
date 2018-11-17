package site.lhearen.ajava.base.primitives.se8_8;

import org.junit.Test;

import static java.lang.System.out;
import static org.junit.Assert.assertEquals;

public class Sol_1_integer {
    /**
     Integer - Max range
     Signed: From −2,147,483,648 to 2,147,483,647, from −(2^31) to 2^31 – 1
     Unsigned: From 0 to 4,294,967,295 which equals 2^32 − 1

     Long - Max range
     Signed: From −9,223,372,036,854,775,808 to 9,223,372,036,854,775,807, from −(2^63) to 2^63 − 1
     Unsigned: From 0 to 18,446,744,073,709,551,615 which equals 2^64 – 1
     */
    @Test
    public void testUnsigned() {
        int a = Integer.parseUnsignedInt("4294967295") - 3;
        out.println(a);
        int b = 2;

        long a0 = Long.parseLong("4294967295") - 3;
        long b0 = 2;
        out.println("Comparison:");
        out.println(a + b);
        out.println("add: " + Integer.toUnsignedLong(a + b));
        assertEquals(a0 + b0, Integer.toUnsignedLong(a + b));
        out.println(a - b);
        out.println("subtract: " + Integer.toUnsignedLong(a - b));
        assertEquals(a0 - b0, Integer.toUnsignedLong(a - b));
        out.println(a * b);
        out.println("multiply: " + Integer.toUnsignedLong(a) * Integer.toUnsignedLong(b));
        assertEquals(a0 * b0, Integer.toUnsignedLong(a) * Integer.toUnsignedLong(b));
        out.println(a / b);
        out.println("divide: " + Integer.divideUnsigned(a, b));
        assertEquals(a0 / b0, Integer.divideUnsigned(a, b));
        out.println(a % b);
        out.println("remainder: " + Integer.remainderUnsigned(a, b));
        assertEquals(a0 % b0, Integer.remainderUnsigned(a, b));
        out.println(compare(a, b));
        out.println("compare: " + Integer.compareUnsigned(a, b));
        assertEquals(Long.compareUnsigned(a0, b0), Integer.compareUnsigned(a, b));
    }

    private int compare(int a, int b) {
        if (a == b) {
            return 0;
        }
        if (a > b) {
            return 1;
        }
        return -1;
    }
}
