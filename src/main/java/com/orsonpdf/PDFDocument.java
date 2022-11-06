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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.orsonpdf.util.Args;

/**
 * Represents a PDF document.  The focus of this implementation is to
 * allow the use of the {@link PDFGraphics2D} class to generate PDF content, 
 * typically in the following manner:
 * <p>
 * <code>PDFDocument pdfDoc = new PDFDocument();<br></code>
 * <code>Page page = pdfDoc.createPage(new Rectangle(612, 468));<br></code>
 * <code>PDFGraphics2D g2 = page.getGraphics2D();<br></code>
 * <code>g2.setPaint(Color.RED);<br></code>
 * <code>g2.draw(new Rectangle(10, 10, 40, 50));<br></code>
 * <code>pdfDoc.writeToFile(new File("demo.pdf"));<br></code>
 * <p>
 * The implementation is light-weight and works very well alongside packages 
 * such as <b>JFreeChart</b> and <b>Orson Charts</b>.
 */
public class PDFDocument {
    
    private static final Logger LOGGER = Logger.getLogger(
            PDFDocument.class.getName());

    /** Producer string. */
    private static final String PRODUCER = "OrsonPDF 1.7";
    
    /** The document catalog. */
    private DictionaryObject catalog;
    
    /** The outlines (placeholder, outline support is not implemented). */
    private DictionaryObject outlines;
    
    /** Document info. */
    private DictionaryObject info;
    
    /** The document title (can be null). */
    private String title;
    
    /** The author of the document (can be null). */
    private String author;
    
    /** The pages of the document. */
    private Pages pages;
    
    /** A list of other objects added to the document. */
    private List<PDFObject> otherObjects;
    
    /** The next PDF object number in the document. */
    private int nextNumber = 1;

    /** 
     * A flag that is used to indicate that we are in DEBUG mode.  In this 
     * mode, the graphics stream for a page does not have a filter applied, so
     * the output can be read in a text editor.
     */
    private boolean debug;

    /**
     * Creates a new {@code PDFDocument}, initially with no content.
     */
    public PDFDocument() {
        this.catalog = new DictionaryObject(this.nextNumber++, "/Catalog");
        this.outlines = new DictionaryObject(this.nextNumber++, "/Outlines");
        this.info = new DictionaryObject(this.nextNumber++, "/Info");
        StringBuilder producer = new StringBuilder("(").append(PRODUCER);
        producer.append(")");
        this.info.put("Producer", producer.toString());
        Date now = new Date();
        String creationDateStr = "(" + PDFUtils.toDateFormat(now) + ")";
        this.info.put("CreationDate", creationDateStr);
        this.info.put("ModDate", creationDateStr);
        this.outlines.put("Count", Integer.valueOf(0));
        this.catalog.put("Outlines", this.outlines);
        this.pages = new Pages(this.nextNumber++, 0, this);
        this.catalog.put("Pages", this.pages);
        this.otherObjects = new ArrayList<PDFObject>();
    }
    
    /**
     * Returns the title for the document.  The default value is {@code null}.
     * 
     * @return The title for the document (possibly {@code null}).
     */
    public String getTitle() {
        return this.title;
    }
    
    /**
     * Sets the title for the document.
     * 
     * @param title  the title ({@code null} permitted).
     */
    public void setTitle(String title) {
        this.title = title;
        if (title != null) {
            this.info.put("Title", "(" + title + ")");                    
        } else {
            this.info.remove("Title");
        }
    }

    /**
     * Returns the author for the document.  The default value is {@code null}.
     * 
     * @return The author for the document (possibly {@code null}).
     */
    public String getAuthor() {
        return this.author;
    }
    
    /**
     * Sets the author for the document.
     * 
     * @param author  the author ({@code null} permitted). 
     */
    public void setAuthor(String author) {
        this.author = author;
        if (author != null) {
            this.info.put("Author", "(" + this.author + ")");                    
        } else {
            this.info.remove("Author");
        }
    }
    
    /**
     * Returns the debug mode flag that controls whether or not the output 
     * stream is filtered.
     * 
     * @return The debug flag.
     * 
     * @since 1.4
     */
    public boolean isDebugMode() {
        return this.debug;
    }
    
    /**
     * Sets the debug MODE flag (this needs to be set before any call to 
     * {@link #createPage(java.awt.geom.Rectangle2D)}).
     * 
     * @param debug  the new flag value.
     * 
     * @since 1.4
     */
    public void setDebugMode(boolean debug) {
        this.debug = debug;
    }

