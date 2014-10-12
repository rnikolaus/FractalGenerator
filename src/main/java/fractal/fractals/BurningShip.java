package fractal.fractals;

import fractal.util.FractalResult;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author rapnik
 */
public class BurningShip extends Mandelbrot{
    @Override
    public FractalResult calculate(Complex startValue) {
        int iterationCount = 0;
        Complex oldValue;
        Complex newValue = startValue;
        do {
            oldValue = newValue;
            
            newValue = new Complex(Math.abs(oldValue.getReal()) ,Math.abs(oldValue.getImaginary())).pow(2).add(startValue);
            if (iterationCount++ >= getMaxIterations()) {
                return new FractalResult(0, 0, false);
            }
        } while (newValue.abs()< 1000000);

        return new FractalResult(iterationCount, 0, true);

    }

    @Override
    public String toString() {
        return "BurningShip";
    }
    
}
