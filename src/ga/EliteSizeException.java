package ga;

/**
 * Created by Andrei on 12/3/2014.
 * Version v1.12
 */
public class EliteSizeException extends Throwable {

    public EliteSizeException() {
        System.out.println("The elite set size must be at least 1!");
    }
}
