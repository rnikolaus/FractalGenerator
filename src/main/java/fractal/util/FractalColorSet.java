package fractal.util;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author rapnik
 */
public class FractalColorSet {
    final private List<FractalColor> fractalColors = new LinkedList<>();
    public void addFractalColor(FractalColor fractalColor){
        fractalColors.add(fractalColor);
    }
    /**
     * This maps a FractalResult to a Color
     * If the maximum iteration depth was exceeded, the color is white
     * @param fractalResult
     * @return a Color for a FractalResult
     */
    public Color getColor(FractalResult fractalResult){
        if (!fractalResult.isSolutionFound()){
            return Color.WHITE;
        }
        int num = fractalResult.getResult() %fractalColors.size();
        return fractalColors.get(num).getColor(fractalResult.getIterations());
    }
    
    public int[] getColors(FractalResult fractalResult){
        Color color = getColor(fractalResult);
        return new int[]{color.getRed(),color.getGreen(),color.getBlue()};
    }
}
