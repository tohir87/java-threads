package join_fork;

import java.util.concurrent.ForkJoinPool;

/**
 *
 * @author 2950574
 */
public class ForkJoinTest {
    public static void main(String args[]){
        int f[] = new int[1000000];
        for (int j=0; j < f.length; j++)
            f[j] = (int)(Math.random()*1000);
        ForkJoinPool fjPool = new ForkJoinPool();
        long sum = fjPool.invoke(new Sum(f,0,f.length));
        System.out.print("Sum = " + sum);
    }
    
}
