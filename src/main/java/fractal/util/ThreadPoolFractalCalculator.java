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

    public ThreadPoolFractalCalculator(FractalDimensionsBean fractalDimensionsBean,
            FractalColorSet fractalColorSet, Runnable finishCallback, int threads) {
        super(fractalDimensionsBean, fractalColorSet, finishCallback);
        exs = Executors.newFixedThreadPool(threads);
    }

    @Override
    public void run() {
        try {
            for (Iterator<DimXY> producer = getDimensionProducer(); producer.hasNext();) {
                DimXY dim = producer.next();
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
