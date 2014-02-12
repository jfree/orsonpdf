/* =====================================================================
 * OrsonPDF : a fast, light-weight PDF library for the Java(tm) platform
 * =====================================================================
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.  All rights reserved.
 *
 * Project Info:  http://www.object-refinery.com/pdf/index.html
 */

package com.orsonpdf;

import java.awt.RenderingHints;

/**
 * Defines the rendering hints that can be used with the {@link PDFGraphics2D} 
 * class.  There is just one hint defined for the moment:<br>
 * <ul>
 * <li>{@link #KEY_DRAW_STRING_TYPE} that controls how the drawString() methods
 * generate output (regular text or vector graphics);</li>
 * </ul>
 * 
 * @since 1.5
 */
public final class PDFHints {

    private PDFHints() {
        // no need to instantiate this    
    }
    
    /**
     * The key for the hint that controls whether strings are rendered as
     * characters (standard PDF output) or vector graphics (implemented using 
     * {@link TextLayout}).  The latter will result in larger output files but 
     * permits rendering Unicode characters without font embedding (which is 
     * not supported by <strong>OrsonPDF</code> at this point).  Valid hint 
     * values are {@link #VALUE_DRAW_STRING_TYPE_STANDARD} and 
     * {@link #VALUE_DRAW_STRING_TYPE_VECTOR}.
     */
    public static final PDFHints.Key KEY_DRAW_STRING_TYPE = new PDFHints.Key(0);
    
    /**
     * Hint value for <code>KEY_DRAW_STRING_TYPE</code> to specify that strings
     * should be written to the output using standard PDF text primitives.
     */
    public static final Object VALUE_DRAW_STRING_TYPE_STANDARD 
            = "VALUE_DRAW_STRING_TYPE_STANDARD";
    
    /**
     * Hint value for <code>KEY_DRAW_STRING_TYPE</code> to say that strings
     * should be written to the output using vector graphics primitives.
     */
    public static final Object VALUE_DRAW_STRING_TYPE_VECTOR
            = "VALUE_DRAW_STRING_TYPE_VECTOR";
    
    /**
     * A key for hints used by the {@link PDFGraphics2D} class.
     */
    public static class Key extends RenderingHints.Key {

        public Key(int privateKey) {
            super(privateKey);    
        }
    
        /**
         * Returns <code>true</code> if <code>val</code> is a value that is
         * compatible with this key, and <code>false</code> otherwise.
         * 
         * @param val  the value.
         * 
         * @return A boolean. 
         */
        @Override
        public boolean isCompatibleValue(Object val) {
            switch (intKey()) {
                case 0:
                    return val == null 
                            || VALUE_DRAW_STRING_TYPE_STANDARD.equals(val)
                            || VALUE_DRAW_STRING_TYPE_VECTOR.equals(val);
                default:
                    throw new RuntimeException("Not expected!");
            }
        }
    }
    
}
