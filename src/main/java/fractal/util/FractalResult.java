package fractal.util;

/**
 *
 * @author rapnik
 */
public class FractalResult {
    private final int iterations;
    private final int result;
    private final boolean solutionFound;

    /**
     *
     * @param iterations
     * @param result
     * @param solutionFound
     */
    public FractalResult(int iterations, int result, boolean solutionFound) {
        this.iterations = iterations;
        this.result = result;
        this.solutionFound = solutionFound;
    }

    

    /**
     * @return the iterations
     */
    public int getIterations() {
        return iterations;
    }

    /**
     * @return the result
     */
    public int getResult() {
        return result;
    }

    /**
     * @return the solutionFound
     */
    public boolean isSolutionFound() {
        return solutionFound;
    }
}
