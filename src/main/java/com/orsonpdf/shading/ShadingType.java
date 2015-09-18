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

/**
 * An enumeration of the PDF shading types.
 */
public enum ShadingType {
    
    FUNCTION(1),
    
    /** Axial shading. */
    AXIAL(2),
    
    /** Radial shading. */
    RADIAL(3),
    
    FREE_FORM(4),
    
    LATTICE_FORM(5),
    
    COONS(6),
    
    TENSOR(7);
    
    /** The PDF number for this shading type. */
    private int number;
    
    /**
     * Creates a new shading type.
     * 
     * @param number  the PDF number for the shading type. 
     */
    private ShadingType(int number) {
        this.number = number;
    }
    
    /**
     * Returns the PDF number for the shading type.
     * 
     * @return The PDF number for the shading type. 
     */
    public int getNumber() {
        return this.number;
    }
}
