/* =====================================================================
 * OrsonPDF : a fast, light-weight PDF library for the Java(tm) platform
 * =====================================================================
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.  All rights reserved.
 *
 * Project Info:  http://www.object-refinery.com/orsonpdf/index.html
 * 
 */

package com.orsonpdf;

import com.orsonpdf.util.Args;

/**
 * A PDF object that is represented by a dictionary.  This is used to
 * represent the <code>Catalog</code> and the <code>Outlines</code> (the latter 
 * being a placeholder implementation only since we don't yet generate 
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
     * @param name  the name (without the leading "/", <code>null</code> not 
     *     permitted).
     * @param value  the value (<code>null</code> not permitted).
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
