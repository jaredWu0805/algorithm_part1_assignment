/* *****************************************************************************
 *  Name: Jared Wu
 *  Date: 6/5/2020
 *  Description: Fast algorithm
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private int numOfSegments = 0;
    private ArrayList<LineSegment> segs = new ArrayList<LineSegment>();

    /**
     * Corner cases. Throw an IllegalArgumentException if the argument to the
     * constructor is null, if any point in the array is null, or if the
     * argument to the constructor contains a repeated point.
     */
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException("Argument is null");
        int leng = points.length;
        Point[] copy = new Point[leng];
        // duplicate points array to copy array

        for (int i = 0; i < leng; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException("Point is null");
            copy[i] = points[i];
            for (int j = i + 1; j < leng; j++) {
                if (points[j] == null)
                    throw new IllegalArgumentException("Point is null");
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException("Repeated point");
            }
        }

        if (leng > 3) {
            for (int i = 0; i < leng; i++) {
                Arrays.sort(copy, points[i].slopeOrder());
                Point p = copy[0];

                int collinearCount = 0;
                double currentSlope = p.slopeTo(copy[1]);
                Point max = p;
                Point min = p;

                for (int j = 1; j < leng; j++) {
                    double prevSlope = currentSlope;
                    currentSlope = p.slopeTo(copy[j]);

                    if (currentSlope == prevSlope) {
                        collinearCount++;
                        if (copy[j].compareTo(max) > 0) max = copy[j];
                        if (copy[j].compareTo(min) < 0) min = copy[j];
                        if (j == leng - 1 && collinearCount >= 3 && p == min) {
                            segs.add(new LineSegment(min, max));
                            numOfSegments++;
                        }
                    }
                    else {
                        // Check if there are more that 3 adjacent points
                        if (collinearCount >= 3 && p == min) {
                            segs.add(new LineSegment(min, max));
                            numOfSegments++;
                        }
                        collinearCount = 1;
                        max = p;
                        min = p;
                        if (copy[j].compareTo(p) > 0) max = copy[j];
                        else min = copy[j];
                    }

                }

            }
        }

    }

    public int numberOfSegments() {
        return numOfSegments;
    }

    public LineSegment[] segments() {
        return segs.toArray(new LineSegment[segs.size()]);
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
