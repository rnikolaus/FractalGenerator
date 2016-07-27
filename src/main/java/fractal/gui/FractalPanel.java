/*
 * The panel that displays the Fractal
 * 
 */
package fractal.gui;

import fractal.fractals.AbstractFractal;
import fractal.util.AbstractFractalCalculator;
import fractal.util.FractalColor;
import fractal.util.FractalColorSet;
import fractal.util.FractalConfigBean;
import fractal.util.FractalPixel;
import fractal.util.PixelQueue;
import fractal.util.StreamsFractalCalculator;
import fractal.util.ThreadPoolFractalCalculator;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
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
    private final PixelQueue pixelQueue;

    double fact, offsetX, offsetY;
    final private FractalColorSet fractalColorSet = new FractalColorSet();

    Timer deferredTimer = new Timer(100, (ActionEvent e) -> {
        deferredAction();
    });
    Timer renderTimer = new Timer(50, (ActionEvent e) -> {
        renderPixels();
    });
    

    private void deferredAction() {
        createFract();
    }
    private boolean running;

    private int threadPoolSize = 1;
    private AbstractFractal abstractFractal;

    /**
     * Creates new form FractalPanel
     */
    public FractalPanel() {
        initComponents();
        deferredTimer.setRepeats(false);
        renderTimer.setRepeats(false);
        resetOffset();
        fractalColorSet.addFractalColor(new FractalColor(10, 0, 0));
        fractalColorSet.addFractalColor(new FractalColor(0, 10, 0));
        fractalColorSet.addFractalColor(new FractalColor(0, 0, 10));
        fractalColorSet.addFractalColor(new FractalColor(10, 10, 0));
        fractalColorSet.addFractalColor(new FractalColor(10, 0, 10));
        fractalColorSet.addFractalColor(new FractalColor(0, 10, 10));
        pixelQueue = new PixelQueue();
         

    }

    public boolean isRunning() {
        return running;
    }
    private void renderPixels() {
        for (FractalPixel fp : pixelQueue.getPixels()) {
            img.getRaster().setPixel(fp.getX(), fp.getY(), fp.getCol());
        }
        repaint();
    }

    private void setRunning(boolean running) {
        SwingUtilities.invokeLater(() -> {
            boolean old = this.running;
            this.running = running;
            if (running) {
                renderTimer.setRepeats(true);
                renderTimer.restart();
            } else {
                renderTimer.setRepeats(false);
                renderTimer.stop();
                renderPixels();

            }
        

        firePropertyChange("running", old, running);
        });
    }

    public void setThreadPoolSize(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
        calculateFractalDeferred();
    }

    public int getThreadPoolSize() {
        return threadPoolSize;
    }

    public AbstractFractal getAbstractFractal() {
        return abstractFractal;
    }

    public void setAbstractFractal(AbstractFractal abstractFractal) {
        this.abstractFractal = abstractFractal;
        resetOffset();
        calculateFractalDeferred();
    }

    private void resetOffset() {
        fact = 0.01;
        offsetX = 0;
        offsetY = 0;

    }

    @Override
    public void paint(Graphics g) {
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
        calculateFractalDeferred();
    }

    private void stopCalculation(){
        if (this.fractalCalculator != null) {
            this.fractalCalculator.interrupt();
            try {
                fractalCalculator.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(FractalPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    private synchronized void createFract() {
        stopCalculation();
        img.setData(blank);
        setRunning(true);
        

        FractalConfigBean frb = new FractalConfigBean(fact, offsetX, offsetY ,fractalColorSet, abstractFractal, img.getRaster().getWidth(),img.getRaster().getHeight(),pixelQueue);


        if (useLambda) {
            fractalCalculator = new StreamsFractalCalculator(frb, ()  -> {
            setRunning(false);});
        } else {
            fractalCalculator=new ThreadPoolFractalCalculator(frb,  ()  -> {
            setRunning(false);}, getThreadPoolSize());
        }
        fractalCalculator.start();
    }

    public void setUseLambda(boolean useLambda) {
        this.useLambda = useLambda;
        calculateFractalDeferred();
    }

    public boolean getUseLambda() {
        return useLambda;
    }

    /**
     * All values that are set manually (by resizing the panel, moving sliders,
     * etc.) will wait for at least 50 ms to make the ui more responsive
     */
    private synchronized void calculateFractalDeferred() {

        if (deferredTimer.isRunning()) {
            deferredTimer.restart();
        } else {
            deferredTimer.start();
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
        int sizeX = getWidth();
        int sizeY = getHeight();
        pixelQueue.clear();
        img = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_RGB);
        blank = img.getData();
       
        calculateFractalDeferred();
    }//GEN-LAST:event_formComponentResized

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        stopCalculation();
        Point p = ((java.awt.event.MouseEvent) evt).getPoint();
        rescale(p, 10.0, (((java.awt.event.MouseEvent) evt).getButton() == MouseEvent.BUTTON1));
        calculateFractalDeferred();
    }//GEN-LAST:event_formMouseClicked

    private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved
        stopCalculation();
        Point p = ((java.awt.event.MouseEvent) evt).getPoint();
        double amount = ((java.awt.event.MouseWheelEvent) evt).getScrollAmount() / 2.0;
        rescale(p, amount, (((java.awt.event.MouseWheelEvent) evt).getWheelRotation() == -1));
        calculateFractalDeferred();
    }//GEN-LAST:event_formMouseWheelMoved
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
