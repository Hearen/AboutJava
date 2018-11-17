package site.lhearen.ajava.base.future;

import java.util.Arrays;
import java.util.stream.LongStream;

import org.junit.Test;

import static java.lang.System.out;

public class Sol_9 {
    @Test
    public void testArrays() {
        FiboMatrix[] arr = LongStream.range(0, 100).mapToObj(i -> new FiboMatrix()).toArray(FiboMatrix[]::new);
        // a side-effect-free, associative function;
        Arrays.parallelPrefix(arr, (pre, cur) -> pre.multiply(cur));
        Arrays.stream(arr).forEach(out::println);
    }

    class FiboMatrix {
        long[][] matrix = new long[][]{{1, 1}, {1, 0}};

        public long[][] getMatrix() {
            return matrix;
        }

        public void setMatrix(long[][] newMatrix) {
            this.matrix = newMatrix;
        }

        public FiboMatrix multiply(FiboMatrix another) {
            FiboMatrix newFiboMatrix = new FiboMatrix();
            long[][] newMatix = {{0, 0}, {0, 0}};
            for (int r = 0, rLen = matrix.length; r < rLen; ++r) {
                for (int c = 0, cLen = matrix[r].length; c < cLen; ++c) {
                    for (int i = 0; i < cLen; ++i) {
                        newMatix[r][c] += matrix[r][i] * another.getMatrix()[i][c];
                    }
                }
            }
            newFiboMatrix.setMatrix(newMatix);
            return newFiboMatrix;
        }

        @Override
        public String toString() {
            return String.format(Arrays.deepToString(matrix));
        }
    }
}
