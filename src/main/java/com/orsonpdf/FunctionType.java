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
 * The PDF function types.  For the {@link PDFGraphics2D} class, only
 * <code>EXPONENTIAL_INTERPOLATION</code> and <code>STITCHING</code> are used 
 * so far (to support gradient paint implementations).
 */
public enum FunctionType {

    /** PDF function type 0. */
    SAMPLED(0),
    
    /** PDF function type 2. */
    EXPONENTIAL_INTERPOLATION(2),
    
    /** PDF function type 3. */
    STITCHING(3),
    
    /** PDF function type 4. */
    POSTSCRIPT_CALCULATOR(4);
    
    /** The PDF number for the function type. */
    private int number;
    
    private FunctionType(int number) {
        this.number = number;
    }
    
    /**
     * Returns the PDF number for the function type.
     * 
     * @return The PDF number.
     */
    public int getNumber() {
        return this.number;
    }
}
