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
    private double weight;

    private final double MAX = Double.MAX_VALUE;

    public MST(ArrayList<Point> points){
        this.points = points;
        this.totalTime = 0;
    }

    @Override
    public void calculate() {
        double[][] table = this.makeTable();
        PriorityQueue<ReferenceSet> queue = new PriorityQueue<>();
        int index = 0;
        int tmp_index = -1;
        double [] row;
        ReferenceSet tmp;
        long initTime = System.nanoTime();

        // Prim to get the MST on adjacent matrix

        for(;;){
            row = table[index];
            for(int i = 0; i < row.length; i++)
                if(row[i] < this.MAX)
                    queue.add(new ReferenceSet(index, i, row[i]));

            while(!queue.isEmpty()){
                tmp_index = -1;
                tmp = queue.poll();
                if(tmp.getValue(table) < this.MAX){
                    tmp_index = tmp.getX();
                    for(int i = 0; i < index; i++)
                        table[i][tmp_index] = this.MAX;
                    for(int i = index + 1; i < table.length; i++)
                        table[i][tmp_index] = this.MAX;
                    index = tmp_index;

                    break;
                }
            }

            if(tmp_index != index)
                break;

        }

        //DFS in a list

        ArrayList<Integer> hamiltonian = new ArrayList<>();
        hamiltonian.add(0);

        for(index = 0; index < this.points.size(); index++){
            tmp_index = hamiltonian.get(index);
            row = table[tmp_index];
            for(int i = row.length - 1; i > 0; i--)
                if(row[i] < this.MAX)
                    hamiltonian.add(index + 1, i);
        }

        //Lenght
        double length = 0;
        int next, index_prev, index_next;
        for(int i = 0; i < hamiltonian.size(); i ++){
            next = (i + 1)%hamiltonian.size();
            index_prev = hamiltonian.get(i);
            index_next = hamiltonian.get(next);
            length += this.points.get(index_prev).distance(this.points.get(index_next));
        }
        this.weight = length;

        long endTime = System.nanoTime();
        this.totalTime = endTime - initTime;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public long getDuration() {
        return this.totalTime;
    }

    private double[][] makeTable(){
        int size = this.points.size();
        double[][] table = new double[size][size];

        //Fill the table with values
        Point point;
        double value;

        for(int i = 0; i < size; i++){
            point = this.points.get(i);

            for(int a = 0; a < i; a++){
                value = point.distance(this.points.get(a));
                table[i][a] = value;
            }

            table[i][i] = this.MAX;

            for(int b = i + 1; b < size; b++){
                value = point.distance(this.points.get(b));
                table[i][b] = value;
            }


        }

        return table;
    }
}
