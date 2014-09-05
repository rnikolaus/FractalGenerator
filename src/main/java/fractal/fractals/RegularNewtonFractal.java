package fractal.fractals;

import fractal.util.complex.ComplexValue;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author rapnik
 */
public abstract class RegularNewtonFractal extends NewtonFractal{
    private final List<ComplexValue> solutionList = new LinkedList<>();
    

    /**
     *
     * @return the list of solutions for f(x)=0
     */
    protected List<ComplexValue> getSolutions(){
        return solutionList;
    }
    protected void addSolution(Complex complex){
        solutionList.add(new ComplexValue(complex, 7));
    }

    
    /**
     * get the index number of the calculated result
     * to calculate the color from it
     * @param complex
     * @return the index of the result
     */
    @Override
    public int getResultNum(Complex complex){
        ComplexValue cv = new ComplexValue(complex, 7);
        return getSolutions().lastIndexOf(cv);
    }

    

}
