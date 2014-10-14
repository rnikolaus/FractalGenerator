package fractal.util;

/**
 *
 * @author rapnik
 */
public class StreamsFractalCalculator extends AbstractFractalCalculator {

    public StreamsFractalCalculator(FractalDimensionsBean fractalDimensionsBean, FractalColorSet fractalColorSet, FinishCallback finishCallback) {
        super(fractalDimensionsBean, fractalColorSet, finishCallback);
    }

    @Override
    public void run() {
        getDimensions()
                .stream()
                .parallel()
                .filter((DimXY t) -> !isInterrupted())//don't calculate if parent thread was interrupted
                .forEach((DimXY dim) -> {
                    int[] col = runFunction(dim);
                    if (isInterrupted()) {//don't draw if parent thread was interrupted
                        return;
                    }
                    paint(dim.getX(),dim.getY(),col);

                });
        if (isInterrupted()) {
            return;
        }
        runCallback();

    }

    
}
