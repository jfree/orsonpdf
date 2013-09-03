/* =====================================================================
 * OrsonPDF : a fast, light-weight PDF library for the Java(tm) platform
 * =====================================================================
 * 
 * (C)opyright 2013, by Object Refinery Limited.  All rights reserved.
 *
 * Project Info:  http://www.object-refinery.com/pdf/index.html
 */

package com.orsonpdf.filter;

/**
 * A filter that can be used to encode stream data in PDF output.
 */
public interface Filter {

    /**
     * Returns the filter type.
     * 
     * @return The filter type (never <code>null</code>). 
     */
    FilterType getFilterType();
    
    /**
     * Apply the encoding to the bytes in <code>source</code> and return the
     * encoded data in a new array.
     * 
     * @param source  the source (<code>null</code> not permitted).
     * 
     * @return The encoded bytes.
     */
    byte[] encode(byte[] source);
}
