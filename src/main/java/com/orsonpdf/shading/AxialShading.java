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

import com.orsonpdf.Function;
import com.orsonpdf.PDFUtils;
import com.orsonpdf.util.Args;

/**
 * An axial shading.
 */
public final class AxialShading extends Shading {
       
    /** Coordinates (x0, y0, x1, y1) defining the axis. */
    private double[] coords;
     
    /** The shading function. */
    private Function function;
        
    /** The domain. */
    private double[] domain;
        
    /** The extend flags (two of them). */
    private boolean[] extend;
        
    /**
     * Creates a new axial shading.
     * 
     * @param number  the object number.
     * @param coords  the coordinates of the axis (x1, y1, x2, y2).
     * @param function  the function object for the shading 
     *     (<code>null</code> not permitted). 
     */
    public AxialShading(int number, double[] coords, Function function) {
        super(number, ShadingType.AXIAL);
        Args.arrayMustHaveLength(4, coords, "coords");
        Args.nullNotPermitted(function, "function");
        this.dictionary.put("/ColorSpace", "/DeviceRGB");
        setCoords(coords);
        setFunction(function);
        setExtend(new boolean[] {true, true});
        this.domain = new double[] {0.0, 1.0};
    }

    /**
     * Returns a copy of the axis coordinates array (x1, y1, x2, y2).
     * 
     * @return A copy of the axis coordinates array (never 
     *     <code>null</code>). 
     */
    public double[] getCoords() {
        return this.coords.clone();
    }
        
    /**
     * Sets the axis coordinates array (x1, y1, x2, y2).
     * 
     * @param coords  the axis coordinates (<code>null</code> not 
     *     permitted).
     */
    public void setCoords(double[] coords) {
        Args.arrayMustHaveLength(4, coords, "coords");
        this.coords = coords.clone();
        this.dictionary.put("/Coords", PDFUtils.toPDFArray(this.coords));
    }
        
    /**
     * Returns the function for this shading.
     * 
     * @return The function (never <code>null</code>). 
     */
    public Function getFunction() {
        return this.function;
    }
        
    /**
     * Sets the function for this shading.
     * 
     * @param function  the function (<code>null</code> not permitted). 
     */
    public void setFunction(Function function) {
        Args.nullNotPermitted(function, "function");
        this.function = function;
        this.dictionary.put("/Function", this.function);
    }
        
    /**
     * Returns the function domain.  The default value is 
     * <code>[0.0, 1.0]</code>.
     * 
     * @return The function domain. 
     */
    public double[] getDomain() {
        return this.domain.clone();
    }
        
    /**
     * Sets the domain.
     * 
     * @param domain  the domain (array must have length 2). 
     */
    public void setDomain(double[] domain) {
        Args.arrayMustHaveLength(2, domain, "domain");
        this.domain = domain.clone();
        this.dictionary.put("/Domain", PDFUtils.toPDFArray(this.domain));
    }
        
    /**
     * Returns the extend array. 
     * 
     * @return The extend array. 
     */
    public boolean[] getExtend() {
        return this.extend.clone();
    }
        
    /**
     * Sets the extend array.
     * 
     * @param extend  the extend array (must have length 2). 
     */
    public void setExtend(boolean[] extend) {
        Args.arrayMustHaveLength(2, extend, "extend");
        this.extend = extend.clone();
        this.dictionary.put("/Extend", PDFUtils.toPDFArray(this.extend));
    }
        
}