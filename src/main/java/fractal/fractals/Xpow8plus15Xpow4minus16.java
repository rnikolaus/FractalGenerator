package fractal.fractals;

import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author rapnik
 */
public class Xpow8plus15Xpow4minus16 extends RegularNewtonFractal {
    
    /**
     *
     */
    public Xpow8plus15Xpow4minus16() {
        addSolution(new Complex(-1.414213562371, -1.414213562371));
        addSolution(new Complex(1.414213562371, 1.414213562371));
        addSolution(new Complex(1.414213562371, -1.414213562371));
        addSolution(new Complex(-1.414213562371, 1.414213562371));
        addSolution(new Complex(-1.0, 0.0));
        addSolution(new Complex(0.0, 1.0));
        addSolution(new Complex(0.0, -1.0));
        addSolution(new Complex(1.0, 0.0));
    }

    /**
     * x^8+15x^4-16
     * @param complex
     * @return
     */
    @Override
    protected Complex function(Complex complex) {
        return complex.pow(8).add(complex.pow(4).multiply(15)).subtract(16);
    }

    /**
     * 8x^7+60x^3
     * @param complex
     * @return
     */
    @Override
    protected Complex derivedFunction(Complex complex) {
        return complex.pow(7).multiply(8)
                                .add(complex.pow(3).multiply(60));
    }
    

    @Override
    public String toString() {
        return "x^8 + 15 * x^4 - 16";
    }

}
