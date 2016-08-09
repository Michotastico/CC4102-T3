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
    private double weight;

    public Dynamic(ArrayList<Point> points){
        this.points = points;
        this.totalTime = 0;
        this.weight = 0;
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

        HashMap<String, double[]> table = new HashMap<>();
        table.put("0", row);
        int maxCols = (int) Math.pow(2, rowSize);
        Point point;
        ArrayList<double[]> otherRows;
        ArrayList<String> keys;
        ArrayList<Integer> indexes;
        double[] tmpRow;
        String key = "0";

        for(int i = 1; i < maxCols; i++){
            key = Integer.toString(i, 2);
            String reverseKey = new StringBuilder(key).reverse().toString();
            int ones = key.replace("0", "").length();
            //Base case
            if(ones == 1){
                int index = reverseKey.indexOf("1");
                point = points.get(index + 1);
                double[] newRow = new double[rowSize];
                for(int counter = 0; counter < index; counter ++)
                    newRow[counter] = 0;
                for(int counter = index + 1 ; counter < rowSize; counter ++)
                    newRow[counter] = 0;
                newRow[index] = p1.distance(point);

                table.put(key, newRow);
            }
            else{
                indexes = getIndexes(reverseKey);
                otherRows = new ArrayList<>();
                tmpRow = new double[rowSize];
                String tmpIndex = "";
                for(int counter = 0; counter < reverseKey.length(); counter++){
                    if(indexes.contains(counter)){
                        String tmp = tmpIndex + "0" + reverseKey.substring(counter+1, reverseKey.length());
                        tmp = removeHead(new StringBuilder(tmp).reverse().toString());

                        double [] anotherTmp = table.get(tmp);
                        double [] otherAnotherTmp = new double[rowSize];
                        otherAnotherTmp[counter] = Double.MAX_VALUE;
                        double val;
                        for(int a = 0; a < counter; a++){
                            val = anotherTmp[a] + points.get(counter + 1).distance(points.get(a + 1));
                            otherAnotherTmp[a] = val;
                        }
                        for(int b = counter + 1; b < rowSize; b++){
                            val = anotherTmp[b] + points.get(counter + 1).distance(points.get(b + 1));
                            otherAnotherTmp[b] = val;
                        }

                        double min = Double.MAX_VALUE;
                        for(Double d: otherAnotherTmp)
                            if(d < min)
                                min = d;
                        tmpRow[counter] = min;
                        tmpIndex += "1";
                    }
                    else{
                        tmpIndex += "0";
                        tmpRow[counter] = 0;
                    }
                }
                table.put(key, tmpRow);
            }
        }
        double [] finalRow = table.get(key);
        for(int i = 0; i < finalRow.length; i++){
            finalRow[i] = finalRow[i] + p1.distance(points.get(i + 1));
        }
        double min = Double.MAX_VALUE;
        for(Double d: finalRow)
            if(d < min)
                min = d;
        this.weight = min;


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
    private String removeHead(String s){
        String returnString = "";
        boolean head = true;
        for(int i = 0; i < s.length(); i++){
            if(head && s.charAt(i) == '1')
                head = false;
            if(!head)
                returnString += s.charAt(i);
        }
        return returnString;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public long getDuration() {
        return this.totalTime;
    }
}
