/* *****************************************************************************
 *  Name:              Jared Wu
 *  Coursera User ID:  Chi-Kai Wu
 *  Last modified:     5/26/2020
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // creates n-by-n grid, with all sites initially blocked
    private boolean[][] grid;
    private final WeightedQuickUnionUF wQUUF;
    private final WeightedQuickUnionUF wQUUFSec;
    private final int bound;
    private int opened;
    private final int[][] direction = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };

    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("n must be greater than 0");

        grid = new boolean[n][n];
        wQUUF = new WeightedQuickUnionUF(n * n + 2);
        wQUUFSec = new WeightedQuickUnionUF(n * n + 1);
        bound = n;
        opened = 0;

        // Initialize all sites to be blocked and connect bottom column to virtual bottom node
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = false;
                if (i == 0) {
                    wQUUF.union(0, j + 1);
                    wQUUFSec.union(0, j + 1);
                }

            }
        }

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        // System.out.println(bound);
        checkBoundary(row, col);

        if (!grid[row - 1][col - 1]) {
            grid[row - 1][col - 1] = true;
            opened++;

            if (row == bound) wQUUF.union(bound * bound + 1, (row - 1) * bound + col);
            // check if adjacent block is opened and union
            for (int[] dir : direction) {
                int x = row + dir[0];
                int y = col + dir[1];
                if ((x > 0 && x <= bound && y > 0 && y <= bound) && isOpen(x, y)) {
                    wQUUF.union((row - 1) * bound + col, (x - 1) * bound + y);
                    wQUUFSec.union((row - 1) * bound + col, (x - 1) * bound + y);
                }

            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkBoundary(row, col);
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        checkBoundary(row, col);
        if (isOpen(row, col)) {
            if (wQUUFSec.find((row - 1) * bound + col) == wQUUFSec.find(0)) return true;
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return opened;
    }

    // does the system percolate?
    public boolean percolates() {
        if (wQUUF.find(bound * bound + 1) == wQUUF.find(0)) return true;
        return false;
    }

    private void checkBoundary(int row, int col) {
        if (row <= 0 || row > bound || col <= 0 || col > bound) {
            throw new IllegalArgumentException("row or col is outside the boundary");

        }
    }
}
