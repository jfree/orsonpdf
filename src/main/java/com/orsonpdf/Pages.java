/* =====================================================================
 * OrsonPDF : a fast, light-weight PDF library for the Java(tm) platform
 * =====================================================================
 * 
 * (C)opyright 2013, by Object Refinery Limited.  All rights reserved.
 *
 * Project Info:  http://www.object-refinery.com/pdf/index.html
 */

package com.orsonpdf;

import com.orsonpdf.util.Args;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A <code>PDFObject</code> that maintains the list of pages for the document.
 * When a {@link PDFDocument} is created, it will create an instance of 
 * <code>Pages</code> and add it to the document catalog.  You won't normally
 * interact directly with this class.
 */
public final class Pages extends PDFObject {
    
    /** The PDF document. */
    private PDFDocument parent;
    
    private List<Page> pages;

    /** The list of font objects used in the document. */
    private List<PDFFont> fonts;
    
    private Map<FontKey, PDFFont> fontMap;
    
    private int nextFont = 1;
    
    private FontMapper fontMapper;
    
    /**
     * Creates a new <code>Pages</code> object.
     * 
     * @param number  the PDF object number.
     * @param generation  the PDF object generation number.
     * @param parent  the PDF document (<code>null</code> not permitted).
     */
    Pages(int number, int generation, PDFDocument parent) {
        super(number, generation);
        Args.nullNotPermitted(parent, "parent");
        this.parent = parent;
        this.pages = new ArrayList<Page>();
        this.fonts = new ArrayList<PDFFont>();
        this.fontMap = new HashMap<FontKey, PDFFont>();
        this.fontMapper = new DefaultFontMapper();
    }
    
    /**
     * Returns the PDF document that the pages belong to.
     * 
     * @return The PDF document (never <code>null</code>). 
     */
    public PDFDocument getDocument() {
        return this.parent;
    }
    
    /**
     * Returns a list of the pages in this object.
     * 
     * @return A list of the pages.
     */
    public List<Page> getPages() {
        return this.pages;
    }
    
    /**
     * Returns a list of fonts used in these pages.
     * 
     * @return A list of fonts.
     */
    public List<PDFFont> getFonts() {
        return this.fonts;
    }
    
    /**
     * Returns the PDF font with the specified name, or <code>null</code> if 
     * there is no font with that name.
     * 
     * @param name  the font name.
     * 
     * @return The PDF font or <code>null</code>. 
     */
    public PDFFont getFont(String name) {
        for (PDFFont f : this.fonts) {
            if (f.getName().equals(name)) {
                return f;
            }
        }
        return null;
    }
    
    /**
     * 
     * @param page 
     */
    void add(Page page) {
        this.pages.add(page);
    }
    
    /**
     * Finds or creates a font reference for the specified AWT font.
     * 
     * @param f  the font (<code>null</code> not permitted).
     * 
     * @return The font reference.
     */
    public String findOrCreateFontReference(Font f) {
        // for now, map all fonts to one of the standard PDF fonts
        FontKey fontKey = FontKey.createFontKey(f);
        PDFFont pdfFont = this.fontMap.get(fontKey);
        if (pdfFont == null) {
            int number = this.parent.getNextNumber();
            String name = "/F" + this.nextFont + "-" + f.getFamily();
            String baseFont = this.fontMapper.mapToBaseFont(f);
            this.nextFont++;
            pdfFont = new PDFFont(number, 0, name, "/" + baseFont, 
                    "/MacRomanEncoding");
            this.fonts.add(pdfFont);
            this.fontMap.put(fontKey, pdfFont);
        }
        return pdfFont.getName();
    }

    private Dictionary createDictionary() {
        Dictionary dictionary = new Dictionary("/Pages");
        Page[] pagesArray = new Page[this.pages.size()];
        for (int i = 0; i < this.pages.size(); i++) {
            pagesArray[i] = this.pages.get(i);
        }
        dictionary.put("/Kids", pagesArray);
        dictionary.put("/Count", Integer.valueOf(pages.size()));
        return dictionary;        
    }

    @Override
    public byte[] getObjectBytes() {
        return createDictionary().toPDFBytes();
    }

}
