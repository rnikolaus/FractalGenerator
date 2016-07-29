

package fractal.util;

import junit.framework.TestCase;

/**
 *
 * @author rapnik
 */
public class DimXYTest extends TestCase {
    
    /**
     *
     * @param testName
     */
    public DimXYTest(String testName) {
        super(testName);
    }

    /**
     *
     */
    public void testSomeMethod() {
       DimXY dimXY= new DimXY(1,2);
        assertEquals(dimXY.getX(), 1);
        assertEquals(dimXY.getY(), 2);
        
    }
    
}
