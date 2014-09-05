package fractal.util;

import fractal.fractals.Xpow3minusOne;
import junit.framework.TestCase;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author rapnik
 */
public class WorkBeanTest extends TestCase {
    
    /**
     *
     * @param testName
     */
    public WorkBeanTest(String testName) {
        super(testName);
    }

    /**
     *
     */
    public void testComplex() {
        WorkBean wb = new WorkBean(0, 0, 1, 0, 0, 200, 200,new Xpow3minusOne());
        Complex c = new Complex(-100, -100);
        assertEquals(c,wb.getComplex());
    }

    /**
     *
     */
    public void testDimXY() {
        WorkBean wb = new WorkBean(0, 0, 1, 0, 0, 200, 200,new Xpow3minusOne());
        DimXY dim = new DimXY(0,0);
        assertEquals(dim,wb.getDimXY());
    }
    
}
