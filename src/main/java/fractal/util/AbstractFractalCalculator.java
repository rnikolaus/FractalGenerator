package fractal.util;


import java.util.Collection;
import java.util.Iterator;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author rapnik
 */
public abstract class AbstractFractalCalculator extends Thread {

    protected final FractalConfigBean fractalDimensionsBean;
   
    private final Runnable finishCallback;
   

    public AbstractFractalCalculator(FractalConfigBean fractalDimensionsBean,  Runnable finishCallback) {
        this.fractalDimensionsBean = fractalDimensionsBean;
        
        this.finishCallback = finishCallback;
        this.setPriority(MIN_PRIORITY);
        
    }
    public PixelQueue getPixelQueue(){
        return fractalDimensionsBean.getPixelQueue();
    }

    protected FractalPixel runFunction(DimXY dim) {
        if (isInterrupted()) {
            throw new RuntimeException("Thread was interrupted");
        }
        return runFunction(dim.getX(), dim.getY());
    }

    protected FractalPixel runFunction(int[] dim) {
        return runFunction(dim[0], dim[1]);
    }

    protected FractalPixel runFunction(int x, int y) {
        FractalResult fractalResult = fractalDimensionsBean.getAbstractFractal().calculate(getComplex(x, y));
        FractalPixel result = new FractalPixel(x, y, fractalDimensionsBean.getFractalColorSet().getColors(fractalResult));
        return result;
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

    protected Iterable<DimXY> getDimensionProducer() {
        return DimensionCreator.getDimensionProducer(fractalDimensionsBean);
    }

    protected void runCallback() {
        finishCallback.run();
    }
}
