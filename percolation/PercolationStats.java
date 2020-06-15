/* *****************************************************************************
 *  Name:              Jared Wu
 *  Coursera User ID:  Chi-Kai Wu
 *  Last modified:     5/26/2020
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
    // perform independent trials on an n-by-n grid
    private static final double CONFIDENCE_95 = 1.96;
    private final int trial;
    private double mean;
    private double stddev;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException(("args must be greater than 0"));

        double[] thresholds = new double[trials];
        trial = trials;
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                perc.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
            }
            thresholds[i] = (double) perc.numberOfOpenSites() / (n * n);
        }
        mean = StdStats.mean(thresholds);
        stddev = StdStats.stddev(thresholds);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean - CONFIDENCE_95 * stddev() / Math.sqrt(trial);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean + CONFIDENCE_95 * stddev() / Math.sqrt(trial);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats st = new PercolationStats(n, trials);
        StdOut.println("mean                    = " + st.mean());
        StdOut.println("stddev                  = " + st.stddev());
        StdOut.println("95% confidence interval = [" + st.confidenceLo() + ", " + st
                .confidenceHi() + "]");
    }
}
