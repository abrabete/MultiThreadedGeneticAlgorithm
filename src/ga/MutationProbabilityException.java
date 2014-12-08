package ga;

/**
 * Created by Andrei on 12/3/2014.
 * Version v1.12
 */
class MutationProbabilityException extends Throwable {

    public MutationProbabilityException() {
        System.out.println("The mutation probability must be between 0 and 1!");
    }
}
