import algorithms.ConvexHull;
import algorithms.Dynamic;
import algorithms.Hamiltonian;
import algorithms.MST;
import structures.Point;

import java.util.ArrayList;

/**
 * Class Main
 *
 * @author Michel Llorens <mllorens@dcc.uchile.cl>
 * @version 1.0, 06-08-2016
 */
public class Main {

    private static ArrayList<Point> points = new ArrayList<>();

    public static void main(String[] args){
        generatePoints();

        Hamiltonian[] algorithms = new Hamiltonian[3];
        algorithms[0] = new Dynamic(points);
        algorithms[1] = new ConvexHull(points);
        algorithms[2] = new MST(points);

        for(Hamiltonian h : algorithms){
            System.out.println(h.getName());
            h.calculate();
            System.out.println("Duration");
            System.out.println(h.getDuration());
            System.out.println("Weight");
            System.out.println(h.getWeight());
            System.out.println("");
        }
    }

    private static void generatePoints(){
        points.add(new Point(0.0, 0.0));
        points.add(new Point(1.0, 0.0));
        points.add(new Point(1.0, 1.0));
        points.add(new Point(0.0, 1.0));
    }
}
