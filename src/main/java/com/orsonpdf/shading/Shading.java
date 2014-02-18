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

import com.orsonpdf.Dictionary;
import com.orsonpdf.PDFObject;
import com.orsonpdf.util.Args;

/**
 * A PDF shading object (this class is abstract, subclasses implement 
 * particular shading types).
 */
public abstract class Shading extends PDFObject {

    /** The shading type. */
    private ShadingType shadingType;
    
    /** The dictionary. */
    protected Dictionary dictionary;
    
    /**
     * Creates a new shading instance.
     * 
     * @param number  the PDF object number.
     * @param shadingType  the shading type (<code>null</code> not permitted).
     */
    protected Shading(int number, ShadingType shadingType) {
        super(number);
        Args.nullNotPermitted(shadingType, "shadingType");
        this.shadingType = shadingType;
        this.dictionary = new Dictionary();
        this.dictionary.put("/ShadingType", String.valueOf(
                shadingType.getNumber()));
    }
    
    /**
     * Returns the shading type.
     * 
     * @return The shading type (never <code>null</code>). 
     */
    public ShadingType getShadingType() {
        return this.shadingType;
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
