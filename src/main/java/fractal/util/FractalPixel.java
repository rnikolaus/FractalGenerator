/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractal.util;

/**
 *
 * @author rapnik
 */
public class FractalPixel {
    private final int x; 
    private final int y; 
    private final int[] col;
    public FractalPixel(int x, int y, int[] col) {
        this.x = x;
        this.y = y;
        this.col = col;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @return the col
     */
    public int[] getCol() {
        return col;
    }
    
    
    
    
}
