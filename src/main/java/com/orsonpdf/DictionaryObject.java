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

import com.orsonpdf.util.Args;

/**
 * A PDF object that is represented by a dictionary.  This is used to
 * represent the {@code Catalog} and the {@code Outlines} (the latter 
 * being a placeholder implementation only since we don't presently generate 
 * outlines).
 */
public class DictionaryObject extends PDFObject {
    
    protected Dictionary dictionary;

    /**
     * Creates a new instance.
     * 
     * @param number  the object number.
     * @param type  the object type (for example, "/Catalog"). 
     */
    DictionaryObject(int number, String type) {
        super(number);
        this.dictionary = new Dictionary(type);
    }
    
    /**
     * Puts an item in the dictionary.
     * 
     * @param name  the name (without the leading "/", {@code null} not 
     *     permitted).
     * @param value  the value ({@code null} not permitted).
     */
    public void put(String name, Object value) {
        Args.nullNotPermitted(value, "value");
        this.dictionary.put("/" + name, value);
    }
    
    /**
     * Removes an item from the dictionary.
     * 
     * @param name  the name (without the leading "/").
     * 
     * @return The value that was previously stored with the given name.
     */
    public Object remove(String name) {
        return this.dictionary.remove("/" + name);
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
