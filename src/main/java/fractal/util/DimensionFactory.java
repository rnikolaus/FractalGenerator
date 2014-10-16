package fractal.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rapnik
 */
public class DimensionFactory {

    private DimensionFactory() {

    }

    public static List<DimXY> getDimensions(int x, int y) {

        return createDimensions(x, y);
    }

    public static List<DimXY> getDimensions(FractalDimensionsBean fractalDimensionsBean) {
        return createDimensions(fractalDimensionsBean.getSizeX(), fractalDimensionsBean.getSizeY());
    }

    public static Iterable<DimXY> getDimensionIterable(FractalDimensionsBean fractalDimensionsBean) {
        return getDimensionIterable(fractalDimensionsBean.getSizeX(), fractalDimensionsBean.getSizeY());
    }

    public static Iterable<DimXY> getDimensionIterable(int x, int y) {
        final int pixelCount = x * y;
        final LinkedBlockingQueue<DimXY> dimensionQueue = new LinkedBlockingQueue<DimXY>() {

            @Override
            public Iterator<DimXY> iterator() {

                return new Iterator<DimXY>() {
                    private int count = 0;

                    @Override
                    public synchronized boolean hasNext() {
                        return count < pixelCount;
                    }

                    @Override
                    public synchronized DimXY next() {
                        DimXY result = null;
                        try {
                            if (hasNext()) {
                                result = take();
                            }
                        } catch (InterruptedException ex) {
                            Logger.getLogger(DimensionFactory.class.getName()).log(Level.INFO, null, ex);
                        }
                        count++;
                        return result;
                    }
                };
            }

        };
        Thread dimensionCreationThread = new Thread(new Runnable() {

            @Override
            public void run() {
                for (int xLocal = 0; xLocal < x; xLocal++) {
                    for (int yLocal = 0; yLocal < y; yLocal++) {
                        dimensionQueue.add(new DimXY(xLocal, yLocal));
                    }
                }
            }
        });
        dimensionCreationThread.start();

        return dimensionQueue;
    }

    private static List<DimXY> createDimensions(int x, int y) {
        final List<DimXY> result = new ArrayList<>();
        for (int xLocal = 0; xLocal < x; xLocal++) {
            for (int yLocal = 0; yLocal < y; yLocal++) {
                result.add(new DimXY(xLocal, yLocal));
            }
        }
        return result;

    }

}