    /**
     * Creates a new {@code Page}, adds it to the document, and returns
     * a reference to the {@code Page}.
     * 
     * @param bounds  the page bounds ({@code null} not permitted).
     * 
     * @return The new page. 
     */
    public Page createPage(Rectangle2D bounds) {
        Page page = new Page(this.nextNumber++, 0, this.pages, bounds, 
                !this.debug);
        this.pages.add(page);
        return page;
    }
    
    /**
     * Adds an object to the document.
     * 
     * @param object  the object ({@code null} not permitted). 
     */
    public void addObject(PDFObject object) {
        Args.nullNotPermitted(object, "object");
        this.otherObjects.add(object);
    }

    /**
     * Returns a new PDF object number and increments the internal counter
     * for the next PDF object number.  This method is used to ensure that
     * all objects in the document are assigned a unique number.
     * 
     * @return A new PDF object number. 
     */
    public int getNextNumber() {
        int result = this.nextNumber;
        this.nextNumber++;
        return result;
    }

    /**
     * Returns a byte array containing the encoding of this PDF document.
     * 
     * @return A byte array containing the encoding of this PDF document. 
     */
    public byte[] getPDFBytes() {
        int[] xref = new int[this.nextNumber];
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            bos.write(toBytes("%PDF-1.4\n"));
            bos.write(new byte[] { (byte) 37, (byte) 128, (byte) 129, 
                (byte) 130, (byte) 131, (byte) 10});
            xref[this.catalog.getNumber() - 1] = bos.size();  // offset to catalog
            bos.write(this.catalog.toPDFBytes());
            xref[this.outlines.getNumber() - 1] = bos.size();  // offset to outlines
            bos.write(this.outlines.toPDFBytes());            
            xref[this.info.getNumber() - 1] = bos.size();  // offset to info
            bos.write(this.info.toPDFBytes());
            xref[this.pages.getNumber() - 1] = bos.size();  // offset to pages
            bos.write(this.pages.toPDFBytes());
            for (Page page : this.pages.getPages()) {
                xref[page.getNumber() - 1] = bos.size();
                bos.write(page.toPDFBytes());
                PDFObject contents = page.getContents();
                xref[contents.getNumber() - 1] = bos.size();
                bos.write(contents.toPDFBytes());
            }
            for (PDFFont font: this.pages.getFonts()) {
                xref[font.getNumber() - 1] = bos.size();
                bos.write(font.toPDFBytes());
            }
            for (PDFObject object: this.otherObjects) {
                xref[object.getNumber() - 1] = bos.size();
                bos.write(object.toPDFBytes());
            }
            xref[xref.length - 1] = bos.size();
            // write the xref table
            bos.write(toBytes("xref\n"));
            bos.write(toBytes("0 " + String.valueOf(this.nextNumber) 
                    + "\n"));
            bos.write(toBytes("0000000000 65535 f \n"));
            for (int i = 0; i < this.nextNumber - 1; i++) {
                String offset = String.valueOf(xref[i]);
                int len = offset.length();
                String offset10 = "0000000000".substring(len) + offset;
                bos.write(toBytes(offset10 + " 00000 n \n"));
            }
  
            // write the trailer
            bos.write(toBytes("trailer\n"));
            Dictionary trailer = new Dictionary();
            trailer.put("/Size", Integer.valueOf(this.nextNumber));
            trailer.put("/Root", this.catalog);
            trailer.put("/Info", this.info);
            bos.write(trailer.toPDFBytes());
            bos.write(toBytes("startxref\n"));
            bos.write(toBytes(String.valueOf(xref[this.nextNumber - 1]) 
                    + "\n"));
            bos.write(toBytes("%%EOF"));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return bos.toByteArray();
    }
    
    /**
     * Writes the PDF document to a file.  This is not a robust method, it
     * exists mainly for the demo output. 
     * 
     * @param f  the file.
     */
    public void writeToFile(File f) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            fos.write(getPDFBytes());
        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }
    }
   
    /**
     * A utility method to convert a string to US-ASCII byte format.
     * 
     * @param s  the string.
     * 
     * @return The corresponding byte array.
     */
    private byte[] toBytes(String s) {
        byte[] result = null;
        try {
            result = s.getBytes("US-ASCII");
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
        return result;
    }

}
