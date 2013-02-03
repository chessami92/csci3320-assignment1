package algorithm;

/**
 * Created by: Josh
 * On: 1/25/13 7:55 PM
 */
public class SolutionRunner extends Thread {
    private Solution solver;
    private int[] testData;
    private int maxSubSum = -1;

    public SolutionRunner(Solution solver, int[] testData){
        this.solver = solver;
        this.testData = testData;
    }

    @Override
    public void run() {
        try {
            maxSubSum = solver.maxSubSum(testData);
        } catch (InterruptedException e) {
        }
    }

    public int getMaxSubSum() {
        return maxSubSum;
    }
}
