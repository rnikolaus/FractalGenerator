package fractal.fractals;

import fractal.util.FractalResult;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author rapnik
 */
public abstract class AbstractFractal {

    /**
     * 
     * @return the maximum number of iterations to calculate
     */
    public int getMaxIterations() {
        return 255;
    }

    /**
     * This is the actual calculation function
     * @param startValue the starting point for the iteration
     * @return the FractalResult
     */
    public abstract FractalResult calculate(Complex startValue);

}
