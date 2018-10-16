package join_fork;

import java.util.concurrent.RecursiveTask;

/**
 *
 * @author 2950574
 */
public class Sum extends RecursiveTask<Long> {
    static final int BaseBlockSize = 5000;
    int lb, ub;
    int[] data;
    
    Sum(int[] dt, int a, int b){
        data = dt;
        lb = a;
        ub = b;
    }
    
    protected Long compute(){
        if(ub - lb <+ BaseBlockSize){
            long sum = 0;
            for (int i=lb; i < ub; ++i) 
                sum += data[i];
            return sum;
        }else{
            int mid = lb + (ub - lb) / 2; // find the middle index
            Sum left = new Sum(data, lb, mid);
            Sum right= new Sum(data, mid, ub);
            
            // start the threads
            left.fork();
            right.fork();
            
            // wait for the threads to finish
            long rSum = right.join();
            long lSum = left.join();
            
            return lSum + rSum;
        }
    }
}
