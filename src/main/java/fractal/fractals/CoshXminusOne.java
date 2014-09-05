package fractal.fractals;

import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author rapnik
 */
public class CoshXminusOne extends NewtonFractal {

    private final Complex twoImaginaryPi = new Complex(0, 2*Math.PI);

    /**
     *
     * @param complex the solution of the iteration
     * @return the number to use for coloring
     */
    @Override
    public int getResultNum(Complex complex) {
        return (int) complex.divide(twoImaginaryPi).abs();
    }

    /**
     *
     * @param complex the input value for the function
     * @return the result of the function
     */
    @Override
    protected Complex function(Complex complex) {
        return complex.cosh().subtract(1);
    }

    /**
     *
     * @param complex the input value for the first derivative of the function
     * @return the result of the first derivative of the function
     */
    @Override
    protected Complex derivedFunction(Complex complex) {
        return complex.sinh();
    }
    

    @Override
    public String toString() {
        return "Cosh x - 1";
    }

}
