/* =====================================================================
 * OrsonPDF : a fast, light-weight PDF library for the Java(tm) platform
 * =====================================================================
 * 
 * (C)opyright 2013, by Object Refinery Limited.  All rights reserved.
 *
 * Project Info:  http://www.object-refinery.com/pdf/index.html
 */

package com.orsonpdf;

import com.orsonpdf.filter.Filter;
import com.orsonpdf.util.Args;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A <code>Stream</code> is a {@link PDFObject} that has a {@link Dictionary} 
 * and a byte stream.  
 */
public abstract class Stream extends PDFObject {
    
    /** Filters (if any) to apply to the stream data. */
    private List<Filter> filters;

    /**
     * Creates a new stream.
     * 
     * @param number  the PDF object number.
     */
    Stream(int number) {
        super(number);
        this.filters = new ArrayList<Filter>();
    }

    /**
     * Adds a filter to the stream.
     * 
     * @param f  the filter (<code>null</code> not permitted).
     * 
     * @see #removeFilters() 
     */
    public void addFilter(Filter f) {
        Args.nullNotPermitted(f, "f");
        this.filters.add(f);    
    }
    
    /**
     * Removes any filters that were previously added.
     * 
     * @see #addFilter(org.jfree.graphics2d.pdf.filter.Filter) 
     */
    public void removeFilters() {
        this.filters.clear();
    }
    
    /**
     * Returns the PDF bytes for this stream object, with all current filters
     * applied.
     * 
     * @return The PDF bytes for this stream object.
     * 
     * @throws IOException 
     */
    @Override
    public byte[] getObjectBytes() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] streamData = getRawStreamData();
        for (Filter f: this.filters) {
            streamData = f.encode(streamData);
        }
        Dictionary dictionary = createDictionary(streamData.length);
        baos.write(dictionary.toPDFBytes());
        baos.write(PDFUtils.toBytes("stream\n"));
        baos.write(streamData);
        baos.write(PDFUtils.toBytes("endstream\n"));
        return baos.toByteArray();
    }

    /**
     * Creates the dictionary for this stream object.  The dictionary will
     * be populated with the stream length and the decode values for any
     * filters that are currently applied.
     * 
     * @param streamLength  the stream length.
     * 
     * @return The dictionary. 
     */
    protected Dictionary createDictionary(int streamLength) {
        Dictionary dictionary = new Dictionary();
        dictionary.put("/Length", Integer.valueOf(streamLength));
        if (!this.filters.isEmpty()) {
            String[] decodes = new String[this.filters.size()];
            int count = this.filters.size();
            for (int i = 0; i < count; i++) {
                Filter f = this.filters.get(count - i - 1);
                decodes[i] = f.getFilterType().getDecode();
            }
            dictionary.put("/Filter", decodes);
        }
        return dictionary;
    }
    
    /**
     * Returns the raw data for the stream.
     * 
     * @return The raw data for the stream. 
     */
    public abstract byte[] getRawStreamData();

}
