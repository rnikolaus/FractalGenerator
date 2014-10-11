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

    private final FractalDimensionsBean frb;
    private final WritableRaster wr;
    private ExecutorService exs;
    private final FractalColorSet fcs;
    private boolean running = true;
    private final FinishCallback fhc;

    public FractalCalculator(FractalDimensionsBean frb, WritableRaster wr, FractalColorSet fcs, FinishCallback fhc) {
        this.frb = frb;
        this.wr = wr;
        this.fcs = fcs;
        this.fhc = fhc;
    }

    public void calculate(int threads) {
        exs = Executors.newFixedThreadPool(threads);
        DimensionFactory dimensionFactory = new DimensionFactory(frb.getSizeX(), frb.getSizeY());
        for (final DimXY dim : dimensionFactory.getDimensions()) {
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
            fhc.run();
        }

    }

    private void runFunction(DimXY dim) {
        if (!running) {
            return;
        }
        int x = dim.getX();
        int y = dim.getY();
        FractalResult fractalResult = frb.getAbstractFractal().calculate(getComplex(x, y));
        Color col = fcs.getColor(fractalResult);
        if (running) {
            wr.setPixel(x, y, new int[]{col.getRed(), col.getGreen(), col.getBlue()});
        }
    }

    public void calculate() {
        DimensionFactory dimensionFactory = new DimensionFactory(frb.getSizeX(), frb.getSizeY());
        dimensionFactory.getDimensions().stream().parallel().forEach((DimXY t) -> {
            runFunction(t);
        });

        if (running) {
            fhc.run();
        }
    }

    public Complex getComplex(int x, int y) {
        double real = frb.getFactor()
                * (x - frb.getSizeX() / 2.0) + frb.getOffsetX();
        double imaginary = frb.getFactor()
                * (y - frb.getSizeY() / 2.0) + frb.getOffsetY();
        return new Complex(real, imaginary);
    }

    public void stop() {
        running = false;
    }

}
