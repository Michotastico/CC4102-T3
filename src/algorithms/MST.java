package algorithms;

import structures.Point;
import structures.ReferenceSet;

import java.util.ArrayList;
import java.util.HashMap;
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
        this.points = new ArrayList<>(points);
        this.totalTime = 0;
    }

    @Override
    public String getName() {
        return "MST";
    }

    @Override
    public void calculate() {
        double[][] table = this.makeTable();
        PriorityQueue<ReferenceSet> queue = new PriorityQueue<>();
        int index = 0;
        int tmp_index;
        double [] row;
        ReferenceSet tmp;
        long initTime = System.nanoTime();

        // Prim to get the MST on adjacent matrix
        for(int i = 0; i < table.length; i++)
            table[i][0] = this.MAX;

        for(;;){
            tmp_index = -1;
            row = table[index];
            for(int i = 0; i < row.length; i++)
                if(row[i] < this.MAX)
                    queue.add(new ReferenceSet(index, i, row[i]));

            while(!queue.isEmpty()){
                tmp_index = -1;
                tmp = queue.poll();
                if(tmp.getValue(table) < this.MAX){
                    tmp_index = tmp.getY();
                    for(int i = 0; i < tmp.getX(); i++)
                        table[i][tmp_index] = this.MAX;
                    for(int i = tmp.getX() + 1; i < table.length; i++)
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
        ArrayList<Integer> temporal = new ArrayList<>();
        hamiltonian.add(0);

        HashMap<Integer, Boolean> visited = new HashMap<>();
        index = 0;
        while(visited.size() < this.points.size()){
            if(visited.containsKey(hamiltonian.get(index))){
                index++;
                continue;
            }
            visited.put(hamiltonian.get(index), true);
            tmp_index = hamiltonian.get(index);
            row = table[tmp_index];
            for(int i = 0; i < row.length; i++)
                if(row[i] < this.MAX)
                    temporal.add(i);

            int middle = temporal.size()/2;
            for(int i = temporal.size() - 1 ; i >= middle ; i--)
                hamiltonian.add(index + 1, temporal.get(i));
            for(int i = middle - 1; i >= 0; i--)
                hamiltonian.add(index, temporal.get(i));
            temporal.clear();
            index = 0;

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
