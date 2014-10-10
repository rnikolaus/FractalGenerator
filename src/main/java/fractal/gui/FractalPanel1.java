/*
 * The panel that displays the Fractal
 * 
 */
package fractal.gui;

import fractal.fractals.AbstractFractal;
import fractal.util.FractalColor;
import fractal.util.FractalColorSet;
import fractal.util.FractalDimensionsBean;
import fractal.util.TestFractalCalculator;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import javax.swing.Timer;

/**
 *
 * @author rapnik
 */
public class FractalPanel1 extends javax.swing.JPanel {

    transient private BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    private transient TestFractalCalculator fractalCalculator;
    Raster blank = img.getData();
    boolean useLambda;

    double fact, offsetX, offsetY;
//    ExecutorService exs = Executors.newFixedThreadPool(1);
    final private FractalColorSet fractalColorSet = new FractalColorSet();

    Timer resizedTimer = new Timer(50, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            resizedAction();
        }
    });
    Timer refreshTimer = new Timer(100, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            repaint();
        }
    });

    private void resizedAction() {

        int sizeX = getWidth();
        int sizeY = getHeight();
        img = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_RGB);
        blank = img.getData();
        createFract();
    }
    private boolean running;

    private int threadPoolSize = 1;
    private AbstractFractal abstractFractal;

    /**
     * Creates new form FractalPanel
     */
    public FractalPanel1() {
        initComponents();
        resizedTimer.setRepeats(false);
        refreshTimer.setRepeats(true);
        refreshTimer.start();
        resetOffset();
        fractalColorSet.addFractalColor(new FractalColor(10, 0, 0));
        fractalColorSet.addFractalColor(new FractalColor(0, 10, 0));
        fractalColorSet.addFractalColor(new FractalColor(0, 0, 10));
        fractalColorSet.addFractalColor(new FractalColor(10, 10, 0));
        fractalColorSet.addFractalColor(new FractalColor(10, 0, 10));
        fractalColorSet.addFractalColor(new FractalColor(0, 10, 10));
        createFract();

    }

    public boolean getRunning() {
        return running;
    }

    private void setRunning(boolean running) {
        boolean old = this.running;
        this.running = running;
        
//        if (running&&!refreshTimer.isRunning()) {
//            refreshTimer.start();
//        } else {
//            refreshTimer.stop();
//        }
//        repaint();
        firePropertyChange("running", old, running);
    }

    public void setThreadPoolSize(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
        createFract();
    }

    public int getThreadPoolSize() {
        return threadPoolSize;
    }

    public AbstractFractal getAbstractFractal() {
        return abstractFractal;
    }

    public void setAbstractFractal(AbstractFractal abstractFractal) {
        this.abstractFractal = abstractFractal;
        createFract();
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
        if (this.fractalCalculator != null) {
            this.fractalCalculator.stop();
        }
        img.setData(blank);
        int sizeX = this.getWidth();
        int sizeY = this.getHeight();

        setRunning(true);
        FractalDimensionsBean frb = new FractalDimensionsBean(sizeX, sizeY, fact, offsetX, offsetY, abstractFractal);

        final TestFractalCalculator fractalCalculatorLocal = new TestFractalCalculator(frb, img.getRaster(), fractalColorSet);
        fractalCalculator = fractalCalculatorLocal;

        Runnable calculation;
        if (useLambda) {
            calculation = new Runnable() {

                @Override
                public void run() {
                    fractalCalculatorLocal.calculate();
                    setRunning(false);
                }

            };
        } else {
            calculation = new Runnable() {

                @Override
                public void run() {
                    fractalCalculatorLocal.calculate(getThreadPoolSize());
                    setRunning(false);
                }

            };

        }
        Thread t = new Thread(calculation);
        t.start();

    }

    public void setUseLambda(boolean useLambda) {
        this.useLambda = useLambda;
        createFract();
    }

    public boolean getUseLambda() {
        return useLambda;
    }

    private synchronized void resized() {

        if (resizedTimer.isRunning()) {
            resizedTimer.restart();
        } else {
            resizedTimer.start();
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
