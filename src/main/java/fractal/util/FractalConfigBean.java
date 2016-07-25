package fractal.util;

import fractal.fractals.AbstractFractal;
import java.awt.image.WritableRaster;

   public class FractalConfigBean {

        final private WritableRaster imgData;
        final private double factor;
        final private double offsetX;
        final private double offsetY;
        final private AbstractFractal abstractFractal;
        final private FractalColorSet fractalColorSet;

        public FractalConfigBean(double factor, double offsetX, double offsetY,FractalColorSet fcs,AbstractFractal abstractFractal1,WritableRaster imgData) {
            this.factor = factor;
            this.offsetX = offsetX;
            this.offsetY = offsetY;
            this.abstractFractal = abstractFractal1;
            this.imgData = imgData;
            this.fractalColorSet = fcs;
        }
        
        
        public FractalColorSet getFractalColorSet(){
            return fractalColorSet;
        }


        /**
         * @return the factor
         */
        public double getFactor() {
            return factor;
        }

        /**
         * @return the offsetX
         */
        public double getOffsetX() {
            return offsetX;
        }

        /**
         * @return the offsetY
         */
        public double getOffsetY() {
            return offsetY;
        }
        public AbstractFractal getAbstractFractal(){
            return abstractFractal;
        }
        public WritableRaster getImgData(){
            return imgData;
        }
        
        public int getSizeX(){
            return imgData.getWidth();
        }
        public int getSizeY(){
            return imgData.getHeight();
        }

    

    }