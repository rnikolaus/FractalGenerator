package fractal.fractals;

import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author rapnik
 */
public class SinX extends NewtonFractal {

    private final Complex complexPi = new Complex(Math.PI, 0);

    /**
     * 
     * @param complex the solution of the newton method
     * @return the result number to use for coloring
     */
    @Override
    public int getResultNum(Complex complex) {
        return (int) Math.abs(complex.divide(complexPi).getReal());
    }

    @Override
    public String toString() {
        return "SinX";
    }

    /**
     * this is the sine function
     * @param complex
     * @return
     */
    @Override
    protected Complex function(Complex complex) {
        return complex.sin();
    }

    /**
     * cosine is the first drivative of sine
     * @param complex
     * @return
     */
    @Override
    protected Complex derivedFunction(Complex complex) {
        return complex.cos();
    }
    

}
