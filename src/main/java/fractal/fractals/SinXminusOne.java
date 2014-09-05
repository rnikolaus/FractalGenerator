package fractal.fractals;

import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author rapnik
 */
public class SinXminusOne extends NewtonFractal{
    private final Complex complexPi = new Complex(Math.PI, 0);
    private final Complex halfPi = new Complex(Math.PI/2,0);

    /**
     * sin(x) - 1
     * @param complex
     * @return
     */
    @Override
    protected Complex function(Complex complex) {
        return complex.sin().subtract(1);
    }

    /**
     * cos(x)
     * @param complex
     * @return
     */
    @Override
    protected Complex derivedFunction(Complex complex) {
        return complex.cos();
    }
    
    /**
     * 
     * @param complex
     * @return
     */
    @Override
    public int getResultNum(Complex complex) {
        return(int)Math.abs(complex.subtract(halfPi).divide(complexPi).getReal());
    }

    @Override
    public String toString() {
        return "Sin (x) - 1";
    }
    
    
    
}
