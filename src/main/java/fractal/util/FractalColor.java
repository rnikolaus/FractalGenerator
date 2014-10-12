package fractal.util;

import java.awt.Color;

/**
 *
 * @author rapnik
 */
public class FractalColor {
    final double redFactor;
    final double greenFactor;
    final double blueFactor;

    public FractalColor(double redFactor, double greenFactor, double blueFactor) {
        this.redFactor = redFactor;
        this.greenFactor = greenFactor;
        this.blueFactor = blueFactor;
    }
    /**
     * Maps the number of iterations to a Color
     * @param value
     * @return a Color
     */
    public Color getColor(int value){
        return new Color(getInt(value, redFactor), 
                getInt(value, greenFactor), 
                getInt(value, blueFactor));
    }
    private int getInt(int value, double factor){
        return (int)Math.round((factor*value)%256);
    }
}
