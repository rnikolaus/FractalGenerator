package fractal.util;

import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rapnik
 */
public class DimensionFactory {
   

    private DimensionFactory() {
        
    }
    public static List<DimXY> getDimensions(int x, int y){
        
        return createDimensions(x, y);
    }
    public static List<DimXY> getDimensions(FractalDimensionsBean fractalDimensionsBean){
        return createDimensions(fractalDimensionsBean.getSizeX(), fractalDimensionsBean.getSizeY());
    }
    
    
    
    private static List<DimXY> createDimensions(int x,int y) {
        final List<DimXY> result = new ArrayList<>();
        for (int xLocal = 0; xLocal < x; xLocal ++) {
            for (int yLocal = 0; yLocal < y; yLocal ++) {
                result.add(new DimXY(xLocal, yLocal));
            }
        }
        return result;
        
    }
    
}
