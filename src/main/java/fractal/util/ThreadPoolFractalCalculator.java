package fractal.util;

import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rapnik
 */
public class ThreadPoolFractalCalculator extends AbstractFractalCalculator {
    private class DimensionIterator implements Iterable<DimXY>{

        final private int x;
        final private int y;
        public DimensionIterator(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        @Override
            public Iterator<DimXY> iterator() {
                return new Iterator<DimXY>() {
                    int xlocal = 0;
                    int ylocal = 0;
                    DimXY nextVal = new DimXY(xlocal, ylocal);

                    @Override
                    public boolean hasNext() {
                        return nextVal!=null;
                    }

                    @Override
                    public DimXY next() {
                        DimXY result = nextVal;
                        ylocal++;
                        if (!(ylocal<y)){
                            ylocal=0;
                            xlocal++;
                            if (!(xlocal<x)){
                                nextVal = null;
                                return result;
                            }
                        }
                        nextVal=new DimXY(xlocal, ylocal);
                        return result;
                        
                    }
                };
            }
    }

    private class CalculationRunnable implements Runnable {

        private final DimXY dim;

        public CalculationRunnable(DimXY dim) {
            this.dim = dim;
        }

        @Override
        public void run() {
            getPixelQueue().add(runFunction(dim));
        }

    }

    private final ExecutorService exs;

    public ThreadPoolFractalCalculator(FractalConfigBean fractalDimensionsBean,
            Runnable finishCallback, int threads) {
        super(fractalDimensionsBean, finishCallback);
        exs = Executors.newFixedThreadPool(threads);
    }

    @Override
    public void run() {
        try {
            for (DimXY dim :new DimensionIterator(
                    fractalDimensionsBean.getSizeX(), 
                    fractalDimensionsBean.getSizeY())) {
                
                exs.submit(new CalculationRunnable(dim));
            }
            exs.shutdown();
            exs.awaitTermination(1, TimeUnit.DAYS);
            runCallback();
        } catch (InterruptedException | RejectedExecutionException ex) {
            Logger.getLogger(ThreadPoolFractalCalculator.class.getName()).log(Level.FINEST, null, ex);
        }
    }

    @Override
    public void interrupt() {
        exs.shutdownNow();
        super.interrupt();

    }

}
