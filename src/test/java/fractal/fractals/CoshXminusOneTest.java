

package fractal.fractals;

import junit.framework.TestCase;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author rapnik
 */
public class CoshXminusOneTest extends AbstractNewtonFractalTest {
    
    public CoshXminusOneTest(String testName) {
        super(new CoshXminusOne(),testName);
        solutionList.add(new Complex(0, 2*Math.PI));
    }

    
}
