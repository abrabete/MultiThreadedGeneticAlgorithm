package ga;

/**
 * Created by Andrei on 11/29/2014.
 * Version v1.12
 */
class MultiThreadedGeneticAlgorithm {

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
        (new MultiThreadedGeneticAlgorithm()).runEvaluators();
        //data.serialize();

        data.sortRank();
        data.saveFile();
    }

    private static void checkArguments(String[] arg) throws EliteSizeException, CrossoverProbabilityException,
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
        private final int terminateVal;
        private final Double crossoverProb;
        private final Double mutationProb;
        private final Design design;
        private Design otherDesign;
        private int counter;

        private Evaluator() {
            this.problem = MultiThreadedGeneticAlgorithm.problem;
            this.terminateVal = MultiThreadedGeneticAlgorithm.terminationValue;
            this.crossoverProb = MultiThreadedGeneticAlgorithm.crossoverProb;
            this.mutationProb = MultiThreadedGeneticAlgorithm.mutationProb;
            this.design = new Design(problem);
        }


        public void run() {
            counter = 0;
            while (counter<terminateVal) {
                this.design.evaluate();
                if (this.design.isEvaluated()) {
                    if (this.design != null) {
                        MultiThreadedGeneticAlgorithm.data.updateRank(this.design);
                    }
                }
                counter++;
                boolean updated = false;
                if(counter<terminateVal) {
                    while (!updated) {
                        if (!MultiThreadedGeneticAlgorithm.data.getRankLockStatus()) {

                            if (MultiThreadedGeneticAlgorithm.data.getRank().size() >= 0) {
                                this.otherDesign = MultiThreadedGeneticAlgorithm.data.getRankDesign();
                                this.design.evolve(this.otherDesign, this.crossoverProb, this.mutationProb);
                            }
                            updated = true;
                        } else if (!MultiThreadedGeneticAlgorithm.data.getBackupLockStatus()) {

                            if (MultiThreadedGeneticAlgorithm.data.getRankBackup().size() >= 0) {
                                this.otherDesign = MultiThreadedGeneticAlgorithm.data.getBackupDesign();
                                this.design.evolve(this.otherDesign, this.crossoverProb, this.mutationProb);
                            }
                            updated = true;
                        }
                    }
                }
            }
        }
    }

    private void runEvaluators() {
            for (int i = 0; i < population; i++) {
                (new Evaluator()).start();
            }
    }
}
