/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractal.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author rapnik
 */
public class PixelQueue {

    public interface RenderPixels {

        public abstract void run(PixelQueue queue);
    }
    private final ConcurrentLinkedQueue<FractalPixel> queue = new ConcurrentLinkedQueue<>();
    private final RenderPixels renderPixels;
    private final AtomicBoolean signal = new AtomicBoolean(false);

    public PixelQueue(RenderPixels renderPixels) {
        this.renderPixels = renderPixels;
        
    }

    public void add(FractalPixel fp) {
        queue.add(fp);
        if (signal.compareAndSet(false, true)) {
            renderPixels.run(this);
        }

    }

    public Collection<FractalPixel> getPixels() {
        Collection<FractalPixel> result = new ArrayList<>();
            FractalPixel fp;
            fp=queue.poll();
            while(fp!=null){
                result.add(fp);
                fp = queue.poll();
            }
            signal.compareAndSet(true, false); 
        return result;
    }
}
