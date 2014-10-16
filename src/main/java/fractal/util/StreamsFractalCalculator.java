package fractal.util;

import java.util.function.UnaryOperator;
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
        Stream<int[]> dimensionStream = Stream.iterate(new int[]{0, 0}, (int[] t) -> {
            int x = t[0];
            int y = t[1];
            x++;
            if (x == sizeX) {
                x = 0;
                y++;
            }
            return new int[]{x, y};
        }).limit(pixelCount);

        dimensionStream.parallel()
                .filter((int[] t) -> !isInterrupted())//don't calculate if parent thread was interrupted
                .forEach((int[] dim) -> {
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
