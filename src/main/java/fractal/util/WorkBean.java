package fractal.util;

import fractal.fractals.AbstractFractal;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author rapnik
 */
public class WorkBean {

    private final int x;
    private final int y;
    private final FractalDimensionsBean fractalDimensionsBean;
    private FractalResult fractalResult;

    /**
     *
     * @param x
     * @param y
     * @param factor
     * @param offsetX
     * @param offsetY
     * @param sizeX
     * @param sizeY
     * @param strategy
     */
    public WorkBean(int x, int y, double factor, double offsetX, double offsetY, int sizeX, int sizeY,AbstractFractal strategy) {
        this.x = x;
        this.y = y;
        this.fractalDimensionsBean = new FractalDimensionsBean(sizeX, sizeY, factor, offsetX, offsetY,strategy);
    }

    public WorkBean(int x, int y, FractalDimensionsBean fractalDimensionsBean) {
        this.x = x;
        this.y = y;
        this.fractalDimensionsBean = fractalDimensionsBean;
    }

    
    /**
     * @return the dimXY
     */
    public DimXY getDimXY() {
        return new DimXY(x, y);
    }

    /**
     * @return the complex
     */
    public Complex getComplex() {
        double real = fractalDimensionsBean.getFactor() * 
                (x - fractalDimensionsBean.getSizeX() / 2.0) + fractalDimensionsBean.getOffsetX();
        double imaginary = fractalDimensionsBean.getFactor() * 
                (y - fractalDimensionsBean.getSizeY() / 2.0) + fractalDimensionsBean.getOffsetY();
        return new Complex(real, imaginary);
    }
    public void calculate(){
        fractalResult= fractalDimensionsBean.getAbstractFractal().calculate(getComplex());
    }
    public FractalResult getFractalResult(){
        return fractalResult;
    }
    
}
