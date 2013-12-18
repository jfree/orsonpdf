/* =====================================================================
 * OrsonPDF : a fast, light-weight PDF library for the Java(tm) platform
 * =====================================================================
 * 
 * (C)opyright 2013, by Object Refinery Limited.  All rights reserved.
 *
 * Project Info:  http://www.object-refinery.com/pdf/index.html
 */

package com.orsonpdf;

import java.awt.Font;
import com.orsonpdf.util.Args;
import com.orsonpdf.util.ObjectUtils;

/**
 * A key to represent a Java2D font.  This is used to maintain a mapping 
 * between Java2D fonts and PDF fonts.
 */
public class FontKey {
 
    /** The key name. */
    private String name;
    
    /** Flag for bold. */
    private boolean isBold;
    
    /** Flag for italic. */
    private boolean isItalic;
    
    /**
     * Creates a new key for a given font.
     * 
     * @param f  the font (<code>null</code> not permitted).
     * 
     * @return The font key.
     */
    public static FontKey createFontKey(Font f) {
        Args.nullNotPermitted(f, "f");
        String family = f.getFamily().replace(' ', '_');
        boolean bold = f.isBold();
        boolean italic = f.isItalic();
        return new FontKey(family, bold, italic);
    }
    
    /**
     * Creates a new font key.
     * 
     * @param name  the name.
     * @param bold  the bold flag.
     * @param italic  the italic flag.
     * 
     * @see FontKey#createFontKey(java.awt.Font) 
     */
    public FontKey(String name, boolean bold, boolean italic) {
        this.name = name;
        this.isBold = bold;
        this.isItalic = italic;
    }

    /**
     * Tests this key for equality with an arbitrary object.
     * 
     * @param obj  the object to test against (<code>null</code> permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FontKey other = (FontKey) obj;
        if (!ObjectUtils.equals(this.name, other.name)) {
            return false;
        }
        if (this.isBold != other.isBold) {
            return false;
        }
        if (this.isItalic != other.isItalic) {
            return false;
        }
        return true;
    }
    
    /**
     * Returns a hash code for this instance.
     * 
     * @return A hash code. 
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + ObjectUtils.hashCode(this.name);
        hash = 97 * hash + (this.isBold ? 1 : 0);
        hash = 97 * hash + (this.isItalic ? 1 : 0);
        return hash;
    }

}
