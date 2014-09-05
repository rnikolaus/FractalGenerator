/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fractal.util;

import java.awt.Color;
import junit.framework.TestCase;

/**
 *
 * @author rapnik
 */
public class FractalColorSetTest extends TestCase {
    
    public FractalColorSetTest(String testName) {
        super(testName);
    }

    public void testSomeMethod() {
        FractalColorSet fractalColorSet = new FractalColorSet();
        fractalColorSet.addFractalColor(new FractalColor(10, 0, 0));
        Color c = fractalColorSet.getColor(new FractalResult(10, 1, true));
    }
    
}
