package fractal.fractals;

import java.util.LinkedList;
import java.util.List;
import junit.framework.TestCase;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author rapnik
 */
public abstract class AbstractNewtonFractalTest extends TestCase{
    NewtonFractal newtonFractal;
    List<Complex> solutionList = new LinkedList<Complex>();

    /**
     *
     * @param newtonFractal
     * @param testName
     */
    public AbstractNewtonFractalTest(NewtonFractal newtonFractal, String testName) {
        super(testName);
        this.newtonFractal=newtonFractal;
    }
    
    public void testFunction(){
        System.out.println("Testing function against solutions:");
        for (Complex c :solutionList){
            System.out.println("Testing "+c);
            assertEquals(0.0, newtonFractal.function(c).divide(newtonFractal.derivedFunction(c)).abs(), 0.0000001);
        }
    }
}
