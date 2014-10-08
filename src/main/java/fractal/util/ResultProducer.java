package fractal.util;

import fractal.fractals.AbstractFractal;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

/**
 *
 * @author rapnik
 */
public class ResultProducer extends Thread implements Iterable<FractalCalculatorCallable> {

 

    final LinkedBlockingQueue<WorkBean> workList = new LinkedBlockingQueue<WorkBean>();

    final int chunkSize;
    final private FractalDimensionsBean fractalDimensionsBean;
    private final int returnCount;
    private final FractalCalculatorCallableIterator iterator;

    /**
     *
     * @param chunkSize
     * @param fractalStrategy
     * @param sizeX
     * @param sizeY
     * @param factor
     * @param offsetX
     * @param offsetY
     */
    public ResultProducer(int chunkSize, AbstractFractal fractalStrategy, int sizeX, int sizeY, double factor, double offsetX, double offsetY) {
        this.chunkSize = chunkSize;
        this.fractalDimensionsBean = new FractalDimensionsBean(sizeX, sizeY, factor, offsetX, offsetY,fractalStrategy);
        returnCount = sizeX * sizeY;
        iterator = new FractalCalculatorCallableIterator(returnCount, workList, chunkSize);

    }

    private void add(int x, int y) {
        WorkBean wb = new WorkBean(x, y, fractalDimensionsBean);
        workList.add(wb);
    }

    /**
     *
     */
    public void createWorkbeans() {
        int spaceFactor = 3;
        for (int x = 0; x < fractalDimensionsBean.getSizeX(); x += spaceFactor) {
            for (int y = 0; y < fractalDimensionsBean.getSizeY(); y += spaceFactor) {
                add(x, y);
            }
        }
        for (int x = 0; x < fractalDimensionsBean.getSizeX(); x++) {
            for (int y = 0; y < fractalDimensionsBean.getSizeY(); y++) {
                if (y % spaceFactor == 0 && x % spaceFactor == 0) {
                    continue;
                }
                add(x, y);
            }
        }
    }
    
    public Stream<WorkBean> getStream(){
        return workList.stream().parallel();
    }

    @Override
    public FractalCalculatorCallableIterator iterator() {
        return iterator;

    }

    @Override
    public void run() {
        createWorkbeans();
    }

}
