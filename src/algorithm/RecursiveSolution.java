package algorithm;

/**
 * Created by: Josh
 * On: 1/24/13 10:11 PM
 */
public class RecursiveSolution implements Solution {

    /**
     * Recursive maximum contiguous subsequence sum algorithm.
     * Finds maximum sum in subarray spanning a[left..right].
     * Does not attempt to maintain actual best sequence.
     */
    private static int maxSumRec(int[] a, int left, int right) {
        if (left == right) // Base case
            if (a[left] > 0)
                return a[left];
            else
                return 0;

        int center = (left + right) / 2;
        int maxLeftSum = maxSumRec(a, left, center);
        int maxRightSum = maxSumRec(a, center + 1, right);

        int maxLeftBorderSum = 0, leftBorderSum = 0;
        for (int i = center; i >= left; i--) {
            leftBorderSum += a[i];
            if (leftBorderSum > maxLeftBorderSum)
                maxLeftBorderSum = leftBorderSum;
        }

        int maxRightBorderSum = 0, rightBorderSum = 0;
        for (int i = center + 1; i <= right; i++) {
            rightBorderSum += a[i];
            if (rightBorderSum > maxRightBorderSum)
                maxRightBorderSum = rightBorderSum;
        }

        return max3(maxLeftSum, maxRightSum,
                maxLeftBorderSum + maxRightBorderSum);
    }

    /**
     * Takes three numbers and returns the maximum
     */
    private static int max3(int num1, int num2, int num3) {
        if (num1 > num2 && num1 > num3)
            return num1;
        else if (num2 > num3)
            return num2;
        else
            return num3;
    }

    /**
     * Driver for divide-and-conquer maximum contiguous
     * subsequence sum algorithm.
     */
    public int maxSubSum(int[] a) {
        return maxSumRec(a, 0, a.length - 1);
    }

    @Override
    public String getSolutionType() {
        return "Recursive";
    }
}
