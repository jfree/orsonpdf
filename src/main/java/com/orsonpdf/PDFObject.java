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

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * A PDF Object (also referred to as an 'Indirect Object' in the PDF
 * specification).  Objects are identified within a document by their
 * PDF object number (which must be unique within the document) and their
 * PDF object generation number.
 */
public abstract class PDFObject {

    private int number;
    
    private int generation;
    
    /**
     * Creates a new instance with the specified object number and the default
     * generation number (0).
     * 
     * @param number  the object number. 
     */
    protected PDFObject(int number) {
        this(number, 0);
    }
    
    /**
     * Creates a new instance.
     * 
     * @param number  the PDF object number (must be unique within the 
     *     document).
     * @param generation  the PDF generation number.
     */
    protected PDFObject(int number, int generation) {
        this.number = number;
        this.generation = generation;
    }
    
    /**
     * Returns the PDF object number (specified in the constructor).
     * 
     * @return The PDF object number. 
     */
    public int getNumber() {
        return this.number;
    }
    
    /**
     * Returns the PDF object generation number.
     * 
     * @return The PDF object generation number. 
     */
    public int getGeneration() {
        return this.generation;
    }
    
    /**
     * Returns the PDF reference string for this object (for example, 
     * {@code "2 0 R"}).  This is the PDF object number followed by a space 
     * followed by the PDF object generation number, followed by a space and 
     * an {@code 'R'}.
     * 
     * @return The PDF reference string. 
     */
    public String getReference() {
        return this.number + " " + this.generation + " R";
    }
    
    /**
     * Returns the PDF bytes representing this object.
     * 
     * @return The PDF bytes representing this object.
     * 
     * @throws IOException if there is a problem writing to the byte array.
     */
    public byte[] toPDFBytes() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(PDFUtils.toBytes(objectIntroString()));
        baos.write(getObjectBytes());
        baos.write(PDFUtils.toBytes("endobj\n"));
        return baos.toByteArray();
    }

    /**
     * Returns the bytes that go between the 'obj' and 'endobj' in the
     * PDF output for this object.
     * 
     * @return A byte array.
     * @throws IOException if there is a problem writing to the byte array.
     */
    public abstract byte[] getObjectBytes() throws IOException;
    
    private String objectIntroString() {
        StringBuilder b = new StringBuilder();
        b.append(this.number).append(" ").append(this.generation).append(" ");
        b.append("obj\n");
        return b.toString();       
    }
    
}
