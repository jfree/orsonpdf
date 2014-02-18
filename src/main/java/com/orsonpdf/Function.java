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

import com.orsonpdf.util.Args;

/**
 * A PDF function object.  For the {@link PDFGraphics2D} class, only
 * <code>EXPONENTIAL_INTERPOLATION</code> and <code>STITCHING</code> are used 
 * so far (to support gradient paint implementations).
 */
public abstract class Function extends PDFObject {
    
    /** The function type. */
    private FunctionType functionType;
    
    /** The function domain. */
    private double[] domain;
    
    /** The function dictionary. */
    protected Dictionary dictionary;
    
    /**
     * Creates a new function.
     *
     * @param number  the PDF object number.
     * @param functionType  the function type.
     */
    protected Function(int number, FunctionType functionType) {
        super(number);
        Args.nullNotPermitted(functionType, "functionType");
        this.functionType = functionType;
        this.domain = new double[] {0.0, 1.0};
        this.dictionary = new Dictionary();
        this.dictionary.put("/Domain", PDFUtils.toPDFArray(this.domain));
        this.dictionary.put("/FunctionType", String.valueOf(
                functionType.getNumber()));
    }

    /**
     * Returns the function type.
     * 
     * @return The function type (never <code>null</code>). 
     */
    public FunctionType getFunctionType() {
        return this.functionType;
    }
    
    /**
     * Returns the function domain.
     * 
     * @return The function domain (never <code>null</code>).
     */
    public double[] getDomain() {
        return this.domain.clone();      
    }
    
    /**
     * Sets the domain for the function.
     * 
     * @param domain  the domain (<code>null</code> not permitted). 
     */
    public void setDomain(double[] domain) {
        Args.nullNotPermitted(domain, "domain");
        this.domain = domain.clone();
        this.dictionary.put("/Domain", PDFUtils.toPDFArray(this.domain));
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
