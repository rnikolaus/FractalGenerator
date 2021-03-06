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

       
        private final int x;
        private final int y;
        

       
        public CalculationRunnable(int x, int y){
            this.x = x;
            this.y = y;
        }

        @Override
        public void run() {
            getPixelQueue().add(runFunction(x,y));
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
            for (int localx =0;localx<fractalDimensionsBean.getSizeX();localx++){
                for (int localy =0;localy<fractalDimensionsBean.getSizeY();localy++){
                    exs.submit(new CalculationRunnable(localx, localy));
                }
                
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
