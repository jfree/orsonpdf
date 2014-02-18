/* =====================================================================
 * OrsonPDF : a fast, light-weight PDF library for the Java(tm) platform
 * =====================================================================
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.  All rights reserved.
 *
 * Project Info:  http://www.object-refinery.com/orsonpdf/index.html
 * 
 */

package com.orsonpdf;

import java.awt.geom.AffineTransform;
import com.orsonpdf.shading.Shading;
import com.orsonpdf.util.Args;

/**
 * A pattern object (see the concrete subclass {@link ShadingPattern}).
 */
public abstract class Pattern extends PDFObject {
    
    /**
     * A shading pattern object.
     */
    public static final class ShadingPattern extends Pattern {
    
        private Shading shading;
        
        /**
         * Creates a new shading pattern.
         * 
         * @param number  the PDF object number.
         * @param shading  the shading.
         * @param t  the transform from the initial page space to Java2D space.
         */
        public ShadingPattern(int number, Shading shading, AffineTransform t) {
            super(number);
            this.dictionary.put("/PatternType", "2");
            this.dictionary.put("/Matrix", PDFUtils.transformToPDF(t));
            setShading(shading);
        }
        
        /**
         * Returns the shading.
         * 
         * @return The shading (never <code>null</code>). 
         */
        public Shading getShading() {
            return this.shading;
        }
        
        /**
         * Sets the shading.
         * 
         * @param shading  the shading (<code>null</code> not permitted). 
         */
        public void setShading(Shading shading) {
            Args.nullNotPermitted(shading, "shading");
            this.shading = shading;
            this.dictionary.put("/Shading", this.shading);
        }
    }
    
    protected Dictionary dictionary;
    
    /**
     * Creates a new pattern object.
     * 
     * @param number  the PDF object number. 
     */
    protected Pattern(int number) {
        super(number);
        this.dictionary = new Dictionary("/Pattern");
    }

    /**
     * Returns the bytes that go between the 'obj' and 'endobj' in the
     * PDF output for this object.
     * 
     * @return A byte array.
     */
    @Override
    public byte[] getObjectBytes() {
        return this.dictionary.toPDFBytes(); 
    }
}
