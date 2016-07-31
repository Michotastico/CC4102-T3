package algorithms;

import structures.Point;

import java.util.ArrayList;

/**
 * Class Dynamic
 *
 * @author Michel Llorens <mllorens@dcc.uchile.cl>
 * @version 1.0, 04-07-2016
 */
public class Dynamic implements Hamiltonian{

    private ArrayList<Point> points;
    private long totalTime;

    public Dynamic(ArrayList<Point> points){
        this.points = points;
        this.totalTime = 0;
    }

    @Override
    public void calculate() {
        long initTime = System.nanoTime();

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
}
