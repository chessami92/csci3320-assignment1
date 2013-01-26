package main;

import algorithm.*;

import java.util.Date;
import java.util.Random;

/**
 * Created By: Josh
 * On: 1/24/13 8:47 PM
 */
public class TestEfficiency {
    private static final int COLUMN_WIDTH = 15;
    private static final Random numberGenerator = new Random();
    private static final SolutionRunner solutionRunner = new SolutionRunner();

    /*
     * Main entry point for program. Creates the test trials and calls to execute them.
     */
    public static void main(String[] args) {
        int[] trialSampleSize = {100, 1000, 10000, 100000};
        Solution solvers[] = {new CubicSolution(), new QuadraticSolution(), new RecursiveSolution(), new LinearSolution()};

        printTableHeader(solvers);

        for (int i = 0; i < trialSampleSize.length; ++i) {
            //Print out what trial is currently running
            String padded = padRight("n=" + trialSampleSize[i], COLUMN_WIDTH);
            System.out.print(padded);

            int[] testData = generateTestData(trialSampleSize[i]);
            solutionRunner.setTestData(testData);

            int maxSubSum = executeSolutions(solvers);

            System.out.println(maxSubSum);
        }
    }

    private static void printTableHeader(Solution[] solvers) {
        //Print out the header for table of results
        System.out.print(padRight("", COLUMN_WIDTH));
        for (int i = 0; i < solvers.length; ++i) {
            System.out.print(padRight(solvers[i].getSolutionType(), COLUMN_WIDTH));
        }
        System.out.println(padRight("Max Sub-Sum", COLUMN_WIDTH));
    }

    private static int executeSolutions(Solution[] solvers) {
        for (int i = 0; i < solvers.length; ++i) { //loop through all solutions to the problem
            solutionRunner.setSolver(solvers[i]);
            final Thread thread = new Thread(solutionRunner);
            thread.setPriority(1); //Allow the thread executing solutions higher priority than the monitoring thread

            Date startTime = new Date(); //Find starting time before executing
            thread.start(); //Run the solution on the given data set

            long maxRunTime = startTime.getTime() + 5 * 60 * 1000; //Timeout after 5 minutes
            while (thread.isAlive()) {
                if (System.currentTimeMillis() > maxRunTime) {
                    thread.interrupt();
                    break;
                }
            }

            Date endTime = new Date(); //Find ending time after executing
            String padded;
            if (!thread.isInterrupted()) {
                long timeTook = (endTime.getTime() - startTime.getTime()); //Find total run time in milliseconds
                padded = padRight(timeTook + "ms", COLUMN_WIDTH);
            } else {
                padded = padRight("timeout", COLUMN_WIDTH);
            }

            System.out.print(padded);
        }

        return solutionRunner.getMaxSubSum();
    }

    private static String padRight(String string, int n) {
        return String.format("%1$-" + n + "s", string);
    }

    private static int[] generateTestData(int length) {
        int[] testData = new int[length];

        int range = length / 2;
        int offset = length / 4;

        for (int i = 0; i < length; ++i) {
            testData[i] = numberGenerator.nextInt(range) - offset;
        }

        return testData;
    }
}
