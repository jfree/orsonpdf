/* =====================================================================
 * OrsonPDF : a fast, light-weight PDF library for the Java(tm) platform
 * =====================================================================
 * 
 * (C)opyright 2013, by Object Refinery Limited.  All rights reserved.
 *
 * Project Info:  http://www.object-refinery.com/pdf/index.html
 */

package com.orsonpdf.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DeflaterOutputStream;

/**
 * Encodes data using Flate.
 */
public class FlateFilter implements Filter {

    /**
     * Default constructor.
     */
    public FlateFilter() {
        
    }
    
    /**
     * Returns the filter type.
     * 
     * @return {@link FilterType#FLATE} 
     */
    @Override
    public FilterType getFilterType() {
        return FilterType.FLATE;
    }

    @Override
    public byte[] encode(byte[] source) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DeflaterOutputStream out = new DeflaterOutputStream(baos);
        try {
            out.write(source);
            out.flush();
            out.close();
        } catch (IOException e) {
            // didn't expect this...
            throw new RuntimeException(e);
        }
        return baos.toByteArray();
    }
    
}
