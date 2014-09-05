
package fractal.util;

import fractal.fractals.AbstractFractal;
import java.util.Collections;
import java.util.Iterator;
import java.util.Observable;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rapnik
 */
public class FractalCalculator implements Iterable<ResultIterator> {
    final AbstractFractal fractalStrategy;
    private ExecutorService exs;
    private final int threadpoolSize;
    final int workChunkSize;
    private final Queue<Future<ResultIterator>> result = new ConcurrentLinkedQueue<Future<ResultIterator>>();

    /**
     *
     * @param fractalStrategy
     * @param threadpoolSize
     * @param workChunkSize
     */
    public FractalCalculator(AbstractFractal fractalStrategy, int threadpoolSize, int workChunkSize) {
        this.fractalStrategy = fractalStrategy;
        this.threadpoolSize = threadpoolSize;
        this.workChunkSize = workChunkSize;
        exs = Executors.newFixedThreadPool(threadpoolSize);
    }

    /**
     *
     * @param factor
     * @param offsetX
     * @param offsetY
     * @param sizeX
     * @param sizeY
     */
    public synchronized void createFract(double factor, double offsetX, double offsetY, int sizeX, int sizeY) {      
        stop();
        ResultProducer workCache = new ResultProducer(workChunkSize, 
                fractalStrategy, sizeX, sizeY, factor, offsetX, offsetY);
        calculate(workCache);
    }
    

    private void calculate(ResultProducer workCache) {
        exs.submit(workCache);
        for (FractalCalculatorCallable c : workCache) {
            result.add(exs.submit(c));
        }
    }

    /**
     *
     */
    public synchronized void stop() {
        result.clear();
        exs.shutdownNow();
        exs = Executors.newFixedThreadPool(threadpoolSize);
    }

    public Iterator<ResultIterator> iterator() {
        return new Iterator<ResultIterator>() {
            public boolean hasNext() {
                return !result.isEmpty();
            }
            
            public ResultIterator next() {
                
                    try {
                        ResultIterator res = result.poll().get();
                        return res;
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FractalCalculator.class.getName()).log(Level.ALL, null, ex);
                    } catch (ExecutionException ex) {
                        Logger.getLogger(FractalCalculator.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return new ResultIterator(Collections.EMPTY_LIST);
                }
            

            public void remove() {
                throw new UnsupportedOperationException("Not supported"); 
            }
        };
    }

    
}
