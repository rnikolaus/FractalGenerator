package fractal.util;

import java.awt.Color;
import java.util.List;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author rapnik
 */
public abstract class AbstractFractalCalculator extends Thread{
    private final FractalDimensionsBean fractalDimensionsBean;
    private final FractalColorSet fractalColorSet;
    private final FinishCallback finishCallback;
    public interface FinishCallback {
        public void run();
    }

    public AbstractFractalCalculator(FractalDimensionsBean fractalDimensionsBean, FractalColorSet fractalColorSet, FinishCallback finishCallback) {
        this.fractalDimensionsBean = fractalDimensionsBean;
        this.fractalColorSet = fractalColorSet;
        this.finishCallback = finishCallback;
        this.setPriority(MIN_PRIORITY);
    }
    
    protected int[] runFunction(DimXY dim) {
        int x = dim.getX();
        int y = dim.getY();
        FractalResult fractalResult = fractalDimensionsBean.getAbstractFractal().calculate(getComplex(x, y));

        Color col = fractalColorSet.getColor(fractalResult);
        return new int[]{col.getRed(), col.getGreen(), col.getBlue()};

    }
    
    protected void paint(int x,int y,int[] col) {
                            fractalDimensionsBean.getImgData().setPixel(x, y, col);
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
    protected void runCallback() {
        finishCallback.run();
    }
}
