
package fractal.fractals;

import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author rapnik
 */
public class Xpow3minusOne extends RegularNewtonFractal {

    /**
     *
     */
    public Xpow3minusOne() {
        addSolution(new Complex(-0.5, -0.8660254037844386));
        addSolution(new Complex(-0.5, 0.8660254037844387));
        addSolution(new Complex(1.0, 0));
    }

    /**
     * x^3-1
     * @param complex
     * @return
     */
    @Override
    protected Complex function(Complex complex) {
        return complex.pow(3).subtract(1);
    }

    /**
     * 3*x^2
     * @param complex
     * @return
     */
    @Override
    protected Complex derivedFunction(Complex complex) {
        return complex.pow(2).multiply(3);
    }
    

    @Override
    public String toString() {
        return "x^3 - 1";
    }
}
