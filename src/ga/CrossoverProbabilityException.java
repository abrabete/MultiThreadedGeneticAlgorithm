package ga;

/**
 * Created by Andrei on 12/3/2014.
 * Version v1.12
 */
public class CrossoverProbabilityException extends Throwable {

    public CrossoverProbabilityException() {
        System.out.println("The crossover probability must be between 0 and 1!");
    }
}
