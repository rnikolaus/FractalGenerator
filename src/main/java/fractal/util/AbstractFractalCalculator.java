package fractal.util;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author rapnik
 */
public abstract class AbstractFractalCalculator extends Thread {

    protected final FractalDimensionsBean fractalDimensionsBean;
    private final FractalColorSet fractalColorSet;
    private final Runnable finishCallback;

    public AbstractFractalCalculator(FractalDimensionsBean fractalDimensionsBean, FractalColorSet fractalColorSet, Runnable finishCallback) {
        this.fractalDimensionsBean = fractalDimensionsBean;
        this.fractalColorSet = fractalColorSet;
        this.finishCallback = finishCallback;
        this.setPriority(MIN_PRIORITY);
    }

    protected int[] runFunction(DimXY dim) {
        return runFunction(dim.getX(), dim.getY());
    }

    protected int[] runFunction(int[] dim) {
        return runFunction(dim[0], dim[1]);
    }

    protected int[] runFunction(int x, int y) {

        FractalResult fractalResult = fractalDimensionsBean.getAbstractFractal().calculate(getComplex(x, y));

        Color col = fractalColorSet.getColor(fractalResult);
        return new int[]{col.getRed(), col.getGreen(), col.getBlue()};

    }

    protected void paint(int x, int y, int[] col) {
        fractalDimensionsBean.getImgData().setPixel(x, y, col);
    }

    protected void paint(int[] dim, int[] col) {
        int x = dim[0];
        int y = dim[1];
        paint(x, y, col);
    }
    protected void paint(DimXY dim, int[] col) {
        int x = dim.getX();
        int y = dim.getY();
        paint(x, y, col);
    }
    

    private Complex getComplex(int x, int y) {
        double real = fractalDimensionsBean.getFactor()
                * (x - fractalDimensionsBean.getSizeX() / 2.0) + fractalDimensionsBean.getOffsetX();
        double imaginary = fractalDimensionsBean.getFactor()
                * (y - fractalDimensionsBean.getSizeY() / 2.0) + fractalDimensionsBean.getOffsetY();
        return new Complex(real, imaginary);
    }

    protected List<DimXY> getDimensions() {
        return DimensionFactory.getDimensions(fractalDimensionsBean);
    }
    protected Iterator<DimXY> getDimensionProducer(){
        return DimensionFactory.getDimensionProducer(fractalDimensionsBean);
    }

    protected void runCallback() {
        finishCallback.run();
    }
}
