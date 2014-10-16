package fractal.util;

import java.util.stream.Stream;

/**
 *
 * @author rapnik
 */
public class StreamsFractalCalculator extends AbstractFractalCalculator {

    public StreamsFractalCalculator(FractalDimensionsBean fractalDimensionsBean, FractalColorSet fractalColorSet, Runnable finishCallback) {
        super(fractalDimensionsBean, fractalColorSet, finishCallback);
    }

    @Override
    public void run() {
        final int sizeX = fractalDimensionsBean.getSizeX();
        final int sizeY = fractalDimensionsBean.getSizeY();
        final int pixelCount = sizeX * sizeY;
        Stream<DimXY> dimensionStream = Stream.iterate(new DimXY(0,0), (DimXY t) -> {
            int x = t.getX();
            int y = t.getY();
            y++;
            if (y == sizeY) {
                y = 0;
                x++;
            }
            return new DimXY(x,y);
        }).limit(pixelCount);

        dimensionStream.parallel()
                .filter((DimXY t) -> !isInterrupted())//don't calculate if parent thread was interrupted
                .forEach((DimXY dim) -> {
                    int[] col = runFunction(dim);
                    if (isInterrupted()) {//don't draw if parent thread was interrupted
                        return;
                    }
                    paint(dim, col);

                });
        if (isInterrupted()) {
            return;
        }
        runCallback();

    }

}
