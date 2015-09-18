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

package com.orsonpdf;

/**
 * A graphics state dictionary.
 */
public class GraphicsStateDictionary extends DictionaryObject {
    
    private float strokeAlpha;
    
    private float nonStrokeAlpha;
    
    /**
     * Creates a new instance with the type {@code /ExtGState}.
     * 
     * @param number  the PDF object number.
     */
    public GraphicsStateDictionary(int number) {
        super(number, "/ExtGState");
    }
    
    /**
     * Returns the stroke alpha (in the range {@code 0.0} to {@code 1.0}).
     * 
     * @return The stroke alpha. 
     */
    public float getStrokeAlpha() {
        return this.strokeAlpha;   
    }
    
    /**
     * Sets the stroke alpha.
     * 
     * @param alpha  the stroke alpha (in the range {@code 0.0} to {@code 1.0}). 
     */
    public void setStrokeAlpha(float alpha) {
        this.strokeAlpha = alpha;
        this.dictionary.put("/CA", alpha);
    }
    
    /**
     * Returns the non-stroke alpha (a value in the range {@code 0.0} to 
     * {@code 1.0}).
     * 
     * @return The non-stroke alpha. 
     */
    public float getNonStrokeAlpha() {
        return this.nonStrokeAlpha;   
    }
    
    /**
     * Sets the non-stroke alpha.
     * 
     * @param alpha  the new alpha value (in the range {@code 0.0} to 
     * {@code 1.0}). 
     */
    public void setNonStrokeAlpha(float alpha) {
        this.nonStrokeAlpha = alpha;
        this.dictionary.put("/ca", alpha);
    }
   
}
