
package fractal.util;

import fractal.fractals.AbstractFractal;
import java.util.List;
import java.util.concurrent.Callable;

/**
 *
 * @author rapnik
 */
public class FractalCalculatorCallable implements Callable<ResultIterator> {

    final List<WorkBean> valuesToCalculate;

    /**
     *
     * @param valuesToCalculate
     */
    public FractalCalculatorCallable(List<WorkBean> valuesToCalculate) {
        this.valuesToCalculate = valuesToCalculate;
    }

    @Override
    public ResultIterator call() throws Exception {
        return new ResultIterator(valuesToCalculate);
    }
}