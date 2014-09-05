
package fractal.fractals;

import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author rapnik
 */
public class Zpow6plusZpow3minus1 extends RegularNewtonFractal {

    /**
     *
     */
    public Zpow6plusZpow3minus1() {
        addSolution(new Complex(0.851799642081, 0.000000000001));
        addSolution(new Complex(-0.425899821041, 0.737680128981));
        addSolution(new Complex(-1.173984996711, 0.000000000001));
        addSolution(new Complex(-0.425899821041, -0.737680128981));
        addSolution(new Complex(0.586992498351, 1.016700830811));
        addSolution(new Complex(0.586992498351, -1.016700830811));
    }

    /**
     * x^6+x^3-1
     * @param complex
     * @return
     */
    @Override
    protected Complex function(Complex complex) {
        return complex.pow(6).add(
                        complex.pow(3)).subtract(1);
    }

    /**
     * 6x^5+3x^2
     * @param complex
     * @return
     */
    @Override
    protected Complex derivedFunction(Complex complex) {
        return complex.pow(5).multiply(6).add(
                                complex.pow(2).multiply(3));
    }
    
    

    @Override
    public String toString() {
        return "Z^6 + Z^3 - 1";
    }
}
