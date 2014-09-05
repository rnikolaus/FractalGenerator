
package fractal.util;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author rapnik
 */
public class ResultIterator implements Iterable<WorkBean> {

    final private List<WorkBean> result;

    /**
     *
     * @param valuesToCalculate
     */
    public ResultIterator( List<WorkBean> valuesToCalculate) {
        
        this.result = Collections.unmodifiableList( valuesToCalculate);
        for (WorkBean wb : valuesToCalculate) {
            wb.calculate();
        }
    }

    public Iterator<WorkBean> iterator() {
        return result.iterator();
    }
}
