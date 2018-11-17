package site.lhearen.ajava.algorithm.math;

public class Normalize {
    static int m, n, p, k, j, i;
    static int x[][] = new int[m][n];
    static double h[] = new double[p];

    public static double[] eigenvector(int[][] x) {
        double sum = 0;
        for (p = 1; p <= k; p++) {
            {
                for (i = 1; i <= m; i++) {
                    for (j = 1; j <= n; j++) {
                        double px = Math.pow(x[i][j], 2);
                        sum = sum + px;
                    }
                }
                h[p] = Math.sqrt(sum);
            }
        }
        return h;
    }


    public static void main(String[] args) {
        int[][] x = new int[3][3];
        x[1][1] = 1;
        x[1][2] = 1;
        double h[] = eigenvector(x);
        for (p = 1; p <= k; p++) {
            System.out.println("The normalzed matrix is of class p is" + h[p]);
        }
        System.exit(0);

    }
}
