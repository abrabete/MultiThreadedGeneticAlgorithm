package ga;

/**
 * Created by Andrei on 12/3/2014.
 * Version v1.12
 */
class TerminationValueException extends Throwable {

    public TerminationValueException() {
        System.out.println("The number of function evaluations termination value must be at least the elite set size!");
    }
}
