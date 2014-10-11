/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fractal.util;

import fractal.fractals.AbstractFractal;
import java.awt.image.WritableRaster;

   public class FractalDimensionsBean {

        final private WritableRaster imgData;
        final private double factor;
        final private double offsetX;
        final private double offsetY;
        final private AbstractFractal abstractFractal;

        public FractalDimensionsBean(double factor, double offsetX, double offsetY,AbstractFractal abstractFractal1,WritableRaster imgData) {
            this.factor = factor;
            this.offsetX = offsetX;
            this.offsetY = offsetY;
            this.abstractFractal = abstractFractal1;
            this.imgData = imgData;
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