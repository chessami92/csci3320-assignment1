package algorithm;

/**
 * Created by: Josh
 * On: 1/25/13 7:55 PM
 */
public class SolutionRunner implements Runnable {
    private Solution solver;
    private int[] testData;
    private int maxSubSum;

    @Override
    public void run() {
        maxSubSum = solver.maxSubSum(testData);
    }

    public int getMaxSubSum() {
        return maxSubSum;
    }

    public void setSolver(Solution solver) {
        this.solver = solver;
    }

    public void setTestData(int[] testData) {
        this.testData = testData;
    }
}
