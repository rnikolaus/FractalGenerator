package fractal.util;

import fractal.fractals.AbstractFractal;

   public class FractalConfigBean {

       
        final private double factor;
        final private double offsetX;
        final private double offsetY;
        final private AbstractFractal abstractFractal;
        final private FractalColorSet fractalColorSet;
        final private int width;
        final private int height;
        final private PixelQueue pixelQueue;

        public FractalConfigBean(double factor, double offsetX, double offsetY,FractalColorSet fcs,AbstractFractal abstractFractal1,int width, int height ,PixelQueue pixelqueue) {
            this.factor = factor;
            this.offsetX = offsetX;
            this.offsetY = offsetY;
            this.abstractFractal = abstractFractal1;
            this.pixelQueue = pixelqueue;
            
            this.fractalColorSet = fcs;
            this.width = width;
            this.height = height;
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
        
        
        public int getSizeX(){
            return width;
        }
        public int getSizeY(){
            return height;
        }

    PixelQueue getPixelQueue() {
        return pixelQueue;
    }

    

    }