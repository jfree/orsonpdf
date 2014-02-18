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

/**
 * A {@link PDFObject} representing a PDF font.
 */
public class PDFFont extends PDFObject {
  
    /** Identifier for the standard PDF font 'Helvetica'. */
    public static final String HELVETICA = "Helvetica";

    /** Identifier for the standard PDF font 'Helvetica-Bold'. */
    public static final String HELVETICA_BOLD = "Helvetica-Bold";

    /** Identifier for the standard PDF font 'Helvetica-Oblique'. */
    public static final String HELVETICA_OBLIQUE = "Helvetica-Oblique";
    
    /** Identifier for the standard PDF font 'Helvetica-BoldOblique'. */
    public static final String HELVETICA_BOLDOBLIQUE = "Helvetica-BoldOblique";
    
    /** Identifier for the standard PDF font 'Times-Roman'. */
    public static final String TIMES_ROMAN = "Times-Roman";
    
    /** Identifier for the standard PDF font 'Times-Bold'. */
    public static final String TIMES_BOLD = "Times-Bold";
    
    /** Identifier for the standard PDF font 'Times-Italic'. */
    public static final String TIMES_ITALIC = "Times-Italic";
    
    /** Identifier for the standard PDF font 'Times-BoldItalic'. */
    public static final String TIMES_BOLDITALIC = "Times-BoldItalic";
    
    /** Identifier for the standard PDF font 'Courier'. */
    public static final String COURIER = "Courier";
    
    /** Identifier for the standard PDF font 'Courier-Bold'. */
    public static final String COURIER_BOLD = "Courier-Bold";
    
    /** Identifier for the standard PDF font 'Courier-Italic'. */
    public static final String COURIER_ITALIC = "Courier-Italic";
    
    /** Identifier for the standard PDF font 'Courier-BoldItalic'. */
    public static final String COURIER_BOLDITALIC = "Courier-BoldItalic";
    
    private String name;
    
    /** The BaseFont (for example, "/Helvetica"). */
    private String baseFont;
    
    private String encoding;
    
    /**
     * Creates a new <code>PDFFont</code> instance.
     * 
     * @param number  the PDF object number.
     * @param generation  the PDF object generation number.
     * @param name  the font name within the PDF document.
     * @param baseFont  the base font name.
     * @param encoding  the encoding.
     */
    PDFFont(int number, int generation, String name, String baseFont, 
            String encoding) {
        super(number, generation);
        this.name = name;
        this.baseFont = baseFont;
        this.encoding = encoding;
    }

    /**
     * Returns the name of the font within the PDF document (this is not the
     * same as the font name).
     * 
     * @return The font name. 
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the bytes that go between the 'obj' and 'endobj' in the
     * PDF output for this object.
     * 
     * @return A byte array.
     */
    @Override
    public byte[] getObjectBytes() {
        return createDictionary().toPDFBytes(); 
    }

    private Dictionary createDictionary() {
        Dictionary dictionary = new Dictionary("/Font");
        dictionary.put("/Subtype", "/Type1");
        dictionary.put("/Name", this.name);
        dictionary.put("/BaseFont", this.baseFont);
        dictionary.put("/Encoding", this.encoding);
        return dictionary;        
    }
    
}
