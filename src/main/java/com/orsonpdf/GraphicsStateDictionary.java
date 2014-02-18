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

/**
 * A graphics state dictionary.
 */
public class GraphicsStateDictionary extends DictionaryObject {
    
    private float strokeAlpha;
    
    private float nonStrokeAlpha;
    
    /**
     * Creates a new instance with the type <code>/ExtGState</code>.
     * 
     * @param number  the PDF object number.
     */
    public GraphicsStateDictionary(int number) {
        super(number, "/ExtGState");
    }
    
    /**
     * Returns the stroke alpha.
     * 
     * @return The stroke alpha. 
     */
    public float getStrokeAlpha() {
        return this.strokeAlpha;   
    }
    
    /**
     * Sets the stroke alpha.
     * 
     * @param alpha  the stroke alpha. 
     */
    public void setStrokeAlpha(float alpha) {
        this.strokeAlpha = alpha;
        this.dictionary.put("/CA", alpha);
    }
    
    /**
     * Returns the non-stroke alpha.
     * 
     * @return The non-stroke alpha. 
     */
    public float getNonStrokeAlpha() {
        return this.nonStrokeAlpha;   
    }
    
    /**
     * Sets the non-stroke alpha.
     * 
     * @param alpha  the new alpha value. 
     */
    public void setNonStrokeAlpha(float alpha) {
        this.nonStrokeAlpha = alpha;
        this.dictionary.put("/ca", alpha);
    }
   
}
