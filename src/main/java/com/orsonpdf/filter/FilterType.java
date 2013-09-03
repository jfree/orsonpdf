/* =====================================================================
 * OrsonPDF : a fast, light-weight PDF library for the Java(tm) platform
 * =====================================================================
 * 
 * (C)opyright 2013, by Object Refinery Limited.  All rights reserved.
 *
 * Project Info:  http://www.object-refinery.com/pdf/index.html
 */

package com.orsonpdf.filter;

import com.orsonpdf.util.Args;

/**
 * Filter types for PDF streams.
 */
public enum FilterType {
    
    /** ASCII85 encoding. */
    ASCII85("/ASCII85Decode"),
    
    /** Flate encoding. */
    FLATE("/FlateDecode");
    
    private String decode;
    
    /**
     * Creates a new filter type.
     * 
     * @param decode  the decode value. 
     */
    FilterType(String decode) {
        Args.nullNotPermitted(decode, "decode");
        this.decode = decode;
    }
    
    /**
     * Returns the decode value that can be written in the PDF output for
     * this filter type.
     * 
     * @return The decode value (never <code>null</code>). 
     */
    public String getDecode() {
        return this.decode;
    }
}
