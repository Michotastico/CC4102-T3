package algorithms;

import structures.Point;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class ConvexHull
 *
 * @author Michel Llorens <mllorens@dcc.uchile.cl>
 * @version 1.0, 04-07-2016
 */
public class ConvexHull implements Hamiltonian {

    private ArrayList<Point> points;
    private long totalTime;
    private double weight;

    public ConvexHull(ArrayList<Point> points){
        this.points = points;
        this.totalTime = 0;
    }

    @Override
    public void calculate() {
        long initTime = System.nanoTime();
        Point[] points = new Point[this.points.size()];
        points = this.points.toArray(points);
        Point[] hull = this.calculateHull(points);

        for(Point p: hull){
            this.points.remove(p);
        }
        ArrayList<Point> hamiltonian = new ArrayList<>(Arrays.asList(hull));

        Point pp, ppp;
        double min_value = Double.MAX_VALUE;
        double actual_value;
        int index = -1;

        for(Point p : this.points){
            int size = hamiltonian.size();
            for(int i = 0; i < size; i++){
                pp = hamiltonian.get(i);
                ppp = hamiltonian.get((i+1)%size);
                actual_value = p.distance(pp) + p.distance(ppp);
                actual_value = actual_value / pp.distance(ppp);
                if(actual_value < min_value){
                    index = i;
                    min_value = actual_value;
                }
            }
            hamiltonian.add((index + 1) % size, p);
        }

        long endTime = System.nanoTime();
        this.totalTime = endTime - initTime;
    }

    @Override
    public double getWeight() {
        return 0;
    }

    @Override
    public long getDuration() {
        return this.totalTime;
    }

    /*
    Andrew's monotone chain convex hull algorithm
    Implementation at https://en.wikibooks.org/wiki/Algorithm_Implementation/Geometry/Convex_hull/Monotone_chain
     */
    private double cross(Point O, Point A, Point B) {
        return (A.getX() - O.getX()) * (B.getY() - O.getY()) - (A.getY() - O.getY()) * (B.getX() - O.getX());
    }
    /*
    Andrew's monotone chain convex hull algorithm
    Implementation at https://en.wikibooks.org/wiki/Algorithm_Implementation/Geometry/Convex_hull/Monotone_chain
     */
    private Point[] calculateHull(Point[] P) {

        if (P.length > 1) {
            int n = P.length, k = 0;
            Point[] H = new Point[2 * n];

            Arrays.sort(P);

            // Build lower hull
            for (int i = 0; i < n; ++i) {
                while (k >= 2 && cross(H[k - 2], H[k - 1], P[i]) <= 0)
                    k--;
                H[k++] = P[i];
            }

            // Build upper hull
            for (int i = n - 2, t = k + 1; i >= 0; i--) {
                while (k >= t && cross(H[k - 2], H[k - 1], P[i]) <= 0)
                    k--;
                H[k++] = P[i];
            }
            if (k > 1) {
                H = Arrays.copyOfRange(H, 0, k - 1);
            }
            return H;
        } else if (P.length <= 1) {
            return P;
        } else{
            return null;
        }
    }
}
