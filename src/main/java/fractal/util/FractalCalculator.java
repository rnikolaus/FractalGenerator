/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractal.util;

import java.awt.Color;
import java.awt.image.WritableRaster;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author rapnik
 */
public class FractalCalculator {

    public interface FinishCallback {

        public void run();
    }

    private final FractalDimensionsBean fractalDimensionsBean;

    private final FractalColorSet fractalColorSet;
    private boolean running = true;
    private final FinishCallback finishCallback;

    public FractalCalculator(FractalDimensionsBean fractalDimensionsBean, 
            FractalColorSet fractalColorSet, FinishCallback finishCallback) {
        this.fractalDimensionsBean = fractalDimensionsBean;
        this.fractalColorSet = fractalColorSet;
        this.finishCallback = finishCallback;
    }

    /**
     * This calculates the fractal. It's asynchronus, so it returns immediately.
     * if it finishes before stop() has been called the FinishCallback will be
     * invoked
     *
     * @param threads the number of threads in the calculation threadpool
     */
    public void calculate(int threads) {
        Thread calculationThread = new Thread(new Runnable() {

            @Override
            public void run() {
                ExecutorService exs = Executors.newFixedThreadPool(threads);
                
                for (final DimXY dim : DimensionFactory.getDimensions(fractalDimensionsBean)) {
                    exs.submit(new Runnable() {

                        @Override
                        public void run() {
                            runFunction(dim);
                        }

                    });
                }

                exs.shutdown();
                try {
                    exs.awaitTermination(1, TimeUnit.DAYS);
                } catch (InterruptedException ex) {
                    Logger.getLogger(FractalCalculator.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (running) {
                    finishCallback.run();
                }

            }
        });
        calculationThread.start();

    }

    private void runFunction(DimXY dim) {
        if (!running) {
            return;
        }
        int x = dim.getX();
        int y = dim.getY();
        FractalResult fractalResult = fractalDimensionsBean.getAbstractFractal().calculate(getComplex(x, y));
        if (running) {
            Color col = fractalColorSet.getColor(fractalResult);
            fractalDimensionsBean.getImgData().setPixel(x, y, new int[]{col.getRed(), col.getGreen(), col.getBlue()});
        }
    }

    /**
     * calculates the fractal with lambdas and streams.
     * It is asynchronus, so it returns immediately.
     * If it finishes before stop() has been called,
     * the FinishCallback will be invoked.
     */
    public void calculate() {
        Thread calculationThread = new Thread(() -> {
            DimensionFactory.getDimensions(fractalDimensionsBean).stream().parallel().forEach((DimXY dim) -> {
                runFunction(dim);
            });
            if (running) {
                finishCallback.run();
            }
        });
        calculationThread.start();
    }

    private Complex getComplex(int x, int y) {
        double real = fractalDimensionsBean.getFactor()
                * (x - fractalDimensionsBean.getSizeX() / 2.0) + fractalDimensionsBean.getOffsetX();
        double imaginary = fractalDimensionsBean.getFactor()
                * (y - fractalDimensionsBean.getSizeY() / 2.0) + fractalDimensionsBean.getOffsetY();
        return new Complex(real, imaginary);
    }

    /**
     * This method stops the calculation, the FinishCallback won't run
     */
    public void stop() {
        running = false;
    }

}
