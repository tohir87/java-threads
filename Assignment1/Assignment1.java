package Assignment1;

// imports
import java.util.Scanner;
import java.util.Random;

/**
 * Student Name: Thohiru Omoloye 
 * Student Number: 2950574
 *
 * @author tohir
 */
public class Assignment1 {

    static final int N = 10000000;
    int data[] = new int[N];

    public static void main(String args[]) {

        //==================================================
        //Test code for Question 1
        // instantiate scanner class
        Scanner input = new Scanner(System.in);
        int num;

        // Prompt the user to enter the number of times 
        // wanna toss a die
        System.out.printf("\nHow many times you wanna toss a die: ");
        num = input.nextInt();

        Result res = new Result();

        TossDie t1 = new TossDie(num, 0, num, res);
        TossDie t2 = new TossDie(num, num, num * 2, res);
        TossDie t3 = new TossDie(num, num * 2, num * 3, res);
        TossDie t4 = new TossDie(num, num * 3, num * 4, res);

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try {
            t4.join();
        } catch (InterruptedException ex) {
            System.out.println("execution has been interrupted");
        }

        System.out.println("Frequency of each throw:");
        for (int i = 1; i <= 6; i++) {
            int freq = 0;
            for (int numb : res.get()) {
                if (numb == i) {
                    freq++;
                }
            }
            System.out.printf("\n Number %d appears %d time(s)", i, freq);
        }
        
        
        
        //==================================================
        //Test code for Question 2
        int data[] = new int[N];
        //assume occurrence of zero equally likely for all numbers generated
        for (int j = 0; j < N; j++) {
            data[j] = (int) (Math.random() * N);
        }
        
        // create an instance of the /* Found */ class
        Found fnd = new Found();

        FindLeftmostZero f1 = new FindLeftmostZero(data, 0, N / 4, fnd);
        FindLeftmostZero f2 = new FindLeftmostZero(data, N / 4, N / 2, fnd);
        FindLeftmostZero f3 = new FindLeftmostZero(data, N / 2, N / 4 + N / 2, fnd);
        FindLeftmostZero f4 = new FindLeftmostZero(data, (N / 4 + N / 2), N, fnd);

        f1.start();
        f2.start();
        f3.start();
        f4.start();

        try {
            f1.join();
            f2.join();
            f3.join();
            f4.join();
        } catch (InterruptedException e) {
        }

        // output the result
        System.out.println(f1.getZeroIndex()+ ' ' + f2.getZeroIndex() + ' ' + f3.getZeroIndex() + ' ' + f4.getZeroIndex());
    }

}

class TossDie extends Thread {

    private int n;      // number of times a dice is to be tossed
    private int upper;  // array upper bound
    private int lower;  // variable representing array lower bound
    private Result res;

    static final int MAX_SIZE = 6;

    public TossDie(int num, int lbound, int ubound, Result r) {
        this.n = num;
        this.upper = ubound;    // upper bound 
        this.lower = lbound;    // lower bound
        this.res = r;
    }

    public void run() {

        Random rand = new Random();
        int temp;

        for (int j = this.lower; j < this.upper; j++) {
            temp = rand.nextInt(MAX_SIZE) + 1;
            this.res.set(j, temp);
        }

    }
}

class Result {

    private int result[] = new int[25];

    void set(int idx, int value) {
        result[idx] = value;
    }

    int[] get() {
        return result;
    }
}

// Thread class for Question 2
// =======================================================
class FindLeftmostZero extends Thread {

    // Class variable declarations
    private int f[];
    private int lb, ub;
    private int zeroIndex;
    private Found fd;

    public FindLeftmostZero(int[] f1, int lbound, int ubound, Found fnd) {
        this.f = f1;
        this.ub = ubound;    // upper bound 
        this.lb = lbound;    // lower bound
        this.fd = fnd;
    }

    public void run() {
        int index = 0;
        while (index < this.f.length && this.f[index] != 0 && !this.fd.found()) {
            index++;
        }

        if (index == this.f.length) {
            System.out.println("No zero");
        } else {
            this.fd.set();
            this.zeroIndex = index;
        }
    }

    public int getZeroIndex() {
        return this.zeroIndex;
    }
}

class Found {

    private boolean found;

    /**
     * Set method
     */
    public void set() {
        found = true;
    }

    /**
     * Found method
     * @return 
     */
    public boolean found() {
        return found;
    }

    /**
     * ToString method
     * @return 
     */
    public String toString() {
        return found + "";
    }
}
