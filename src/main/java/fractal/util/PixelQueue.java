
package fractal.util;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author rapnik
 */
public class PixelQueue {

    public interface RenderPixels {

        public abstract void run(PixelQueue queue);
    }
    private final ConcurrentLinkedQueue<FractalPixel> queue = new ConcurrentLinkedQueue<>();
   

    

    public void add(FractalPixel fp) {
        queue.add(fp);


    }
    public synchronized void clear(){
        queue.clear();
    }

    public Iterable<FractalPixel> getPixels() {
        Iterable<FractalPixel> i = new Iterable<FractalPixel>() {

            @Override
            public Iterator<FractalPixel> iterator() {
                return new Iterator<FractalPixel>() {
                    FractalPixel fp = queue.poll();
                    

                    @Override
                    public boolean hasNext() {
                        return fp!=null;
                    }

                    @Override
                    public FractalPixel next() {
                        FractalPixel result = fp;
                        fp = queue.poll();
                        return result;
                    }
                };
                
            }
        };
        return i;
//        Collection<FractalPixel> result = new ArrayList<>();
//            FractalPixel fp;
//            fp=queue.poll();
//            while(fp!=null){
//                result.add(fp);
//                fp = queue.poll();
//            }
//            
//        return result;
    }
}
