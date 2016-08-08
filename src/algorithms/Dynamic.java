package algorithms;

import structures.Point;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

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
    public String getName() {
        return "Dynamic";
    }

    @Override
    public void calculate() {
        long initTime = System.nanoTime();
        Point p1 = points.get(0);
        int rowSize = points.size() - 1;

        // First row

        double[] row = new double[rowSize];
        for(int i = 0; i < rowSize; i++){
            double distance = p1.distance(points.get(i + 1));
            row[i] = distance;
        }

        HashMap<String, double[]> table = new HashMap<>();
        table.put("0", row);
        int maxCols = (int) Math.pow(2, rowSize);
        Point point;
        ArrayList<double[]> otherRows;
        ArrayList<String> keys;
        ArrayList<Integer> indexes;
        double[] tmpRow;
        for(int i = 1; i < maxCols; i++){
            String key = Integer.toString(i, 2);
            String reverseKey = new StringBuilder(key).reverse().toString();
            int ones = key.replace("0", "").length();
            //Base case
            if(ones == 1){
                int index = reverseKey.indexOf("1");
                point = points.get(index + 1);
                double[] newRow = new double[rowSize];
                for(int counter = 0; counter < index; counter ++)
                    newRow[counter] = row[counter] + point.distance(points.get(counter + 1));
                for(int counter = index + 1 ; counter < rowSize; counter ++)
                    newRow[counter] = row[counter] + point.distance(points.get(counter + 1));
                newRow[index] = row[index];

                table.put(key, newRow);
            }
            else{
                keys = getKeys(reverseKey);
                indexes = getIndexes(reverseKey);
                otherRows = new ArrayList<>();

                for(int index = 0; index < keys.size(); index++){
                    String _key = keys.get(index);
                    tmpRow = table.get(_key).clone();
                    point = points.get(indexes.get(index) + 1);
                    for(int inner = 0; inner < tmpRow.length; inner ++){
                        if(indexes.contains(inner))
                            continue;
                        tmpRow[inner] = tmpRow[inner] + point.distance(points.get(inner + 1));
                    }
                    otherRows.add(tmpRow);
                }

                double[] mins = getMin(otherRows);
                table.put(key, mins);
            }
        }


        long endTime = System.nanoTime();
        this.totalTime = endTime - initTime;
    }

    private ArrayList<String> getKeys(String reverseKey){
        ArrayList<String> returnList = new ArrayList<>();
        String _key;
        for(int i = 0; i < reverseKey.length(); i++){
            if(reverseKey.charAt(i) == '1'){
                _key = "";
                for(int o = 0; o < i; o++)
                    _key += "0";
                _key = "1" + _key;
                returnList.add(_key);
            }
        }

        return returnList;
    }

    private ArrayList<Integer> getIndexes(String reverseKey){
        ArrayList<Integer> returnList = new ArrayList<>();
        for(int i = 0; i < reverseKey.length(); i++)
            if(reverseKey.charAt(i) == '1')
                returnList.add(i);
        return returnList;
    }

    private double[] getMin(ArrayList<double[]> arrays){
        double[] returnList = new double[arrays.get(0).length];
        double min;
        for(int i = 0; i < returnList.length; i++){
            min = Double.MAX_VALUE;
            for(double[] array : arrays)
                if(array[i] < min)
                    min = array[i];
            returnList[i] = min;
        }

        return returnList;
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
