package algorithm;

/**
 * Created by: Josh
 * On: 1/24/13 9:29 PM
 * Interface for all types of solutions.
 * Requires the solving method and a method to return a string of the solution type.
 */
public interface Solution{
    public int maxSubSum(int[] array) throws InterruptedException;
    public String getSolutionType();
}
