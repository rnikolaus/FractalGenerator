package fractal.util;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 *
 * @author rapnik
 */
public class StreamsFractalCalculator extends AbstractFractalCalculator {

    public StreamsFractalCalculator(FractalConfigBean fractalDimensionsBean,  Runnable finishCallback) {
        super(fractalDimensionsBean, finishCallback);
    }

    @Override
    public void run() {
        final int sizeX = fractalDimensionsBean.getSizeX();
        final int sizeY = fractalDimensionsBean.getSizeY();
        final int pixelCount = sizeX * sizeY;
        Stream<DimXY> dimensionStream = Stream.iterate(new DimXY(0, 0), (DimXY t) -> {
            int x = t.getX();
            int y = t.getY();
            y++;
            if (y == sizeY) {
                y = 0;
                x++;
            }
            return new DimXY(x, y);
        }).limit(pixelCount);
        try {
            dimensionStream.parallel()
                    .forEach((DimXY dim) -> {
                        getPixelQueue().add(runFunction(dim));
                        
                    });

            runCallback();
        } catch (RuntimeException ex) {
            Logger.getLogger(StreamsFractalCalculator.class.getName()).log(Level.FINEST, null, ex);
        }

    }

}
