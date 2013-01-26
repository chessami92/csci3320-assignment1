package algorithm;

/**
 * Created by: Josh
 * On: 1/24/13 10:57 PM
 */
public class LinearSolution implements Solution{
    /**
     * Linear-time maximum contiguous subsequence sum algorithm.
     */
    public int maxSubSum(int[] a) {
        int maxSum = 0, thisSum = 0;

        for (int j = 0; j < a.length; j++) {
            thisSum += a[j];

            if (thisSum > maxSum)
                maxSum = thisSum;
            else if (thisSum < 0)
                thisSum = 0;
        }

        return maxSum;
    }

    @Override
    public String getSolutionType() {
        return "Linear";
    }
}
