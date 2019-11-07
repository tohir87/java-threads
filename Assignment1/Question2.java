/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assignments;

import java.util.*;
import java.util.concurrent.*;

/**
 *
 * @author tohir
 */
public class Question2 {

    public static void main(String args[]) {
        int nProc = Runtime.getRuntime().availableProcessors();
        ExecutorService pool = Executors.newFixedThreadPool(nProc);
        int data[] = new int[1000];
        int[] index;
        init(data);

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

    }

    static void init(int dd[]) {
        for (int i = 0; i < dd.length; i++) {
            dd[i] = (int) (Math.random() * 10);
            System.out.print(dd[i] + ",");
        }
    }
}
