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
        Integer value = 0;
        for (int i = 0; i < designVector.length; i++) {
            if (designVector[i]) {
                value = value + 3;
            }
            else {
                value = value - 1;
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
        for (int i=0; i<vector.size(); i++) {
            vector.set(i, rand.nextBoolean());
        }
        return vector;
    }
}
