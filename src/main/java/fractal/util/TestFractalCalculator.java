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

import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author rapnik
 */
public class TestFractalCalculator {

    private final FractalDimensionsBean frb;
    private final WritableRaster wr;
    private ExecutorService exs;
    private final FractalColorSet fcs;
    private boolean running = true;

    public TestFractalCalculator(FractalDimensionsBean frb, WritableRaster wr, FractalColorSet fcs) {
        this.frb = frb;
        this.wr = wr;
        this.fcs = fcs;
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
    }

    private void runFunction(DimXY dim) {
        if (!running) {
            return;
        }
        int x = dim.getX();
        int y = dim.getY();
        FractalResult fractalResult = frb.getAbstractFractal().calculate(getComplex(x, y));
        Color col = fcs.getColor(fractalResult);
        if (running)wr.setPixel(x, y, new int[]{col.getRed(), col.getGreen(), col.getBlue()});
    }

    public void calculate() {
        DimensionFactory dimensionFactory = new DimensionFactory(frb.getSizeX(), frb.getSizeY());
        dimensionFactory.getDimensions().stream().parallel().forEach((DimXY t) -> {
            runFunction(t);
        });

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
