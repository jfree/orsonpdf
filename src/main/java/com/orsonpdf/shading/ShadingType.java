/* =====================================================================
 * OrsonPDF : a fast, light-weight PDF library for the Java(tm) platform
 * =====================================================================
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.  All rights reserved.
 *
 * Project Info:  http://www.object-refinery.com/orsonpdf/index.html
 * 
 */

package com.orsonpdf.shading;

/**
 * An enumeration of the PDF shading types.
 */
public enum ShadingType {
    
    FUNCTION(1),
    
    /** Axial shading. */
    AXIAL(2),
    
    /** Radial shading. */
    RADIAL(3),
    
    FREE_FORM(4),
    
    LATTICE_FORM(5),
    
    COONS(6),
    
    TENSOR(7);
    
    /** The PDF number for this shading type. */
    private int number;
    
    /**
     * Creates a new shading type.
     * 
     * @param number  the PDF number for the shading type. 
     */
    private ShadingType(int number) {
        this.number = number;
    }
    
    /**
     * Returns the PDF number for the shading type.
     * 
     * @return The PDF number for the shading type. 
     */
    public int getNumber() {
        return this.number;
    }
}
