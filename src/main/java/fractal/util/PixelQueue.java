/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractal.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author rapnik
 */
public class PixelQueue {

    public interface RenderPixels {

        public abstract void run(PixelQueue queue);
    }
    private final LinkedBlockingQueue<FractalPixel> queue = new LinkedBlockingQueue<>();
    private final RenderPixels renderPixels;
    private boolean signal;

    public PixelQueue(RenderPixels renderPixels) {
        this.renderPixels = renderPixels;
    }

    public void add(FractalPixel fp) {
        queue.add(fp);
        synchronized (this) {
            if (!queue.isEmpty() && !signal) {
                signal = true;
                renderPixels.run(this);
            }
        }
    }

    public Collection<FractalPixel> getPixels() {
        Collection<FractalPixel> result = new ArrayList<>();
        synchronized (this) {
            this.queue.drainTo(result);
            signal = false;
        }
        return result;
    }
}
