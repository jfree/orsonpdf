/* =====================================================================
 * OrsonPDF : a fast, light-weight PDF library for the Java(tm) platform
 * =====================================================================
 *
 * (C)opyright 2013-2022, by David Gilbert.  All rights reserved.
 *
 * Project Info:  https://github.com/jfree/orsonpdf
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
 * runtime license is available to JFree sponsors:
 *
 * https://github.com/sponsors/jfree
 *
 */

package com.orsonpdf;

/**
 * The PDF function types.  For the {@link PDFGraphics2D} class, only
 * {@code EXPONENTIAL_INTERPOLATION} and {@code STITCHING} are used at present 
 * (to support gradient paint implementations).
 */
public enum FunctionType {

    /** PDF function type 0. */
    SAMPLED(0),
    
    /** PDF function type 2. */
    EXPONENTIAL_INTERPOLATION(2),
    
    /** PDF function type 3. */
    STITCHING(3),
    
    /** PDF function type 4. */
    POSTSCRIPT_CALCULATOR(4);
    
    /** The PDF number for the function type. */
    private int number;
    
    private FunctionType(int number) {
        this.number = number;
    }
    
    /**
     * Returns the PDF number for the function type.
     * 
     * @return The PDF number.
     */
    public int getNumber() {
        return this.number;
    }
}
