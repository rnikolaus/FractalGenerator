/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractal.util;

import java.util.Iterator;

/**
 *
 * @author rapnik
 */
public abstract class AbstractFractalCalculator implements Iterable<ResultIterator>{

    @Override
    public abstract Iterator<ResultIterator> iterator() ;
    
    abstract public void createFract(double factor, double offsetX, double offsetY, int sizeX, int sizeY);
    public void stop(){
        
    }
}
