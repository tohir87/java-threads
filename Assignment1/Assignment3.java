
import java.util.*;
import java.util.concurrent.*;

/**
 * Student Name: Thohiru Omoloye Student Number: 2950574
 *
 * @author tohir
 */
public class Assignment3 {
    
    private static final int SIZE = 10000000;

    public static void main(String[] args) {
        
        
        int data[] = new int[SIZE];
        int data2[] = new int[SIZE];
        for (int j = 0; j < data.length; j++) {
            data[j] = (int) (Math.random() * 100000);
            data2[j] = data[j];
        }
        ForkJoinPool fjPool = new ForkJoinPool();

        // take note of start time
        long startTime = System.currentTimeMillis();
        fjPool.invoke(new Sort(data, 0, data.length));
        long endTime = System.currentTimeMillis();

        // calculate time spent running the sorting
        long runningTime = endTime - startTime;
        
        System.out.println("Sorted ?" + isSorted(data));
        System.out.println("Running Time: " + runningTime);

        // Using Merge sort 
        long startTime2 = System.currentTimeMillis();
        Sort.mergeSort(data, 0, data2.length);

        long endTime2 = System.currentTimeMillis();
        long runningTime2 = endTime2 - startTime2;
        System.out.println("Sorting without using ForkJoin ");
        System.out.println(runningTime2 + " millisecs (" + (runningTime2 / 1000.0) + ")");

        
        System.out.println("Sorted List: " + isSorted(data2));

    }

    static boolean isSorted(int[] arr) {
        boolean sorted = true;
        for (int i = 0; i < arr.length - 1; i++) {

            if (arr[i] > arr[i + 1]) {
                sorted = false;
            }
        }
        
        return sorted;
    }

}

class Sort extends RecursiveAction {

    /* RecursiveAction becasue we dont want to return a value */
    private int[] f;
    private int lb;
    private int ub;
    private static final int BLOCKSIZE = 500;

    public Sort(int a[], int l, int u) {
        f = a;
        lb = l;
        ub = u;
    }

    protected void compute() {
        // Check if bounds are within block size
        if (ub - lb <= BLOCKSIZE) {
            // Implement Insertion sort sequentially
            for (int i = lb; i < ub; i++) {
                int j = i;
                int k = f[i];
                while ((j > 0) && (f[j - 1] > k)) {
                    f[j] = f[j - 1];
                    j--;
                }
                f[j] = k;
            }
        } else {
            // MergeSort
            int m = lb + (ub - lb) / 2;
            Sort left = new Sort(f, lb, m);
            Sort right = new Sort(f, m + 1, ub);
            invokeAll(left, right);
            left.join();
            right.join();
            mergeSort(f, lb, ub);
        }
    }

    static void mergeSort(int f[], int lb, int ub) {
        //termination reached when a segment of size 1 reached - lb+1 = ub
        if (lb + 1 < ub) {
            int mid = (lb + ub) / 2;
            mergeSort(f, lb, mid);
            mergeSort(f, mid, ub);
            merge(f, lb, mid, ub);
        }
    }

    static void merge(int f[], int p, int q, int r) {
        //p<=q<=r
        int i = p;
        int j = q;
        //use temp array to store merged sub-sequence
        int temp[] = new int[r - p];
        int t = 0;
        while (i < q && j < r) {
            if (f[i] <= f[j]) {
                temp[t] = f[i];
                i++;
                t++;
            } else {
                temp[t] = f[j];
                j++;
                t++;
            }
        }
        //tag on remaining sequence
        while (i < q) {
            temp[t] = f[i];
            i++;
            t++;
        }
        while (j < r) {
            temp[t] = f[j];
            j++;
            t++;
        }
        //copy temp back to f
        i = p;
        t = 0;
        while (t < temp.length) {
            f[i] = temp[t];
            i++;
            t++;
        }
    }

}
