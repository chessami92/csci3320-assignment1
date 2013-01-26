package algorithm;

/**
 * Created by: Josh
 * On: 1/24/13 9:43 PM
 */
public class QuadraticSolution implements Solution{
    /**
     * Quadratic maximum contiguous subsequence sum algorithm.
     */
    @Override
     public int maxSubSum(int[] array) {
         int maxSum = 0;

         for( int i = 0; i < array.length; i++ ) {

             int thisSum = 0;
             for( int j = i; j < array.length; j++ ) {
                 thisSum += array[ j ];

                 if( thisSum > maxSum )
                     maxSum = thisSum;
                 }
             }

         return maxSum;
         }

    @Override
    public String getSolutionType() {
        return "Quadratic";
    }
}
