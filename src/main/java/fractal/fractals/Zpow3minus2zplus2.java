package fractal.fractals;

import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author rapnik
 */
public class Zpow3minus2zplus2 extends RegularNewtonFractal {

    /**
     *
     */
    public Zpow3minus2zplus2() {
        addSolution(new Complex( 0.884646177121, - 0.589742805021));
        addSolution(new Complex( 0.884646177121,  0.589742805021));
        addSolution(new Complex(- 1.769292354241, 0.0));
        
        
    }

    /**
     * x^3-2x+2
     * @param complex
     * @return
     */
    @Override
    protected Complex function(Complex complex) {
        return complex.pow(3).subtract(complex.multiply(2)).add(2);
    }

    /**
     * 3x^2-2
     * @param complex
     * @return
     */
    @Override
    protected Complex derivedFunction(Complex complex) {
        return complex.pow(2).multiply(3).subtract(2);
    }
    
    

    @Override
    public String toString() {
        return "z^3 - 2* z + 2";
    }
}
