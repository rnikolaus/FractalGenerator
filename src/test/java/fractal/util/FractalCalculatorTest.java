
package fractal.util;

import fractal.fractals.Xpow3minusOne;
import junit.framework.TestCase;

/**
 *
 * @author rapnik
 */
public class FractalCalculatorTest extends TestCase {
    
    /**
     *
     * @param testName
     */
    public FractalCalculatorTest(String testName) {
        super(testName);
    }

    /*
    * This doesnt test anything, but you can tweak the values here and 
    * see how it affects performance
    */

    /**
     *
     */
    
    public void testSpeed() {
        FractalCalculator fc = new FractalCalculator(new Xpow3minusOne(), 2, 1);
        long start = System.currentTimeMillis();
        fc.createFract(0.01,0,0,2000,1000);
        long now = System.currentTimeMillis();
        System.out.println((now-start)+" milliseconds have passed");
        
       
    }
}
