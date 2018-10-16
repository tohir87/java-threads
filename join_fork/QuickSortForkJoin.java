
package join_fork;

import java.util.concurrent.*;

/**
 *
 * @author 2950574
 */
public class QuickSortForkJoin {
    public static void main(String args[]){
        int data[] = new int[10000000];
        for (int j=0; j < data.length; j++) data[j] = (int)(Math.random()*100000);
        ForkJoinPool fjPool = new ForkJoinPool();
        
        // take note of start time
        long startTime = System.currentTimeMillis();
        fjPool.invoke(new QuickSort(data, 0, data.length));
        long endTime = System.currentTimeMillis();
        
        // calculate time spent running the sorting
        long runningTime = endTime - startTime;
        
        // check if the data is sorted
        boolean sorted = true;
        for (int i=0; i < data.length - 1; i++)
            if(data[i] > data[i+1])
                sorted = false;
        System.out.println("Sorted ?" + sorted);
        System.out.println("Running Time: " + runningTime);
        
        
        
        
    }
}
