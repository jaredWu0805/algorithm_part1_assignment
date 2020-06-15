/* *****************************************************************************
 *  Name: Jared Wu
 *  Date: 6/4/2020
 *  Description: Brute force
 **************************************************************************** */

import java.util.ArrayList;

public class BruteCollinearPoints {
    private int numOfSegments = 0;
    private ArrayList<LineSegment> segs = new ArrayList<LineSegment>();

    /**
     * Corner cases. Throw an IllegalArgumentException if the argument to the
     * constructor is null, if any point in the array is null, or if the
     * argument to the constructor contains a repeated point.
     */
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException("Argument is null");
        int leng = points.length;

        for (int i = 0; i < leng; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException("Point is null");
            for (int j = i + 1; j < leng; j++) {
                if (points[j] == null)
                    throw new IllegalArgumentException("Point is null");
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException("Repeated point");
            }
        }

        Point zero = new Point(0, 0);

        for (int i = 0; i < leng; i++) {
            for (int j = i + 1; j < leng; j++) {
                for (int n = j + 1; n < leng; n++) {
                    double a = points[i].slopeTo(points[j]);
                    double b = points[i].slopeTo(points[n]);
                    if (a == b) {
                        for (int m = n + 1; m < leng; m++) {
                            double c = points[i].slopeTo(points[m]);

                            if (a == c) {
                                Point max = points[i];
                                Point min = points[i];
                                if (max.compareTo(points[j]) > 0) max = points[j];
                                if (min.compareTo(points[j]) < 0) min = points[j];
                                if (max.compareTo(points[n]) > 0) max = points[n];
                                if (min.compareTo(points[n]) < 0) min = points[n];
                                if (max.compareTo(points[m]) > 0) max = points[m];
                                if (min.compareTo(points[m]) < 0) min = points[m];
                                segs.add(new LineSegment(min, max));
                                numOfSegments++;
                            }
                        }
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

}
