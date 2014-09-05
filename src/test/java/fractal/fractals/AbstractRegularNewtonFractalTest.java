/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fractal.fractals;

import fractal.util.complex.ComplexValue;
import junit.framework.TestCase;

/**
 *
 * @author rapnik
 */
public abstract class AbstractRegularNewtonFractalTest extends TestCase{
    RegularNewtonFractal newtonFractal;

    /**
     *
     * @param newtonFractal
     * @param testName
     */
    public AbstractRegularNewtonFractalTest(RegularNewtonFractal newtonFractal, String testName) {
        super(testName);
        this.newtonFractal=newtonFractal;
    }
    
    /**
     *
     */
    public void testFunction(){
        System.out.println("Testing function against solutions:");
        for (ComplexValue c :newtonFractal.getSolutions()){
            System.out.println("Testing "+c.getApacheComplex());
            assertEquals(0.0, newtonFractal.function(c.getApacheComplex()).divide(newtonFractal.derivedFunction(c.getApacheComplex())).abs(), 0.000001);
        }
    }

    
    
}
