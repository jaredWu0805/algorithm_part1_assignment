/* *****************************************************************************
 *  Name: Jared Wu
 *  Date: 6/11/2020
 *  Description: A* algorithm
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {

    // Inner class Node
    private class Node {

        private final int moves;
        private final int priority;
        private final Board board;
        private final Node prev;

        Node(int moves, Node prev, Board board) {
            this.moves = moves + 1;
            this.prev = prev;
            this.board = board;
            // can be optimized
            // int manhattan = board.manhattan();
            priority = this.moves + board.manhattan();
        }
    }

    private Node ans;
    private int moves = -1;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        Comparator<Node> BY_PRIORITY = new Comparator<Node>() {
            public int compare(Node o1, Node o2) {
                if (o1.priority == o2.priority) {
                    return o1.moves - o2.moves;
                }
                return o1.priority - o2.priority;
            }
        };
        MinPQ<Node> minPQ = new MinPQ<Node>(BY_PRIORITY);
        minPQ.insert(new Node(moves, null, initial));

        MinPQ<Node> minPQTwin = new MinPQ<Node>(BY_PRIORITY);
        minPQTwin.insert(new Node(moves, null, initial.twin()));

        while (!minPQ.isEmpty() && !minPQTwin.isEmpty()) {

            Node temp = minPQ.delMin();
            Node tempTwin = minPQTwin.delMin();

            if (temp.board.isGoal()) {
                moves = temp.moves;
                ans = temp;
                break;
            }
            if (tempTwin.board.isGoal()) {
                moves = -1;
                ans = null;
                break;
            }

            for (Board b : temp.board.neighbors()) {
                if (temp.prev == null || !b.equals(temp.prev.board)) {
                    minPQ.insert(new Node(temp.moves, temp, b));
                }
            }
            for (Board b : tempTwin.board.neighbors()) {
                if (tempTwin.prev == null || !b.equals(tempTwin.prev.board)) {
                    minPQTwin.insert(new Node(tempTwin.moves, tempTwin, b));
                }
            }

        }
    }

    // // Print function
    // private void printNode(Node node, int i) {
    //     if (i == 1) System.out.println("-----------Print Node------------");
    //     if (i == -1) System.out.println("-----------Print Twin Node------------");
    //     System.out.println(node.board.toString());
    //     System.out.println(
    //             "Priority: " + node.priority + "\nMoves: " + node.moves + "\nManhanttan: "
    //                     + node.manhattan);
    // }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        if (ans == null) return false;
        return true;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        Node temp = ans;
        Stack<Board> solution = new Stack<Board>();
        while (temp != null) {
            solution.push(temp.board);
            temp = temp.prev;
        }
        return solution;
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
