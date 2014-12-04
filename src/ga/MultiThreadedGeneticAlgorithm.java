package ga;

/**
 * Created by Andrei on 11/29/2014.
 * Version v1.12
 */
public class MultiThreadedGeneticAlgorithm {

    private static int population;
    private static int eliteSize;
    private static double crossoverProb;
    private static double mutationProb;
    private static int terminationValue;
    private static Problem problem;
    private static DesignData data;

    public static void main(String[] args) throws EliteSizeException, MutationProbabilityException,
                                                  CrossoverProbabilityException, TerminationValueException,
                                                  PopulationSizeException, GAInitiationException {

        MultiThreadedGeneticAlgorithm.checkArguments(args);
        problem = Helper.getProblem(args[0]);
        data = new DesignData(eliteSize);


    }

    public static void checkArguments(String [] arg) throws EliteSizeException, CrossoverProbabilityException,
                                                            PopulationSizeException, MutationProbabilityException,
                                                            TerminationValueException{

        population = Integer.parseInt(arg[1]);
        eliteSize = Integer.parseInt(arg[2]);
        crossoverProb = Double.parseDouble(arg[3]);
        mutationProb = Double.parseDouble(arg[4]);
        terminationValue = Integer.parseInt(arg[5]);
        if (population < 1 ) throw new PopulationSizeException();
        if (eliteSize < 1 ) throw new EliteSizeException();
        if (crossoverProb < 0 || crossoverProb >1) throw new CrossoverProbabilityException();
        if (mutationProb < 0 || mutationProb > 1) throw new MutationProbabilityException();
        if (terminationValue < eliteSize) throw new TerminationValueException();

    }

    private class Evaluator extends Thread {

        private Problem problem;
        private int terminateVal;
        private Double crossoverProb;
        private Double mutationProb;
        private Design design;
        private int counter;

        private Evaluator() {
            this.problem = MultiThreadedGeneticAlgorithm.problem;
            this.terminateVal = MultiThreadedGeneticAlgorithm.terminationValue;
            this.crossoverProb = MultiThreadedGeneticAlgorithm.crossoverProb;
            this.mutationProb = MultiThreadedGeneticAlgorithm.mutationProb;
            this.design = new Design(problem);
        }


        public void run() {
            try {
                counter = 0;
                while (counter<terminateVal) {
                    design.evaluate();
                    MultiThreadedGeneticAlgorithm.data.updateRank(design);
                    counter++;
                    if(counter<terminateVal) {
                        Design otherDesign = MultiThreadedGeneticAlgorithm.data.getRank()
                        design.evolve();
                    }
                }

            } catch () {
            }
        }
    }
}
