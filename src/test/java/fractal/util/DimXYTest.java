/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
