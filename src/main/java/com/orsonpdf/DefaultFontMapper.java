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

import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

/**
 * A class that can be used to map AWT/Java2D fonts to PDF built-in font names.
 * This is a very minimal way to support fonts in this 
 * <code>PDFGraphics2D</code> implementation.
 * <br><br>
 * Note that there is an option to draw text as vector graphics which you can
 * specify using the rendering hint {@link PDFHints#KEY_DRAW_STRING_TYPE}.
 * This can be useful, for example, if you need to display characters (such
 * as the euro symbol) that are not supported by the PDF built-in fonts.
 * 
 */
public class DefaultFontMapper implements FontMapper {

    private final Map<FontKey, String> map;
    
    /**
     * Creates a new instance.
     */
    public DefaultFontMapper() {
        this.map = new HashMap<FontKey, String>();
        this.map.put(new FontKey("Dialog", false, false), PDFFont.HELVETICA);
        this.map.put(new FontKey("Dialog", true, false), PDFFont.HELVETICA_BOLD);
        this.map.put(new FontKey("Dialog", false, true), PDFFont.HELVETICA_OBLIQUE);
        this.map.put(new FontKey("Dialog", true, true), PDFFont.HELVETICA_BOLDOBLIQUE);
        this.map.put(new FontKey("Arial", false, false), PDFFont.HELVETICA);
        this.map.put(new FontKey("Arial", true, false), PDFFont.HELVETICA_BOLD);
        this.map.put(new FontKey("Arial", false, true), PDFFont.HELVETICA_OBLIQUE);
        this.map.put(new FontKey("Arial", true, true), PDFFont.HELVETICA_BOLDOBLIQUE);
        this.map.put(new FontKey("Courier", false, false), PDFFont.COURIER);
        this.map.put(new FontKey("Courier", true, false), PDFFont.COURIER_BOLD);
        this.map.put(new FontKey("Courier", false, true), PDFFont.COURIER_ITALIC);
        this.map.put(new FontKey("Courier", true, true), PDFFont.COURIER_BOLDITALIC);
        this.map.put(new FontKey("Courier_New", false, false), PDFFont.COURIER);
        this.map.put(new FontKey("Courier_New", true, false), PDFFont.COURIER_BOLD);
        this.map.put(new FontKey("Courier_New", false, true), PDFFont.COURIER_ITALIC);
        this.map.put(new FontKey("Courier_New", true, true), PDFFont.COURIER_BOLDITALIC);
        this.map.put(new FontKey("DialogInput", false, false), PDFFont.HELVETICA);
        this.map.put(new FontKey("DialogInput", true, false), PDFFont.HELVETICA_BOLD);
        this.map.put(new FontKey("DialogInput", false, true), PDFFont.HELVETICA_OBLIQUE);
        this.map.put(new FontKey("DialogInput", true, true), PDFFont.HELVETICA_BOLDOBLIQUE);
        this.map.put(new FontKey("MgOpen_Cosmetica", false, false), PDFFont.TIMES_ROMAN);
        this.map.put(new FontKey("MgOpen_Cosmetica", true, false), PDFFont.TIMES_BOLD);
        this.map.put(new FontKey("MgOpen_Cosmetica", false, true), PDFFont.TIMES_ITALIC);
        this.map.put(new FontKey("MgOpen_Cosmetica", true, true), PDFFont.TIMES_BOLDITALIC);
        this.map.put(new FontKey("Monospaced", false, false), PDFFont.COURIER);
        this.map.put(new FontKey("Monospaced", true, false), PDFFont.COURIER_BOLD);
        this.map.put(new FontKey("Monospaced", false, true), PDFFont.COURIER_ITALIC);
        this.map.put(new FontKey("Monospaced", true, true), PDFFont.COURIER_BOLDITALIC);
        this.map.put(new FontKey("Palatino", false, false), PDFFont.TIMES_ROMAN);
        this.map.put(new FontKey("Palatino", true, false), PDFFont.TIMES_BOLD);
        this.map.put(new FontKey("Palatino", false, true), PDFFont.TIMES_ITALIC);
        this.map.put(new FontKey("Palatino", true, true), PDFFont.TIMES_BOLDITALIC);
        this.map.put(new FontKey("SansSerif", false, false), PDFFont.HELVETICA);
        this.map.put(new FontKey("SansSerif", true, false), PDFFont.HELVETICA_BOLD);
        this.map.put(new FontKey("SansSerif", false, true), PDFFont.HELVETICA_OBLIQUE);
        this.map.put(new FontKey("SansSerif", true, true), PDFFont.HELVETICA_BOLDOBLIQUE);
        this.map.put(new FontKey("Serif", false, false), PDFFont.TIMES_ROMAN);
        this.map.put(new FontKey("Serif", true, false), PDFFont.TIMES_BOLD);
        this.map.put(new FontKey("Serif", false, true), PDFFont.TIMES_ITALIC);
        this.map.put(new FontKey("Serif", true, true), PDFFont.TIMES_BOLDITALIC);
        this.map.put(new FontKey("Tahoma", false, false), PDFFont.TIMES_ROMAN);
        this.map.put(new FontKey("Tahoma", true, false), PDFFont.TIMES_BOLD);
        this.map.put(new FontKey("Tahoma", false, true), PDFFont.TIMES_ITALIC);
        this.map.put(new FontKey("Tahoma", true, true), PDFFont.TIMES_BOLDITALIC);
        this.map.put(new FontKey("Times_New_Roman", false, false), PDFFont.TIMES_ROMAN);
        this.map.put(new FontKey("Times_New_Roman", true, false), PDFFont.TIMES_BOLD);
        this.map.put(new FontKey("Times_New_Roman", false, true), PDFFont.TIMES_ITALIC);
        this.map.put(new FontKey("Times_New_Roman", true, true), PDFFont.TIMES_BOLDITALIC);
    }
    
    @Override
    public String mapToBaseFont(Font f) {
        String result = this.map.get(FontKey.createFontKey(f));
        if (result == null) {
            result = "Courier";
        }
        return result;
    }
    
}
