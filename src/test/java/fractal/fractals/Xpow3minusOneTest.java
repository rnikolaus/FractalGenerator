
package fractal.fractals;

import junit.framework.TestCase;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author rapnik
 */
public class Xpow3minusOneTest extends AbstractRegularNewtonFractalTest {
    
    /**
     *
     * @param testName
     */
    public Xpow3minusOneTest(String testName) {
        super(new Xpow3minusOne(),testName);
    }

    /**
     *
     */
    public void testCalculate() {
            Xpow3minusOne x = new Xpow3minusOne();
            assertEquals(1,x.calculate(Complex.ONE).getIterations());
            assertEquals(7, x.calculate(Complex.ONE.add(Complex.ONE)).getIterations());
    }
    
}
