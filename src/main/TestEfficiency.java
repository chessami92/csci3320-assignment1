package main;

import algorithm.*;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created By: Josh
 * On: 1/24/13 8:47 PM
 */
public class TestEfficiency {
    private static final int COLUMN_WIDTH = 15;
    private static final Random numberGenerator = new Random();

    /*
     * Main entry point for program. Creates the test trials and calls to execute them.
     */
    public static void main(String[] args) {
        int[] trialSampleSize = {100, 1000, 10000, 100000};
        Solution solutions[] = {new CubicSolution(), new QuadraticSolution(), new RecursiveSolution(), new LinearSolution()};

        printTableHeader(solutions);

        for (int i = 0; i < trialSampleSize.length; ++i) {
            //Print out what trial is currently running
            String padded = padRight("n=" + trialSampleSize[i], COLUMN_WIDTH);
            System.out.print(padded);

            int[] testData = generateTestData(trialSampleSize[i]);

            int maxSubSum = executeSolutions(solutions, testData);

            System.out.println(maxSubSum);
        }
    }

    /*
     * Method that takes a list of Solution objects that can be passed
     */
    private static int executeSolutions(Solution[] solutions, int[] testData) {
        int maxSubSum = 0;
        for (int i = 0; i < solutions.length; ++i) { //loop through all solutions to the problem
            boolean timeout = false;

            SolutionRunner solutionRunner = new SolutionRunner(solutions[i], testData);
            solutionRunner.setPriority(Thread.MAX_PRIORITY);

            Date startTime = new Date(); //Find starting time before executing
            solutionRunner.start();

            //Continue waiting for the solution unless it has reached the max time or has finished
            try {
                final long maxWaitTime = 5 * 60 * 1000;
                solutionRunner.join(maxWaitTime); //Wait for the calculation to finish, a maximum of 5 minutes

                if(solutionRunner.getMaxSubSum() == -1){
                    timeout = true;
                    solutionRunner.interrupt();
                }
            } catch (InterruptedException e) {
            }

            String padded;
            if(timeout){
                padded = padRight("timeout", COLUMN_WIDTH);
            }
            else{
                Date endTime = new Date(); //Find ending time after executing
                long timeTook = (endTime.getTime() - startTime.getTime()); //Find total run time in milliseconds
                padded = padRight(timeTook + "ms", COLUMN_WIDTH);
            }

            System.out.print(padded);
            maxSubSum = solutionRunner.getMaxSubSum();
        }

        return maxSubSum;
    }

    private static int[] generateTestData(int length) {
        int[] testData = new int[length];

        int range = length / 4;
        int offset = length / 8;

        for (int i = 0; i < length; ++i) {
            testData[i] = numberGenerator.nextInt(range) - offset;
        }

        return testData;
    }

    private static void printTableHeader(Solution[] solvers) {
        //Print out the header for table of results
        System.out.print(padRight("", COLUMN_WIDTH));
        for (int i = 0; i < solvers.length; ++i) {
            System.out.print(padRight(solvers[i].getSolutionType(), COLUMN_WIDTH));
        }
        System.out.println(padRight("Max Sub-Sum", COLUMN_WIDTH));
    }

    private static String padRight(String string, int n) {
        return String.format("%1$-" + n + "s", string);
    }
}
