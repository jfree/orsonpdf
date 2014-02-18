/* =====================================================================
 * OrsonPDF : a fast, light-weight PDF library for the Java(tm) platform
 * =====================================================================
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.  All rights reserved.
 *
 * Project Info:  http://www.object-refinery.com/orsonpdf/index.html
 * 
 */

package com.orsonpdf.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * A filter that can encode in ASCII-85 format.
 */
public class ASCII85Filter implements Filter {
    
    /**
     * Default contructor.
     */
    public ASCII85Filter() {   
    }
    
    /**
     * Returns the filter type.
     * 
     * @return {@link FilterType#ASCII85}. 
     */
    @Override
    public FilterType getFilterType() {
        return FilterType.ASCII85;
    }

    @Override
    public byte[] encode(byte[] source) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Ascii85OutputStream out = new Ascii85OutputStream(baos);
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
