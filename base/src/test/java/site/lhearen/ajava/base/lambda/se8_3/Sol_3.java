package site.lhearen.ajava.base.lambda.se8_3;

import java.util.function.BooleanSupplier;

import org.junit.Test;

public class Sol_3 {
    @Test(expected = AssertionError.class)
    public void testAssert() {
        assertThat(4 == 5);
        assertThat(() -> 4 == 5);
    }

    private void assertThat(boolean result) {
        if (!result) {
            throw new AssertionError("False Equation");
        }
    }

    private void assertThat(BooleanSupplier booleanSupplier) {
        if (!booleanSupplier.getAsBoolean()) {
            throw new AssertionError("False Equation");
        }
    }

}
