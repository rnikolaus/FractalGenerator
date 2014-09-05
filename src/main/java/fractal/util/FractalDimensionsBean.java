/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fractal.util;

import fractal.fractals.AbstractFractal;

   public class FractalDimensionsBean {

        final private int sizeX;
        final private int sizeY;
        final private double factor;
        final private double offsetX;
        final private double offsetY;
        final private AbstractFractal abstractFractal;

        public FractalDimensionsBean(int sizeX, int sizeY, double factor, double offsetX, double offsetY,AbstractFractal abstractFractal1) {
            this.sizeX = sizeX;
            this.sizeY = sizeY;
            this.factor = factor;
            this.offsetX = offsetX;
            this.offsetY = offsetY;
            this.abstractFractal = abstractFractal1;
        }

        /**
         * @return the sizeX
         */
        public int getSizeX() {
            return sizeX;
        }

        /**
         * @return the sizeY
         */
        public int getSizeY() {
            return sizeY;
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

    

    }