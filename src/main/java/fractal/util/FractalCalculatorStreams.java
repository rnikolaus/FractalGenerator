/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractal.util;

import fractal.fractals.AbstractFractal;
import java.util.Iterator;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 *
 * @author rapnik
 */
public class FractalCalculatorStreams extends AbstractFractalCalculator{
    private final AbstractFractal fractalStrategy;
    Stream<ResultIterator> s;

    public FractalCalculatorStreams(AbstractFractal fractalStrategy) {
        this.fractalStrategy = fractalStrategy;
    }
    
    
    

    @Override
    public Iterator<ResultIterator> iterator() {
        return s.iterator();
    }

    @Override
    public void createFract(double factor, double offsetX, double offsetY, int sizeX, int sizeY) {
        ResultProducer workCache = new ResultProducer(1, 
                fractalStrategy, sizeX, sizeY, factor, offsetX, offsetY);
        workCache.run();
        s=workCache.getStream().map((WorkBean t) -> {
            t.calculate();
            return new ResultIterator(t);
        });
               
    }
    
}
