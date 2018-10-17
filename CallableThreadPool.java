
package __test;


/**
 *
 * Student Name:
 * Student number:
 *
 */
import java.util.*;
import java.util.concurrent.*;

public class CallableThreadPool {

    public static void main(String[] args) {
        //Question 1
        int data[] = new int[1000];
        init(data);

        int nProc = Runtime.getRuntime().availableProcessors();
        ExecutorService pool = Executors.newFixedThreadPool(nProc);
        int[] index = new int[nProc + 1];

        
        //Question 2

        ArrayList<Future<Integer>> future1 = new ArrayList<Future<Integer>>();

        //divide the work evenly between processors
        for (int i = 0; i <= nProc; i++) {
            index[i] = (i * data.length) / nProc;
        }

        for (int j = 0; j < index.length - 1; j++) {
            Future<Integer> f1 = pool.submit(new FindLargestFrequency(data, index[j], index[j + 1]));
            future1.add(f1);
        }

        int result[] = new int[index.length - 1];
        for (int j = 0; j < result.length; j++) {
            try {
                Future<Integer> f1 = future1.get(j);
                result[j] = f1.get();
            } catch (InterruptedException e) {
            } catch (ExecutionException e) {
            };
        }
        pool.shutdown();

        int biggest = result[0];
        for (int k = 0; k < result.length -1; k++) {
            if (result[k+1] > biggest)
                biggest = result[k];
        }

        // find the biggest frequency
        int count = 0;
        for (int k = 0; k < data.length; k++) {
            if (data[k] == biggest)
                count++;
        }

        // display the result
        System.out.printf("\nBiggest: %d \nFrequency: %d", biggest, count);


        //=======================================
    }

    static void init(int dd[]) {
        for (int i = 0; i < dd.length; i++) {
            dd[i] = (int) (Math.random() * 100);
        }
    }
}

//Code for Callable class here
class FindLargestFrequency implements Callable<Integer> {

    private int from, to;
    private int data[];

    FindLargestFrequency(int dd[], int st, int en) {
        data = dd;
        from = st;
        to = en;
    }

    public Integer call() {
        int large = data[from];
        for (int j = from; j < to; j++) {
            if (data[j+1] > large) {
                large = data[j+1];
            }
        }
        return large;
    }

}

//========================================================}
