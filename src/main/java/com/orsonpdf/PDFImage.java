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
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * Represents an image in a PDF document.
 */
public class PDFImage extends Stream {

    /** The width. */
    int width;
    
    /** The height. */
    int height;

    /** The image. */
    Image image;
    
    String softMaskImageRef;
    
    /**
     * Creates a new image object.
     * 
     * @param number  the PDF object number.
     * @param img  the AWT image object ({@code null} not permitted).
     * @param softMaskImageRef  the soft mask image reference ({@code null} 
     *     permitted).
     */
    public PDFImage(int number, Image img, String softMaskImageRef) {
        super(number);
        Args.nullNotPermitted(img, "img");
        this.width = img.getWidth(null);
        this.height = img.getHeight(null);
        this.image = img;
        this.softMaskImageRef = softMaskImageRef;
    }

    /**
     * Returns the raw image data.  Each call will resample the image data
     * and populate a new array.  Note that the stream may encode this
     * data before it is written to the PDF output.
     * 
     * @return The raw stream data. 
     */
    @Override
    public byte[] getRawStreamData() {
        BufferedImage bi;
        if (!(this.image instanceof BufferedImage)) {
            bi = new BufferedImage(this.width, this.height, 
                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = bi.createGraphics();
            g2.setBackground(new Color(255, 255, 255, 0));
            g2.clearRect(0, 0, this.width, this.height);
            g2.drawImage(this.image, 0, 0, null);
        } else {
            bi = (BufferedImage) this.image;
        }
        // create a byte array of the image data to go in the PDF
        byte[] result = new byte[this.width * this.height * 3];
        int i = 0;
        for (int hh = this.height - 1; hh >= 0; hh--) {
            for (int ww = 0; ww < this.width; ww++) {
                int rgb = bi.getRGB(ww, hh);
                result[i++] = (byte) (rgb >> 16);
                result[i++] = (byte) (rgb >> 8);
                result[i++] = (byte) rgb;
            }
        }
        return result;
    }
    
    /**
     * Creates a dictionary reflecting the current configuration for this
     * image.
     * 
     * @param streamLength  the stream length.
     * 
     * @return A dictionary. 
     */
    @Override
    protected Dictionary createDictionary(int streamLength) {
        Dictionary dictionary = super.createDictionary(streamLength);
        dictionary.setType("/XObject");
        dictionary.put("/Subtype", "/Image");
        dictionary.put("/ColorSpace", "/DeviceRGB");
        dictionary.put("/BitsPerComponent", 8);
        dictionary.put("/Width", this.width);
        dictionary.put("/Height", this.height);
        if (this.softMaskImageRef != null) {
            dictionary.put("/SMask", this.softMaskImageRef);
        }
        return dictionary;
    }
}
