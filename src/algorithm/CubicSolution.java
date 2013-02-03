package algorithm;

/**
 * Created by: Josh
 * On: 1/24/13 9:27 PM
 */
public class CubicSolution implements Solution {
    /**
     * Cubic maximum contiguous subsequence sum algorithm.
     * Calculates all possible subsequences' sums separately
     */
    @Override
    public int maxSubSum(int[] array) throws InterruptedException{
        int maxSum = 0;

        for (int i = 0; i < array.length; i++) {
            //Check to see if execution has been cancelled
            if(Thread.currentThread().isInterrupted()){
                throw new InterruptedException();
            }

            for (int j = i; j < array.length ; j++) {
                int thisSum = 0;
                //Loop through all possible sub-sequences and calculate the sum
                for (int k = i; k <= j; k++)
                    thisSum += array[k];

                if (thisSum > maxSum)
                    maxSum = thisSum;

            }
        }

        return maxSum;
    }

    @Override
    public String getSolutionType() {
        return "Cubic";
    }
}
