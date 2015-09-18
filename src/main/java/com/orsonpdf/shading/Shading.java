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
     * @param shadingType  the shading type ({@code null} not permitted).
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
     * @return The shading type (never {@code null}). 
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
