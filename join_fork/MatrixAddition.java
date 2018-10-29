
package __forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 *
 * @author tohir
 */
public class MatrixAddition {
    
    private static final ForkJoinPool fjPool = new ForkJoinPool();

    public static void main(String[] args) {
        int f[][] = new int[10][5];
        int g[][] = new int[10][5];
        int add[][] = new int[10][5];
        
        // init both matrices   
        init(f,g); 
        
        int[][] result = fjPool.invoke(new RecursiveMatrixSum(add, f, g, 0, add.length));
        
        // print out the result
        for (int i = 0; i < f.length; i++) {
            for (int j = 0; j < f[0].length; j++) {
                System.out.println(result[i][j]);
            }
        }
    }
    
    static void init(int[][] f, int[][] g) {
        for (int i = 0; i < f.length; i++) {
            for (int j = 0; j < f[0].length; j++) {
                f[i][j] = (int) (Math.random() * 10);
                g[i][j] = (int) (Math.random() * 10);
            }
        }
    }

    
    
}

class RecursiveMatrixSum extends RecursiveTask<int[][]> {

    static final int BaseBlockSize = 5;

    private int lo, hi;
    private int[][] arr, f, g;

    public RecursiveMatrixSum(int[][] arr, int f[][], int g[][], int lo, int hi) {
        this.lo = lo;
        this.hi = hi;
        this.arr = arr;
        this.f = f;
        this.g = g;
    }

    @Override
    public int[][] compute() {
        if (hi - lo <= BaseBlockSize) {
            for (int i = lo; i < hi; i++) {
                for (int j = 0; j < f[0].length; j++) {
                    arr[i][j] = f[i][j] + g[i][j];
                }
            }
            
            return arr;

        } else {
            int mid = (lo + hi) / 2;
            RecursiveMatrixSum left = new RecursiveMatrixSum(arr, f, g, lo, mid);
            RecursiveMatrixSum right = new RecursiveMatrixSum(arr, f, g, mid, hi);
            left.fork(); 
            right.fork();
            int[][] rSum = right.join(); 
            int[][] lSum = left.join();
            
            // declare and array to hold both result
                int[][] both = new int[this.f.length][this.g.length];
                for (int i = 0; i < rSum.length - 1; i++) {
                    for (int j = 0; j < rSum[0].length - 1; j++) {
                        both[i][j] = rSum[i][j];
                    }
                }

                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        both[5 + i][j] = lSum[i][j];
                    }
                }
            
            return both;

        }
        
    }

}
