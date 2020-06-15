/* *****************************************************************************
 *  Name: Jared Wu
 *  Date: 6/9/2020
 *  Description: Board
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

public class Board {
    private int[][] tiles;
    private final int dimension;
    private final int[] blankPosition = new int[2];
    private int manhattan;
    private boolean hasManhattan = false;


    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null || tiles.length == 0 || tiles.length == 1)
            throw new IllegalArgumentException();
        dimension = tiles.length;
        this.tiles = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                this.tiles[i][j] = tiles[i][j];
                if (tiles[i][j] == 0) {
                    blankPosition[0] = i;
                    blankPosition[1] = j;
                }
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension + "\n");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return dimension;
    }

    // number of tiles out of place
    public int hamming() {
        int h = dimension * dimension - 1;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (tiles[i][j] == ((j + 1) + (i * dimension))) h--;
            }
        }
        return h;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        if (!hasManhattan) {
            manhattan = calculateManhattan();
            hasManhattan = true;
        }
        return manhattan;
    }

    private int calculateManhattan() {
        int m = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (tiles[i][j] == 0) continue;
                int x = tiles[i][j] - 1;
                int y = x / dimension;
                x = x % dimension;
                m += Math.abs(i - y);
                m += Math.abs(j - x);
            }
        }
        return m;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (i == dimension - 1 && j == dimension - 1 && tiles[i][j] == 0) return true;
                if (tiles[i][j] != ((j + 1) + (i * dimension))) return false;
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass())
            return false;

        Board that = (Board) y;
        if (that.dimension() != this.dimension()) return false;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (that.tiles[i][j] != this.tiles[i][j]) return false;
            }
        }

        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> stack = new Stack<Board>();
        helper(stack);
        return stack;
    }

    private void helper(Stack<Board> stack) {
        int[][] direction = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
        for (int[] dir : direction) {
            int n = blankPosition[0] + dir[0];
            int m = blankPosition[1] + dir[1];
            if (n < dimension && n >= 0 &&
                    m < dimension && m >= 0) {
                int num = tiles[n][m];

                int x = num - 1;
                int y = x / dimension;
                x = x % dimension;
                Board tempBoard;

                if (Math.abs(y - n) + Math.abs(x - m) > Math.abs(y - blankPosition[0]) + Math
                        .abs(x - blankPosition[1])) {
                    tempBoard = new Board(tiles);
                    tempBoard.manhattan = manhattan - 1;
                }
                else {
                    tempBoard = new Board(tiles);
                    tempBoard.manhattan = manhattan + 1;
                }
                tempBoard.tiles[blankPosition[0]][blankPosition[1]] = num;
                tempBoard.tiles[n][m] = 0;
                tempBoard.blankPosition[0] = n;
                tempBoard.blankPosition[1] = m;
                stack.push(tempBoard);
            }
        }
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board twin = new Board(tiles);
        int i = 0;
        int j = 0;
        while (tiles[i][j] == 0 || tiles[i + 1][j] == 0) {
            j++;
        }
        twin.tiles[i + 1][j] = tiles[i][j];
        twin.tiles[i][j] = tiles[i + 1][j];
        return twin;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }

        Board test = new Board(tiles);
        System.out.println(test.dimension() + " ^ 2 Board: ");
        System.out.println(test.toString());
        System.out.println("Hamming distance: " + test.hamming() + " Manhanttan distance: " + test
                .manhattan());
        System.out.println("Is goal? " + test.isGoal());

        for (Board b : test.neighbors()) {
            System.out.println(b.toString());
        }

    }
}
