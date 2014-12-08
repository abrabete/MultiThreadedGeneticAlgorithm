package ga;

/**
 * Created by Andrei on 12/3/2014.
 * Version v1.12
 */
class PopulationSizeException extends Throwable {

    public PopulationSizeException() {
        System.out.println("The population size must be at least 1!");
    }

}
