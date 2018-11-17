package site.lhearen.ajava.base.stream;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import static java.lang.System.out;
import static java.math.BigDecimal.valueOf;
import static org.junit.Assert.assertEquals;

public class Sol_10 {
    @Test
    public void testAvgDouble() {
        assertEquals(5.0, getAvgBasic(Stream.of(2.0, 4.0, 6.0, 8.0)), 1E-5);
        List<Double> doubleList = new ArrayList<>(Arrays.asList(Math.pow(10, 308), Math.pow(10, 308), Math.pow(10, 308), Math.pow(10, 308)));
        // Double.MAX_VALUE = 1.7976931348623157e+308
        BigDecimal doubleSum = BigDecimal.ZERO;
        for (Double d : doubleList) {
            doubleSum = doubleSum.add(new BigDecimal(d.toString()));
        }
        out.println(doubleSum.divide(valueOf(doubleList.size())).doubleValue());
        out.println(getAvgUsingRealBigDecimal(doubleList.stream()));
        out.println(getAvgBasic(doubleList.stream()));
        out.println(getAvgUsingFakeBigDecimal(doubleList.stream()));
    }

    private double getAvgBasic(Stream<Double> doubleStream) {
        return doubleStream.mapToDouble(d -> d).average().orElse(0.0);
    }

    private double getAvgUsingFakeBigDecimal(Stream<Double> doubleStream) {
        return doubleStream.map(BigDecimal::valueOf)
                .collect(Collectors.averagingDouble(BigDecimal::doubleValue));
    }

    private double getAvgUsingRealBigDecimal(Stream<Double> doubleStream) {
        List<Double> doubles = doubleStream.collect(Collectors.toList());
        return doubles.stream().map(BigDecimal::valueOf).reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(valueOf(doubles.size()), BigDecimal.ROUND_DOWN).doubleValue();
    }
}