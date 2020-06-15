/* *****************************************************************************
 *  Name: Jared Wu
 *  Date: 6/1/2020
 *  Description: Permutation
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> RQ = new RandomizedQueue<String>();
        int k = Integer.parseInt(args[0]);
        int i = 0;
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (i < k) {
                RQ.enqueue(s);
            }
            else {
                if (StdRandom.uniform(k + 1) < k) {
                    RQ.dequeue();
                    RQ.enqueue(s);
                }
            }
            i++;
        }
        for (String s : RQ) {
            StdOut.println(s);
        }


    }
}
