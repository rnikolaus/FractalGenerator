

package fractal.fractals;

import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author rapnik
 */
public class SinXTest extends AbstractNewtonFractalTest {
    
    public SinXTest(String testName) {
        super(new SinX(),testName);
        solutionList.add(Complex.ZERO);
    }

    
}
