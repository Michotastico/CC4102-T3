package structures;

/**
 * Class ReferenceSet
 *
 * @author Michel Llorens <mllorens@dcc.uchile.cl>
 * @version 1.0, 31-07-2016
 */
public class ReferenceSet implements Comparable<ReferenceSet>{

    private int x, y;
    private double value;

    public ReferenceSet(int x, int y, double value){
        this.x = x;
        this.y = y;
        this.value = value;
    }

    @Override
    public int compareTo(ReferenceSet o) {
        return (int)(this.value - o.value);
    }

    public double getValue(double[][]table){
        return table[x][y];
    }
}
