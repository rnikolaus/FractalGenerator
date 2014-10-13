/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractal.util;

import java.awt.Color;
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

    private ExecutorService exs;
    private Thread calculationThread;

    public interface FinishCallback {

        public void run();
    }

    private final FractalDimensionsBean fractalDimensionsBean;

    private final FractalColorSet fractalColorSet;
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
        exs = Executors.newFixedThreadPool(threads);
        calculationThread = new Thread() {

            @Override
            public void run() {

                for (final DimXY dim : DimensionFactory.getDimensions(fractalDimensionsBean)) {
                    if (isInterrupted()) {
                        return;
                    }
                    exs.submit(new Thread() {

                        @Override
                        public void run() {
                            if (isInterrupted()) {
                                return;
                            }
                            int[] col = runFunction(dim);
                            if (isInterrupted()) {
                                return;
                            }
                            fractalDimensionsBean.getImgData().setPixel(dim.getX(), dim.getY(), col);

                        }

                    });

                }

                exs.shutdown();
                try {
                    exs.awaitTermination(1, TimeUnit.DAYS);
                } catch (InterruptedException ex) {
                    Logger.getLogger(FractalCalculator.class.getName()).log(Level.FINEST, null, ex);
                }
                if (isInterrupted()) {
                    return;
                }
                finishCallback.run();
            }
        };
        calculationThread.start();

    }

    private int[] runFunction(DimXY dim) {
        int x = dim.getX();
        int y = dim.getY();
        FractalResult fractalResult = fractalDimensionsBean.getAbstractFractal().calculate(getComplex(x, y));

        Color col = fractalColorSet.getColor(fractalResult);
        return new int[]{col.getRed(), col.getGreen(), col.getBlue()};

    }

    /**
     * calculates the fractal with lambdas and streams. It is asynchronus, so it
     * returns immediately. If it finishes before stop() has been called, the
     * FinishCallback will be invoked.
     */
    public void calculate() {
        calculationThread = new Thread() {

            @Override
            public void run() {
                DimensionFactory.getDimensions(fractalDimensionsBean).stream().parallel().filter((DimXY t) -> !isInterrupted()).forEach((DimXY dim) -> {
                    int[] col = runFunction(dim);
                    if (isInterrupted()) {
                        return;
                    }
                    fractalDimensionsBean.getImgData().setPixel(dim.getX(), dim.getY(), col);

                });
                if (isInterrupted()) {
                    return;
                }
                finishCallback.run();

            }
        };

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

        if (calculationThread != null) {
            calculationThread.interrupt();
        }
        if (exs != null) {
            exs.shutdownNow();
        }
    }

}
