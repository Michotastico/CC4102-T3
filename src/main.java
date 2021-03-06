import algorithms.ConvexHull;
import algorithms.Dynamic;
import algorithms.Hamiltonian;
import algorithms.MST;
import structures.Point;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class Main
 *
 * @author Michel Llorens <mllorens@dcc.uchile.cl>
 * @version 1.0, 06-08-2016
 */
public class Main {

    private static ArrayList<Point> points = new ArrayList<>();

    private static class CountrySet{
        private String name;
        private String file;
        private double optimum;
        public CountrySet(String name, String file, double optimum){
            this.name = name;
            this.file = file;
            this.optimum = optimum;
        }

        public String getName() {
            return name;
        }

        public String getFile() {
            return file;
        }

        public double getOptimum() {
            return optimum;
        }
    }

    private static ArrayList<CountrySet> countries = new ArrayList<>();

    private static void fillCountries(){
        countries.add(new CountrySet("Canada", "ca4663.tsp", 1290319));
        countries.add(new CountrySet("Djibouti", "dj38.tsp", 6656));
        //-countries.add(new CountrySet("Finland", "fi10639.tsp", 520527));
        //countries.add(new CountrySet("Greece", "gr9882.tsp", 300899));
        //-countries.add(new CountrySet("Italy", "it16862.tsp", 557315));
        //countries.add(new CountrySet("Japan", "ja9847.tsp", 491924));
        countries.add(new CountrySet("Oman", "mu1979.tsp", 86891));
        countries.add(new CountrySet("Qatar", "qa194.tsp", 9352));
        //-countries.add(new CountrySet("Sweden", "sw24978.tsp", 855597));
        countries.add(new CountrySet("Uruguay", "uy734.tsp", 79114));
        //-countries.add(new CountrySet("Vietnam", "vm22775.tsp", 569288));
        countries.add(new CountrySet("Western Sahara", "wi29.tsp", 27603));
        countries.add(new CountrySet("Zimbabwe", "zi929.tsp", 95345));
    }

    public static void main(String[] args) throws IOException {
        /*points.add(new Point(0.0, 0.0));
        points.add(new Point(1.0, 0.0));
        points.add(new Point(1.0, 1.0));
        points.add(new Point(0.0, 1.0));

        Hamiltonian algorithm = new Dynamic(points);
        algorithm.calculate();*/
        singleTest();
        countriesTest();
    }

    private static void singleTest() throws IOException{
        CountrySet country = new CountrySet("Egypt", "eg7146.tsp", 0);
        readPoints(country.getFile());
        ArrayList<Point> randomCities = new ArrayList<>();
        Random generator = new Random(42);
        for(int i = 0; i < 15; i++){
            randomCities.add(points.remove(generator.nextInt(points.size())));
        }

        int[] testSize = {5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};

        ArrayList<Point> testSet = new ArrayList<>();
        Hamiltonian[] algorithms = new Hamiltonian[3];
        print("Test with arbitrary number of cities in "+country.getName());
        for(Integer size: testSize){
            print("Test with number of cities : " + size);
            testSet.clear();
            for(int i = 0; i < size; i++)
                testSet.add(randomCities.get(i));

            algorithms[0] = new Dynamic(testSet);
            algorithms[1] = new ConvexHull(testSet);
            algorithms[2] = new MST(testSet);

            for(Hamiltonian h : algorithms){
                print(h.getName());
                h.calculate();
                print("Duration");
                print(h.getDuration());
                print("Weight");
                print(h.getWeight());
                print("");
            }
        }


    }

    private static void countriesTest() throws IOException {
        //Fill with countries and his optimum.
        fillCountries();

        //Run test
        Hamiltonian[] algorithms = new Hamiltonian[3];
        for(CountrySet country: countries){
            print(country.getName());
            print("Optimum : "+country.getOptimum());
            readPoints(country.getFile());

            //New algorithms
            algorithms[0] = new Dynamic(points);
            algorithms[1] = new ConvexHull(points);
            algorithms[2] = new MST(points);

            for(Hamiltonian h : algorithms){
                print(h.getName());
                h.calculate();
                print("Duration");
                print(h.getDuration());
                print("Weight");
                print(h.getWeight());
                print("");
            }
            print("");

        }
    }

    private static void readPoints(String name) throws IOException {
        //Clean first
        points.clear();

        //Fill with points
        String _file = "data/"+name;
        BufferedReader reader = new BufferedReader(new FileReader(_file));
        String next_line = "";
        for(int i = 0; i < 5; i++)
            next_line = reader.readLine();
        String s_size = next_line.replace("DIMENSION : ", "");
        int size = Integer.parseInt(s_size);
        String parts[];
        double x, y;
        // Skip next two lines
        next_line = reader.readLine();
        next_line = reader.readLine();

        for(int i = 0; i < size; i++){
            next_line = reader.readLine();
            parts = next_line.split(" ");
            x = Double.parseDouble(parts[1]);
            y = Double.parseDouble(parts[2]);
            points.add(new Point(x, y));
        }
    }

    private static void print(String s){
        System.out.println(s);
    }
    private static void print(double d){
        System.out.println(d);
    }
    private static void print(long l){
        System.out.println(l);
    }
}
