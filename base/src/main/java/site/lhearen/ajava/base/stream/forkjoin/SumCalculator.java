package site.lhearen.ajava.base.stream.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class SumCalculator extends RecursiveTask<Long> {
    public static final ForkJoinPool FORK_JOIN_POOL = new ForkJoinPool();
    public static final long THRESHOLD = 10_000;

    private final long[] numbers;
    private final int start;
    private final int end;

    public SumCalculator(long[] numbers) {
        this(numbers, 0, numbers.length);
    }

    private SumCalculator(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        int length = end - start;
        if (length <= THRESHOLD) {
            return computeSequentially();
        }
        SumCalculator leftTask = new SumCalculator(numbers, start, start + length/2);
        leftTask.fork();
        SumCalculator rightTask = new SumCalculator(numbers, start + length/2, end);
        Long rightResult = rightTask.compute();
        Long leftResult = leftTask.join();
        return leftResult + rightResult;
    }

    private long computeSequentially() {
        long sum = 0;
        for (int i = start; i < end; i++) {
            sum += numbers[i];
        }
        return sum;
    }

    public static void runTest() {
        System.out.println("Iterative Sum done in: " + measurePerf(SumCalculator::iterativeSum, 10_000_000L) + " msecs");
        System.out.println("Sequential Sum done in: " + measurePerf(SumCalculator::sequentialSum, 10_000_000L) + " msecs");
        System.out.println("Parallel forkJoinSum done in: " + measurePerf(SumCalculator::parallelSum, 10_000_000L) + " msecs" );
        System.out.println("Range forkJoinSum done in: " + measurePerf(SumCalculator::rangedSum, 10_000_000L) + " msecs");
        System.out.println("Parallel range forkJoinSum done in: " + measurePerf(SumCalculator::parallelRangedSum, 10_000_000L) + " msecs" );
        System.out.println("ForkJoin sum done in: " + measurePerf(SumCalculator::forkJoinSum, 10_000_000L) + " msecs" );
        System.out.println("SideEffect sum done in: " + measurePerf(SumCalculator::sideEffectSum, 10_000_000L) + " msecs" );
        System.out.println("SideEffect prallel sum done in: " + measurePerf(SumCalculator::sideEffectParallelSum, 10_000_000L) + " msecs" );
    }

    private static long forkJoinSum(long n) {
        long[] numbers = LongStream.rangeClosed(1, n).toArray();
        ForkJoinTask<Long> task = new SumCalculator(numbers);
        return FORK_JOIN_POOL.invoke(task);
    }

    private static <T, R> long measurePerf(Function<T, R> f, T input) {
        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            R result = f.apply(input);
            long duration = (System.nanoTime() - start) / 1_000_000;
            System.out.println("Result: " + result);
            if (duration < fastest) fastest = duration;
        }
        return fastest;
    }

    private static long iterativeSum(long n) {
        long result = 0;
        for (long i = 0; i <= n; i++) {
            result += i;
        }
        return result;
    }

    private static long sequentialSum(long n) {
        return Stream.iterate(1L, i -> i + 1).limit(n).reduce(Long::sum).get();
    }

    private static long parallelSum(long n) {
        return Stream.iterate(1L, i -> i + 1).limit(n).parallel().reduce(Long::sum).get();
    }

    private static long rangedSum(long n) {
        return LongStream.rangeClosed(1, n).reduce(Long::sum).getAsLong();
    }

    private static long parallelRangedSum(long n) {
        return LongStream.rangeClosed(1, n).parallel().reduce(Long::sum).getAsLong();
    }

    private static long sideEffectSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n).forEach(accumulator::add);
        return accumulator.total;
    }

    private static long sideEffectParallelSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n).parallel().forEach(accumulator::add);
        return accumulator.total;
    }

    private static class Accumulator {
        private long total = 0;

        public void add(long value) {
            total += value;
        }
    }
}

