package fractal.fractals;

import fractal.util.FractalResult;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author rapnik
 */
public class Collatz extends AbstractFractal{

    @Override
    public FractalResult calculate(Complex startValue) {
        int iterationCount = 0;
        Complex oldValue;
        Complex newValue = startValue;
        do {
            oldValue = newValue;
            //(-1^z + 1)/2 * (z / 2) – (-1^z – 1)/2 * (3*z + 1).
            final Complex minusOnePowZ = MINUSONE.pow(oldValue);
            newValue = minusOnePowZ.add(Complex.ONE).divide(2)
                    .multiply(oldValue.divide(2))
                    .subtract(
                            minusOnePowZ.subtract(Complex.ONE).divide(2)
                            .multiply(oldValue.multiply(3).add(1))
                    );
            if (iterationCount++ >= getMaxIterations()) {
                return new FractalResult(0, 0, false);
            }
        } while (newValue.abs()< 1000000);

        return new FractalResult(iterationCount, 0, true);

    }
    public static final Complex MINUSONE = Complex.valueOf(-1);

    @Override
    public String toString() {
        return "Collatz";
    }
    @Override
    public int getMaxIterations() {
        return 5000;
    }

    
}
