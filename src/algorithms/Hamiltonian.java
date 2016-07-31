package algorithms;

/**
 * Class Hamiltonian
 *
 * @author Michel Llorens <mllorens@dcc.uchile.cl>
 * @version 1.0, 04-07-2016
 */
public interface Hamiltonian {
    void calculate();
    double getWeight();
    long getDuration();
}
