
package join_fork;

import java.util.concurrent.RecursiveAction;

/**
 *
 * @author 2950574
 */
public class QuickSort extends RecursiveAction {
    int lb;
    int ub;
    int[] data;
    
    public QuickSort(int[] dt, int a, int b){
        data = dt;
        lb = a;
        ub = b;
    }
    
    protected void compute(){
        if (ub - lb > 1){
            int i,j,k;
            // let x = middle element in f
            int x = data[(lb+ub)/2];
            i = lb; j = lb;  k = ub;
            
            while(j != k){
                if(data[j] == x)
                    j = j + 1;
                else if (data[j] < x) { //swap f[j] with f[i]
                    int temp = data[j];
                    data[j] = data[i];
                    data[i] = temp;
                    j++;
                    i++;
                }
                else {  // f[j] > x then swap f[j] with f[k-1]
                    int temp = data[j];
                    data[j] = data[k-1];
                    data[k-1] = temp;
                    k = k -1;
                }
            }
            
            QuickSort left = new QuickSort(data, lb, i);
            QuickSort right = new QuickSort(data, j, ub);
            invokeAll(left, right);
            
            left.join();
            right.join();
        }
    }
}
