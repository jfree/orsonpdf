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

import java.awt.geom.Rectangle2D;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A dictionary is a map and supports writing the bytes for the dictionary
 * in the PDF syntax.  The dictionary has an optional {@code type} entry 
 * which is treated as a special case (to ensure it comes first in the output 
 * if it is specified).
 */
public class Dictionary {
    
    /** 
     * The type entry.  We treat this as a special case, because when a type is
     * defined, we want it to appear first in the PDF output.  Note that it
     * can be set to null for some dictionaries.
     */
    private String type;
    
    /** Data storage. */
    private final Map<String, Object> map;

    /**
     * Creates a new instance with no type.
     */
    public Dictionary() {
        this(null);
    }
    
    /**
     * Creates a new dictionary with the specified type (which can be 
     * {@code null}).
     * 
     * @param type  the type value (for example, "/Catalog").
     */
    public Dictionary(String type) {
        this.type = type;
        this.map = new HashMap<String, Object>();    
    }
    
    /**
     * Returns the dictionary type.
     * 
     * @return The dictionary type (possibly ({@code null}). 
     */
    public String getType() {
        return this.type;
    }
    
    /**
     * Sets the type (for example, "/Catalog").
     * 
     * @param type  the type ({@code null} permitted). 
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns {@code true} if the dictionary has no entries, and 
     * {@code false} otherwise.
     * 
     * @return A boolean.
     * 
     * @see #size() 
     */
    public boolean isEmpty() {
        return this.map.isEmpty();
    }
    
    /**
     * Returns the number of items in the dictionary.
     * 
     * @return The number of items in the dictionary.
     */
    public int size() {
        return this.map.size();
    }
    
    /**
     * Puts an entry in the dictionary.
     * 
     * @param key  the key.
     * @param value  the value.
     */
    public void put(String key, Object value) {
        this.map.put(key, value);
    }
    
    /**
     * Removes an entry from the dictionary, returning the value that was 
     * stored previously.
     * 
     * @param key  the key.
     * 
     * @return The value that was associated with the key. 
     */
    public Object remove(String key) {
        return this.map.remove(key);
    }
    
    /**
     * Returns a byte array containing the ASCII encoding of the dictionary.
     * 
     * @return A byte array. 
     */
    public byte[] toPDFBytes() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            // here we are first creating the String version, then encoding
            // to bytes...it would be more efficient to go direct to bytes
            // but this will do for now
            baos.write(PDFUtils.toBytes(toPDFString()));
        } catch (IOException ex) {
            throw new RuntimeException("Dictionary.toPDFBytes() failed.", ex);
        }
        return baos.toByteArray();
    }

    /**
     * Returns a string containing the PDF text describing the dictionary.
     * Note that this is a Java string, conversion to byte format happens
     * elsewhere.
     * 
     * @return A string.
     */
    public String toPDFString() {
        StringBuilder b = new StringBuilder();
        b.append("<< ");
        if (this.type != null) {
            b.append("/Type ").append(this.type).append("\n");
        }
        // now iterate through the dictionary and write its values
        for (String key : this.map.keySet()) {
            Object value = this.map.get(key);
            if (value instanceof Number || value instanceof String) {
                b.append(key).append(" ");
                b.append(value).append("\n");
            } else if (value instanceof PDFObject) {
                PDFObject pdfObj = (PDFObject) value;
                b.append(key).append(" ");
                b.append(pdfObj.getReference()).append("\n");
            } else if (value instanceof String[]) {
                b.append(key).append(" ");
                String[] array = (String[]) value;
                b.append("[");
                for (int i = 0; i < array.length; i++) {
                    if (i != 0) {
                        b.append(" ");
                    }
                    b.append(array[i]);
                }
                b.append("]\n");                
            } else if (value instanceof PDFObject[]) {
                b.append(key).append(" ");
                PDFObject[] array = (PDFObject[]) value;
                b.append("[");
                for (int i = 0; i < array.length; i++) {
                    if (i != 0) {
                        b.append(" ");
                    }
                    b.append(array[i].getReference());
                }
                b.append("]\n");
            } else if (value instanceof Rectangle2D) {
                Rectangle2D r = (Rectangle2D) value;
                b.append(key).append(" ");
                b.append("[").append(r.getX()).append(" ");
                b.append(r.getY()).append(" ").append(r.getWidth()).append(" ");
                b.append(r.getHeight()).append("]\n");
            } else if (value instanceof Dictionary) {
                b.append(key).append(" ");
                Dictionary d = (Dictionary) value;
                b.append(d.toPDFString());
            } else if (value instanceof float[]) {
                b.append(key).append(" ");
                float[] array = (float[]) value;
                b.append("[");
                for (int i = 0; i < array.length; i++) {
                    if (i != 0) {
                        b.append(" ");
                    }
                    b.append(array[i]);
                }
                b.append("]\n");
                
            } else {
                throw new RuntimeException("Unrecognised value type: " + value);
            }
        }
        b.append(">>\n");
        return b.toString();
    }

}
