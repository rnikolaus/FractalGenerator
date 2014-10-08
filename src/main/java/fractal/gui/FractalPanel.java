/*
 * The panel that displays the Fractal
 * 
 */
package fractal.gui;

import fractal.fractals.AbstractFractal;
import fractal.util.AbstractFractalCalculator;
import fractal.util.DimXY;
import fractal.util.FractalCalculator;
import fractal.util.FractalCalculatorStreams;
import fractal.util.FractalColor;
import fractal.util.FractalColorSet;
import fractal.util.ResultIterator;
import fractal.util.WorkBean;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;

/**
 *
 * @author rapnik
 */
public class FractalPanel extends javax.swing.JPanel {

    transient private BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    private transient AbstractFractalCalculator fractalCalculator;
    Raster blank = img.getData();
    boolean useLambda;

    double fact, offsetX, offsetY;
//    ExecutorService exs = Executors.newFixedThreadPool(1);
    final private FractalColorSet fractalColorSet = new FractalColorSet();
    Thread drawThread;
    Timer timer = new Timer(50, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            resizedAction();
        }
    });

    private void resizedAction() {
        if (this.fractalCalculator != null) {
            this.fractalCalculator.stop();
        }

        int sizeX = getWidth();
        int sizeY = getHeight();
        img = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_RGB);
        blank = img.getData();
        createFract();
    }
    private boolean running;
    private int workChunkSize = 1;
    private int threadPoolSize = 1;
    private AbstractFractal abstractFractal;

    /**
     * Creates new form FractalPanel
     */
    public FractalPanel() {
        initComponents();
        timer.setRepeats(false);
        resetOffset();
        setFractalCalculator();
        fractalColorSet.addFractalColor(new FractalColor(10, 0, 0));
        fractalColorSet.addFractalColor(new FractalColor(0, 10, 0));
        fractalColorSet.addFractalColor(new FractalColor(0, 0, 10));
        fractalColorSet.addFractalColor(new FractalColor(10, 10, 0));
        fractalColorSet.addFractalColor(new FractalColor(10, 0, 10));
        fractalColorSet.addFractalColor(new FractalColor(0, 10, 10));
        
    }

    public boolean getRunning() {
        return running;
    }

    private void setRunning(boolean running) {
        boolean old = this.running;
        this.running = running;
        firePropertyChange("running", old, running);
    }

    public void setWorkChunkSize(int workChunkSize) {
        this.workChunkSize = workChunkSize;
        setFractalCalculator();
    }

    public int getWorkChunkSize() {
        return workChunkSize;
    }

    public void setThreadPoolSize(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
        setFractalCalculator();
    }

    public int getThreadPoolSize() {
        return threadPoolSize;
    }

    public AbstractFractal getAbstractFractal() {
        return abstractFractal;
    }

    public void setAbstractFractal(AbstractFractal abstractFractal) {
        this.abstractFractal = abstractFractal;
        setFractalCalculator();
    }

    private void setFractalCalculator() {
        if (abstractFractal == null) {
            return;
        }
        AbstractFractalCalculator fc=null;
        if (useLambda){
           fc = new FractalCalculatorStreams(abstractFractal);
        }else{
           fc = new FractalCalculator(abstractFractal, threadPoolSize, workChunkSize);
        }
        setFractalCalculator(fc);
    }

    private void resetOffset() {
        fact = 0.01;
        offsetX = 0;
        offsetY = 0;

    }

    @Override
    public synchronized void paint(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }

    private void rescale(Point p, double amount, boolean zoomIn) {
        offsetX += fact * (p.getX() - this.getWidth() / 2.0);
        offsetY += fact * (p.getY() - this.getHeight() / 2.0);
        if (zoomIn) {
            fact /= amount;
        } else {
            fact *= amount;
        }
        createFract();
    }

    private synchronized void createFract() {
        if (fractalCalculator == null) {
            return;
        }

        int sizeX = this.getWidth();
        int sizeY = this.getHeight();
        if (drawThread != null) {
            drawThread.interrupt();
            try {
                drawThread.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(FractalPanel.class.getName()).log(Level.INFO, null, ex);
            }
        }

        setRunning(true);
        fractalCalculator.createFract(fact, offsetX, offsetY, sizeX, sizeY);
        draw();
    }

    /**
     * Set a new FractalCalculator
     *
     * @param fractalCalculator
     */
    public synchronized void setFractalCalculator(AbstractFractalCalculator fractalCalculator) {
        if (this.fractalCalculator != null) {
            this.fractalCalculator.stop();
        }
        this.fractalCalculator = fractalCalculator;
        resetOffset();
        resized();
    }
    public void setUseLambda(boolean useLambda){
        this.useLambda=useLambda;
        setFractalCalculator();
    }
    public boolean getUseLambda(){
        return useLambda;
    }

    /**
     * Start the drawing process
     *
     */
    private synchronized void draw() {

        img.setData(blank);
        final Iterator<ResultIterator> it = fractalCalculator.iterator();
        drawThread = new Thread() {

            @Override
            public void run() {
                long last = System.currentTimeMillis();
                while (!interrupted() && it.hasNext()) {
                    ResultIterator list = it.next();
                    for (WorkBean rb : list) {
                        if (interrupted()) {
                            break;
                        }
                        DimXY d = rb.getDimXY();
                        img.setRGB(d.getX(), d.getY(), fractalColorSet.getColor(rb.getFractalResult()).getRGB());
//                        img.setRGB(d.getX(), d.getY(), getRgb(rb.getFractalResult()));
                    }
                    long now = System.currentTimeMillis();
                    if (now - last > 100) {
                        repaint();
                        last = now;
                    }
                }
                repaint();
                setRunning(false);
            }

        };

        drawThread.setPriority(Thread.MIN_PRIORITY);
        drawThread.start();

    }
    
    private synchronized void resized() {

        if (timer.isRunning()) {
            timer.restart();
        } else {
            timer.start();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setToolTipText("Click left to zoom in, right to zoom out. The mouse wheel also works");
        setCursor(new java.awt.Cursor(java.awt.Cursor.CROSSHAIR_CURSOR));
        addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                formMouseWheelMoved(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        resized();
    }//GEN-LAST:event_formComponentResized

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        Point p = ((java.awt.event.MouseEvent) evt).getPoint();
        rescale(p, 10.0, (((java.awt.event.MouseEvent) evt).getButton() == MouseEvent.BUTTON1));
        createFract();
    }//GEN-LAST:event_formMouseClicked

    private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved
        Point p = ((java.awt.event.MouseEvent) evt).getPoint();
        double amount = ((java.awt.event.MouseWheelEvent) evt).getScrollAmount() / 2.0;
        rescale(p, amount, (((java.awt.event.MouseWheelEvent) evt).getWheelRotation() == -1));
        createFract();
    }//GEN-LAST:event_formMouseWheelMoved
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
