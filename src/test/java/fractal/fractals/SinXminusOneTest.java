
package fractal.fractals;

import junit.framework.TestCase;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author rapnik
 */
public class SinXminusOneTest extends AbstractNewtonFractalTest {
    
    public SinXminusOneTest(String testName) {
        super(new SinXminusOne(),testName);
        solutionList.add(new Complex(Math.PI).divide(2));
    }

}
