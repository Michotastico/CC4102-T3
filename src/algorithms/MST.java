package algorithms;

import structures.Point;
import structures.ReferenceSet;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Class MST
 *
 * @author Michel Llorens <mllorens@dcc.uchile.cl>
 * @version 1.0, 04-07-2016
 */
public class MST implements Hamiltonian {

    private ArrayList<Point> points;
    private long totalTime;

    public MST(ArrayList<Point> points){
        this.points = points;
        this.totalTime = 0;
    }

    @Override
    public void calculate() {
        double[][] table = this.makeTable();
        PriorityQueue<ReferenceSet> queue = new PriorityQueue<>();

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

    private double[][] makeTable(){
        int size = this.points.size();
        double[][] table = new double[size][size];

        double MAX = Double.MAX_VALUE;
        //Fill the table with values
        Point point;
        double value;

        for(int i = 0; i < size; i++){
            point = this.points.get(i);

            for(int a = 0; a < i; a++){
                value = point.distance(this.points.get(a));
                table[i][a] = value;
            }

            table[i][i] = MAX;

            for(int b = i + 1; b < size; b++){
                value = point.distance(this.points.get(b));
                table[i][b] = value;
            }


        }

        return table;
    }
}
