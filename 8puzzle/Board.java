/* *****************************************************************************
 *  Name: Jared Wu
 *  Date: 6/9/2020
 *  Description: Board
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

public class Board {
    private int[] tiles;
    private final int dimension;
    private int blankPosition = 0;
    private int manhattan;
    private boolean hasManhattan = false;
    private boolean isCool = true;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null || tiles.length == 0 || tiles.length == 1)
            throw new IllegalArgumentException();
        dimension = tiles.length;
        this.tiles = new int[dimension * dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                this.tiles[i * dimension + j] = tiles[i][j];
                if (tiles[i][j] == 0) {
                    blankPosition = i * dimension + j;
                }
            }
        }
    }

    private Board(int[] tiles) {
        if (tiles == null || tiles.length == 0 || tiles.length == 1)
            throw new IllegalArgumentException();
        dimension = tiles.length;
        this.tiles = new int[dimension * dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                this.tiles[i * dimension + j] = tiles[i * dimension + j];
                if (tiles[i * dimension + j] == 0) {
                    blankPosition = i * dimension + j;
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
                s.append(String.format("%2d ", tiles[i * dimension + j]));
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
                if (tiles[i * dimension + j] == ((j + 1) + (i * dimension))) h--;
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
                if (tiles[i * dimension + j] == 0) continue;
                int x = tiles[i * dimension + j] - 1;
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
                if (i == dimension - 1 && j == dimension - 1 && tiles[i * dimension + j] == 0)
                    return true;
                if (tiles[i * dimension + j] != ((j + 1) + (i * dimension))) return false;
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
                if (that.tiles[i * dimension + j] != this.tiles[i * dimension + j]) return false;
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
            int y = blankPosition;
            int x = y / dimension;
            y = y % dimension;
            int n = x + dir[0];
            int m = y + dir[1];
            if (n < dimension && n >= 0 &&
                    m < dimension && m >= 0) {
                int num = tiles[n * dimension + m];

                int j = num - 1;
                int i = j / dimension;
                j = j % dimension;
                Board tempBoard;

                if (Math.abs(j - m) + Math.abs(i - n) > Math.abs(j - y) + Math
                        .abs(i - x)) {
                    tempBoard = new Board(tiles);
                    tempBoard.manhattan = manhattan - 1;
                }
                else {
                    tempBoard = new Board(tiles);
                    tempBoard.manhattan = manhattan + 1;
                }
                tempBoard.tiles[x * dimension + y] = num;
                tempBoard.tiles[n * dimension + m] = 0;
                tempBoard.blankPosition = n * dimension + m;
                stack.push(tempBoard);
            }
        }
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board twin = new Board(tiles);
        int i = 0;
        int j = 0;
        while (tiles[i * dimension + j] == 0 || tiles[(i + 1) * dimension + j] == 0) {
            j++;
        }
        twin.tiles[(i + 1) * dimension + j] = tiles[i * dimension + j];
        twin.tiles[i * dimension + j] = tiles[(i + 1) * dimension + j];
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
