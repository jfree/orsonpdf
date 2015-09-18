/* =====================================================================
 * OrsonPDF : a fast, light-weight PDF library for the Java(tm) platform
 * =====================================================================
 * 
 * (C)opyright 2013-2015, by Object Refinery Limited.  All rights reserved.
 *
 * Project Info:  http://www.object-refinery.com/orsonpdf/index.html
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 * 
 * If you do not wish to be bound by the terms of the GPL, an alternative
 * commercial license can be purchased.  For details, please see visit the
 * Orson PDF home page:
 * 
 * http://www.object-refinery.com/orsonpdf/index.html
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
     * @param function  the function object for the shading ({@code null} not 
     *     permitted). 
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
     * Returns a copy of the axis coordinates array {@code (x1, y1, x2, y2)}.
     * 
     * @return A copy of the axis coordinates array (never {@code null}). 
     */
    public double[] getCoords() {
        return this.coords.clone();
    }
        
    /**
     * Sets the axis coordinates array {@code (x1, y1, x2, y2)}.
     * 
     * @param coords  the axis coordinates ({@code null} not permitted).
     */
    public void setCoords(double[] coords) {
        Args.arrayMustHaveLength(4, coords, "coords");
        this.coords = coords.clone();
        this.dictionary.put("/Coords", PDFUtils.toPDFArray(this.coords));
    }
        
    /**
     * Returns the function for this shading.
     * 
     * @return The function (never {@code null}). 
     */
    public Function getFunction() {
        return this.function;
    }
        
    /**
     * Sets the function for this shading.
     * 
     * @param function  the function ({@code null} not permitted). 
     */
    public void setFunction(Function function) {
        Args.nullNotPermitted(function, "function");
        this.function = function;
        this.dictionary.put("/Function", this.function);
    }
        
    /**
     * Returns the function domain.  The default value is {@code [0.0, 1.0]}.
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