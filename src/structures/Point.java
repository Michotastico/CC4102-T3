package structures;

/**
 * Class Point
 *
 * @author Michel Llorens <mllorens@dcc.uchile.cl>
 * @version 1.0, 04-07-2016
 */
public class Point implements Comparable<Point> {
    private double x, y;
    @Override
    public int compareTo(Point p) {
        double value;
        if (this.x == p.x) {
            value = this.y - p.y;
        } else {
            value = this.x - p.x;
        }
        if (value > 0)
            return 1;
        else if (value < 0)
            return -1;
        return 0;
    }

    public double distance(Point p){
        double value = Math.pow((x - p.getX()), 2) + Math.pow((y - p.getY()), 2);
        value = Math.sqrt(value);
        return value;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {

        this.x = x;
    }

    public double getY() {

        return y;
    }

    public void setY(double y) {

        this.y = y;
    }
}
