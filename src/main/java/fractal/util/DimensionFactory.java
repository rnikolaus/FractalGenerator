/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractal.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rapnik
 */
public class DimensionFactory {
    final int x;
    final int y;
    final List<DimXY> result = new ArrayList<>();

    public DimensionFactory(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public List<DimXY> getDimensions(){
        if (result.isEmpty()){
            createDimensions();
        }
        return result;
    }
    
    
    
    
    private void createDimensions() {
       
        for (int xLocal = 0; xLocal < this.x; xLocal ++) {
            for (int yLocal = 0; yLocal < this.y; yLocal ++) {
                result.add(new DimXY(xLocal, yLocal));
            }
        }
        
    }
    
}
