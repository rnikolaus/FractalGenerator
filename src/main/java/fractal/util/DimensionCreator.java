package fractal.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rapnik
 */
public class DimensionCreator {

    private DimensionCreator() {

    }

    public static Collection<DimXY> getDimensions(int x, int y) {

        return createDimensions(x, y, new ArrayList<>());
    }

    public static Collection<DimXY> getDimensions(FractalConfigBean fractalDimensionsBean) {
        return createDimensions(fractalDimensionsBean.getSizeX(), fractalDimensionsBean.getSizeY(),new ArrayList<>());
    }

    public static Iterator<DimXY> getDimensionProducer(FractalConfigBean fractalDimensionsBean) {
        return DimensionCreator.getDimensionProducer(fractalDimensionsBean.getSizeX(), fractalDimensionsBean.getSizeY());
    }

    
    public static Iterator<DimXY> getDimensionProducer(int x, int y) {
        LinkedBlockingQueue<DimXY> dimensionQueue = getProducerQueue(x, y);
        Thread dimensionCreationThread = new Thread(new Runnable() {

            @Override
            public void run() {
                createDimensions(x, y, dimensionQueue);
            }
        });
        dimensionCreationThread.setPriority(Thread.MAX_PRIORITY);
        dimensionCreationThread.start();

        return dimensionQueue.iterator();
    }

    private static LinkedBlockingQueue<DimXY> getProducerQueue(int x, int y) {
        final int pixelCount = x * y;
        final LinkedBlockingQueue<DimXY> dimensionQueue = new LinkedBlockingQueue<DimXY>() {

            @Override
            public Iterator<DimXY> iterator() {

                return new Iterator<DimXY>() {
                    private int count = 0;

                    @Override
                    public  boolean hasNext() {
                        return count < pixelCount;
                    }

                    @Override
                    public  DimXY next() {
                        DimXY result = null;
                        try {
                            if (hasNext()) {
                                result = take();
                            }
                        } catch (InterruptedException ex) {
                            Logger.getLogger(DimensionCreator.class.getName()).log(Level.FINEST, null, ex);
                        }
                        count++;
                        return result;
                    }
                  
                };
            }

        };
        return dimensionQueue;
    }

    /**
     * Fills the supplied Collection and returns it
     * @param x the x boundary 
     * @param y the y boundary
     * @param result the Collection to fill
     * @return result with the dimensions
     */
    private static Collection<DimXY> createDimensions(int x, int y,Collection<DimXY> result) {
        for (int xLocal = 0; xLocal < x; xLocal++) {
            for (int yLocal = 0; yLocal < y; yLocal++) {
                result.add(new DimXY(xLocal, yLocal));
            }
        }
        return result;

    }

}
