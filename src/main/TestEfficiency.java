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
    private static final long maxExecutionTime = 5 * 60 * 1000;

    /*
     * Main entry point for program. Creates the test trials and calls to execute them.
     */
    public static void main(String[] args) {
        int[] trialSampleSize = {100, 1000, 10000, 100000};
        Solution solutions[] = {new CubicSolution(), new QuadraticSolution(), new RecursiveSolution(), new LinearSolution()};

        printTableHeader(solutions);

        for (int i = 0; i < trialSampleSize.length; ++i) {
            //Print out what trial is currently running
            System.out.print(padRight("" + trialSampleSize[i]));

            //Make the test data for the trials
            int[] testData = generateTestData(trialSampleSize[i]);

            //Run each of the trials and receive the resultant max sub-sum
            int maxSubSum = executeSolutions(solutions, testData);

            //Print out the max sub-sum
            System.out.println(maxSubSum);
        }
    }

    /*
     * Method takes a list of Solution objects and the test data.
     * Each solution is given the test data and executed in a separate thread.
     * The execution time is given maxExecutionTime millis to finish, otherwise it is interrupted.
     * Method returns the max sub-sum of the list.
     */
    private static int executeSolutions(Solution[] solutions, int[] testData) {
        int maxSubSum = 0;
        for (Solution solution : solutions) { //Loop through all solutions to the problem
            boolean timeout = false;

            SolutionRunner solutionRunner = new SolutionRunner(solution, testData);
            solutionRunner.setPriority(Thread.MAX_PRIORITY);

            Date startTime = new Date(); //Find starting time before executing
            solutionRunner.start();

            //Continue waiting for the solution unless it has reached the max time or has finished
            try {
                solutionRunner.join(maxExecutionTime); //Wait for the calculation to finish, a maximum of 5 minutes

                if (solutionRunner.getMaxSubSum() == -1) {
                    timeout = true;
                    solutionRunner.interrupt();
                }
            } catch (InterruptedException e) {
            }

            if (timeout) {
                System.out.print(padRight("timeout")); //If execution was cancelled, print so
            } else {
                Date endTime = new Date(); //Find ending time after executing
                long timeTook = (endTime.getTime() - startTime.getTime()); //Find total run time in milliseconds
                String padded = padRight(timeTook + "ms");
                System.out.print(padded);
            }

            maxSubSum = solutionRunner.getMaxSubSum();
        }

        return maxSubSum;
    }

    /*
     * Method takes an integer and makes an integer array of that size
     * Populates the array with random integer values between -length/8 and +length/8
     * Returns the reference to the generated array
     */
    private static int[] generateTestData(int length) {
        int[] testData = new int[length];

        int range = length / 4;
        int offset = length / 8;

        for (int i = 0; i < length; ++i) {
            testData[i] = numberGenerator.nextInt(range) - offset;
        }

        return testData;
    }

    /*
     * Method prints out the header by calling the getSolutionType on each solution
     * and padding right the appropriate number of spaces
     */
    private static void printTableHeader(Solution[] solvers) {
        //Print out the header for table of results
        System.out.print(padRight("n"));
        for (int i = 0; i < solvers.length; ++i) {
            System.out.print(padRight(solvers[i].getSolutionType()));
        }
        System.out.println(padRight("Max Sub-Sum"));
    }

    /*
     * Method formats the string to be padded with COLUMN_WIDTH number
     * of spaces to the right of the string for outputting to the table.
     */
    private static String padRight(String string) {
        return String.format("%1$-" + COLUMN_WIDTH + "s", string);
    }
}
