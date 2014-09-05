/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractal.util;

import fractal.fractals.AbstractFractal;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rapnik
 */
public class FractalCalculatorCallableIterator implements Iterator<FractalCalculatorCallable> {

    private int returnCount;
    final LinkedBlockingQueue<WorkBean> workList;
    private final int chunkSize;

    /**
     *
     * @param returnCount
     * @param workList
     * @param chunkSize
     */
    public FractalCalculatorCallableIterator(int returnCount, LinkedBlockingQueue<WorkBean> workList, int chunkSize) {
        this.returnCount = returnCount;
        this.workList = workList;
        this.chunkSize = chunkSize;
    }

    public boolean hasNext() {
        return returnCount > 0;
    }

    public FractalCalculatorCallable next() {
        LinkedList<WorkBean> tempList = new LinkedList<WorkBean>();
        while (returnCount-- > 0) {
            try {
                tempList.add(workList.take());
            } catch (InterruptedException ex) {
                Logger.getLogger(ResultProducer.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (tempList.size() > chunkSize) {
                return new FractalCalculatorCallable(tempList);

            }
        }
        return new FractalCalculatorCallable(tempList);
    }

    public void remove() {
        throw new UnsupportedOperationException("Not supported ");
    }
}
