
package fractal.fractals;

import fractal.util.FractalResult;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author rapnik
 */
public abstract class NewtonFractal extends AbstractFractal{

    /**
     *
     * @param complex
     * @return
     */
    abstract public int getResultNum(Complex complex);
     /**
     * This is the calculation function
     * @param complex the value for x
     * @return f(x)
     */
    protected abstract Complex function(Complex complex);

    /**
     * the first derivative of f(x)
     * f'(x)
     * @param complex x
     * @return f'(x)
     */
    protected abstract Complex derivedFunction(Complex complex);
    /**
     * This returns the minimum distance between the iteration 
     * values to continue the iteration
     * @return the distance
     */
    protected double getMinDistance(){
        return 0.000000000001;
    }

     /**
     * This is the actual calculation function
     * @param startValue the starting point of the iteration
     * @return the FractalResult
     */
    @Override
    public FractalResult calculate(Complex startValue) {
        int iterationCount = 0;
        Complex oldValue;
        Complex newValue = startValue;
        do {
            oldValue = newValue;
            newValue = oldValue.subtract(
                    function(oldValue).divide(
                            derivedFunction(oldValue))
            );
            if (iterationCount++>=getMaxIterations()||newValue.equals(Complex.NaN)){
                return new FractalResult(0, 0, false);
            }
        } while (oldValue.subtract(newValue).abs() > getMinDistance());
        
        return new FractalResult(iterationCount, getResultNum(newValue), true);
    }
    
}
