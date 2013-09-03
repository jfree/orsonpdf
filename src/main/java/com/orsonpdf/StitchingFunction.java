/* =====================================================================
 * OrsonPDF : a fast, light-weight PDF library for the Java(tm) platform
 * =====================================================================
 * 
 * (C)opyright 2013, by Object Refinery Limited.  All rights reserved.
 *
 * Project Info:  http://www.object-refinery.com/pdf/index.html
 */

package com.orsonpdf;

/**
 * A stitching function.
 */
public final class StitchingFunction extends Function {
    
    private Function[] functions;
    
    private float[] bounds;
    
    private float[] encode;
    
    /**
     * Creates a new stitching function.
     * 
     * @param number  the PDF object number.
     * @param functions  the functions to be stitched.
     * @param bounds  the bounds.
     * @param encode  the encoding.
     */
    public StitchingFunction(int number, Function[] functions, float[] bounds,
            float[] encode) {
        super(number, FunctionType.STITCHING);
        this.functions = functions;
        this.dictionary.put("/Functions", functions);
        this.bounds = bounds;
        this.dictionary.put("/Bounds", bounds);
        this.encode = encode;
        this.dictionary.put("/Encode", encode);
    }

}
