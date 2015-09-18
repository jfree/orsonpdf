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

package com.orsonpdf.filter;

/**
 * A filter that can be used to encode stream data in PDF output.
 */
public interface Filter {

    /**
     * Returns the filter type.
     * 
     * @return The filter type (never {@code null}). 
     */
    FilterType getFilterType();
    
    /**
     * Apply the encoding to the bytes in {@code source} and return the
     * encoded data in a new array.
     * 
     * @param source  the source ({@code null} not permitted).
     * 
     * @return The encoded bytes.
     */
    byte[] encode(byte[] source);
}
