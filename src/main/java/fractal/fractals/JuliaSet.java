package fractal.fractals;

import fractal.util.FractalResult;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author rapnik
 */
public class JuliaSet extends AbstractFractal {
    public static final Complex START = Complex.valueOf(-0.6,0.6);

    /**
     *
     * @param startValue the starting point for the iteration
     * @return the FractalResult
     */
    @Override
    public FractalResult calculate(Complex startValue) {
        int iterationCount = 0;
        Complex oldValue;
        Complex newValue = startValue;
        do {
            oldValue = newValue;
            newValue = oldValue.multiply(oldValue).add(START);
            if (iterationCount++ >= getMaxIterations()) {
                return new FractalResult(0, 0, false);
            }
        } while (newValue.abs()< 1000000);

        return new FractalResult(iterationCount, 0, true);

    }

    
    

    @Override
    public String toString() {
        return "JuliaSet";
    }

}
