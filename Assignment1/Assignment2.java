package Assignments;

import java.util.concurrent.*;
import java.util.*;

/**
 *
 * Student Name: Thohiru Omoloye 
 * Student number: 2950574
 *
 */
public class Assignment2 {

    public static void main(String[] args) {
        int nProc = Runtime.getRuntime().availableProcessors();
        ExecutorService pool = Executors.newFixedThreadPool(nProc);
        int data[] = new int[1000];
        int[] index;
        init(data);
        
        
        //Question 1 
        

        ArrayList<Future<Integer>> future1 = new ArrayList<Future<Integer>>();


        // divide work evenly between processors
        index = new int[nProc + 1];
        for (int i = 0; i < nProc; i++) {
            index[i] = (i * data.length) / nProc;
        }

        for (int j = 0; j < index.length - 1; j++) {
            Future<Integer> f = pool.submit(new CountEven(data, index[j], index[j + 1]));
            future1.add(f);
        }

        // create an array to store results
        int result[] = new int[index.length - 1];
        for (int j = 0; j < result.length; j++) {
            try {
                Future<Integer> f = future1.get(j);
                result[j] = f.get();
            } catch (InterruptedException e) {
            } catch (ExecutionException e) {
            }
        }

        pool.shutdown();

        int freq = 0;
        for(int x : result) freq += x;
        
        // display result
        System.out.printf("\n\n Frequency of even values: %d", freq);

        //========================================
        //Question 2
        ArrayList<Future<Integer>> futureLargest = new ArrayList<Future<Integer>>();
        ArrayList<Future<Integer>> futureFrequency = new ArrayList<Future<Integer>>();

        // divide work evenly between processors
        index = new int[nProc + 1];
        for (int i = 0; i < nProc; i++) {
            index[i] = (i * data.length) / nProc;
        }

        int largestResult[] = new int[index.length - 1];
        for (int j = 0; j < index.length - 1; j++) {
            Future<Integer> f = pool.submit(new FindLargest(data, index[j], index[j + 1]));
            
            futureLargest.add(f);
            try {
                Future<Integer> m = futureLargest.get(j);
                largestResult[j] = m.get();
            } catch (InterruptedException e) {
            } catch (ExecutionException e) {}
            
        }

        
        // find the largest value from the result gotten from our thread pool
        int largest = 0;
        for (int x : largestResult) {
            if (x > largest) {
                largest = x;
            }
        }
        
        // finding the frequency of the largest value
        for (int j = 0; j < index.length - 1; j++) {
            Future<Integer> ff = pool.submit(new FindLargestFrequency(data, index[j], index[j + 1], largest));
            
            futureFrequency.add(ff);
            
        }
        
        // create an array to store results of largest frequencies
        int frequencyResult[] = new int[index.length - 1];
        for (int j = 0; j < frequencyResult.length; j++) {
            try {
                Future<Integer> m = futureFrequency.get(j);
                frequencyResult[j] = m.get();
            } catch (InterruptedException e) {
            } catch (ExecutionException e) {
            }
        }
        
        // shutdown our thread pool
        pool.shutdown();
        
        int largestFreq = 0;
        for (int x : frequencyResult) largestFreq += x;

        //Output the sum
        System.out.printf("\n\nLargest value = %d \n Frequency = %d", largest, largestFreq);


        //=======================================
    }

    static void init(int dd[]) {
        for (int i = 0; i < dd.length; i++) {
            dd[i] = (int) (Math.random() * 10);
        }
    }
}



//Code for threads for Question 1=========================

class FindLargest implements Callable<Integer> {

    private int from, to;
    private int data[];

    public FindLargest(int dd[], int st, int en) {
        data = dd;
        from = st;
        to = en;
    }

    public Integer call() {
        int largest = data[from];
        
        // find the largest
        for (int j = from; j < to; j++) {
            if (data[j + 1] > largest) {
                largest = data[j + 1];
            }
        }
        
        // find its frequency
        return largest;
    }
}

/**
 * This class would find the frequency of the largest value
 * @author tohir
 */
class FindLargestFrequency implements Callable<Integer>{
    private int from, to, value;
    private int data[];
    
    public FindLargestFrequency(int dd[], int st, int en, int v){
        data = dd;
        from = st;
        to = en;
        value = v;
    }
    
    /**
     * Call method
     * @return frequency of value
     */
    public Integer call(){
        int count = 0;
        for (int j = from; j < to; j++) {
            if (data[j] == value)
                count++;
        }
        return count;
    }
}


//Code for threads for Question 2=========================
class CountEven implements Callable<Integer> {

    private int from, to;
    private int data[];

    CountEven(int dd[], int st, int en) {
        data = dd;
        from = st;
        to = en;
    }

    public Integer call() {
        int freq = 0;
        for (int j = from; j < to; j++) {
            if (data[j] % 2 == 0) {
                freq++;
            }
        }
        return freq;
    }

}
