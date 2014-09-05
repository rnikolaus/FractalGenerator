
package fractal.fractals;

import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author rapnik
 */
public class Xpow4plusXpow3minusOne extends RegularNewtonFractal {

    /**
     *
     */
    public Xpow4plusXpow3minusOne() {
        addSolution(new Complex(0.8191725133961645, 0));
        addSolution(new Complex(-1.3802775690976141, 0));
        addSolution(new Complex(-0.2194474721492752, 0.9144736629677265));
        addSolution(new Complex(-0.21944747214927512, -0.9144736629677265));
    }

    /**
     * x^3-1
     * @param complex
     * @return
     */
    @Override
    protected Complex function(Complex complex) {
        return complex.pow(4).add(complex.pow(3)).subtract(1);
    }

    /**
     * 3*x^2
     * @param complex
     * @return
     */
    @Override
    protected Complex derivedFunction(Complex complex) {
        return complex.pow(3).multiply(4).add(complex.pow(2).multiply(3));
    }
    

    @Override
    public String toString() {
        return "x^4 + x^3 - 1";
    }
}
