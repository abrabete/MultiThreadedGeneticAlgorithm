package ga;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Andrei on 12/1/2014.
 * Version v1.12
 */
public class SimpleProblem implements Problem {

    /** {@InheritDoc}
     */
    @Override
    public Number evaluate(Design d) {
        Boolean[] designVector = d.getDesignParameters();
        Double value = 0.0;
        for (Boolean aDesignVector : designVector) {
            if (aDesignVector) {
                value = value + 3.0;
            } else {
                value = value - 1.0;
            }
        }
        return value;
    }

    /** {@InheritDoc}
     */
    @Override
    public ArrayList<Boolean> getRandomDesignVector() {
        ArrayList<Boolean> vector = new ArrayList<Boolean>(25);
        Random rand = new Random();
        for (int i=0; i<25; i++) {
            vector.add(rand.nextBoolean());
        }
        return vector;
    }
}
