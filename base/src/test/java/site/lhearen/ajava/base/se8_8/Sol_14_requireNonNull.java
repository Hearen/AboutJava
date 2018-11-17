package site.lhearen.ajava.base.se8_8;

import java.time.LocalDateTime;
import java.util.Objects;

import org.junit.Test;

public class Sol_14_requireNonNull {
    @Test(expected = NullPointerException.class)
    public void testRequireNonNull() {
        Objects.requireNonNull(null, "Object should not be null [" + LocalDateTime.now() + "]");
        Objects.requireNonNull(null, () -> "Object should not be null [" + LocalDateTime.now() + "]");
    }
}
