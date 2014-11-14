package fractal.util.complex;

import junit.framework.TestCase;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author rapnik
 */
public class ComplexValueTest extends TestCase {
    
    public ComplexValueTest(String testName) {
        super(testName);
    }

    public void testEquals() {
        ComplexValue cv1 = new ComplexValue(Complex.ONE,0);
        ComplexValue cv2 = new ComplexValue(Complex.ONE,0);
        assertEquals(cv1, cv2);
        
    }
    public void testNotEquals() {
        ComplexValue cv1 = new ComplexValue(Complex.ONE,0);
        ComplexValue cv2 = new ComplexValue(Complex.ZERO,0);
        assertNotSame(cv1, cv2);
        
    }
    
}
