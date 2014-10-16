package fractal.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
            if (isInterrupted()) {//don't calculate if parent thread was interrupted
                return;
            }
            int[] col = runFunction(dim);
            if (isInterrupted()) {//don't draw if parent thread was interrupted
                return;
            }
            paint(dim, col);
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

        for (final DimXY dim : getDimensions()) {
            if (isInterrupted()) {
                return;
            }
            exs.submit(new CalculationRunnable(dim));

        }

        exs.shutdown();
        try {
            exs.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException ex) {
            Logger.getLogger(ThreadPoolFractalCalculator.class.getName()).log(Level.FINEST, null, ex);
        }
        if (isInterrupted()) {
            return;
        }
        runCallback();
    }

    @Override
    public void interrupt() {
        super.interrupt();
        exs.shutdownNow();
    }

}
