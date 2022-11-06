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
