package fractal.util;

import java.awt.Color;
import java.util.Collection;
import java.util.Iterator;
import javax.swing.SwingUtilities;
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
        if (isInterrupted()) {
            throw new RuntimeException("Thread was interrupted");
        }
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
        if (isInterrupted()) {
            throw new RuntimeException("Thread was interrupted");
        }
        SwingUtilities.invokeLater(() -> {
            fractalDimensionsBean.getImgData().setPixel(x, y, col);
        });
        
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

    protected Collection<DimXY> getDimensions() {
        return DimensionCreator.getDimensions(fractalDimensionsBean);
    }

    protected Iterator<DimXY> getDimensionProducer() {
        return DimensionCreator.getDimensionProducer(fractalDimensionsBean);
    }

    protected void runCallback() {
        SwingUtilities.invokeLater(finishCallback);
    }
}
