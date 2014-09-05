/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
    public synchronized Color getColor(FractalResult fractalResult){
        if (!fractalResult.isSolutionFound()){
            return Color.WHITE;
        }
        int num = fractalResult.getResult() %fractalColors.size();
        return fractalColors.get(num).getColor(fractalResult.getIterations());
    }
}
