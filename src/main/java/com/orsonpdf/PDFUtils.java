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
import java.awt.geom.AffineTransform;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Various utility functions for working with the PDF format.
 */
public class PDFUtils {

    private PDFUtils() {
        // no need to instantiate this
    }

    /**
     * A utility method to convert a boolean[] to a PDF array string.
     * 
     * @param b  the array ({@code null} not permitted).
     * 
     * @return The string. 
     */
    public static String toPDFArray(boolean[] b) {
        Args.nullNotPermitted(b, "b");
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < b.length; i++) {
            if (i != 0) {
                sb.append(" ");
            }
            sb.append(String.valueOf(b[i]));
        }
        return sb.append("]").toString();
    }
    
    /**
     * A utility method to convert a float[] to a PDF array string.
     * 
     * @param f  the array ({@code null} not permitted).
     * 
     * @return The string. 
     */
    public static String toPDFArray(float[] f) {
        Args.nullNotPermitted(f, "f");
        StringBuilder b = new StringBuilder("[");
        for (int i = 0; i < f.length; i++) {
            if (i != 0) {
                b.append(" ");
            }
            b.append(String.valueOf(f[i]));
        }
        return b.append("]").toString();
    }
    
    /**
     * A utility method to convert a double[] to a PDF array string.
     * 
     * @param d  the array ({@code null} not permitted).
     * 
     * @return The string. 
     */
    public static String toPDFArray(double[] d) {
        Args.nullNotPermitted(d, "d");
        StringBuilder b = new StringBuilder("[");
        for (int i = 0; i < d.length; i++) {
            if (i != 0) {
                b.append(" ");
            }
            b.append(String.valueOf(d[i]));
        }
        return b.append("]").toString();
    }

    /**
     * Returns a Java2D AffineTransform in PDF matrix format.
     * 
     * @param t  the transform ({@code null} not permitted).
     * 
     * @return A PDF matrix string.
     */
    public static String transformToPDF(AffineTransform t) {
        Args.nullNotPermitted(t, "t");
        StringBuilder b = new StringBuilder("[");
        b.append(t.getScaleX()).append(" ");
        b.append(t.getShearY()).append(" ");
        b.append(t.getShearX()).append(" ");
        b.append(t.getScaleY()).append(" ");
        b.append(t.getTranslateX()).append(" ");
        b.append(t.getTranslateY());
        return b.append("]").toString();
    }

    /**
     * Returns a string in standard PDF date format representing the specified 
     * date (in the default timezone).
     * 
     * @param date  the date ({@code null} not permitted).
     * 
     * @return A string in standard PDF date format. 
     */
    public static String toDateFormat(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return toPDFDateFormat(c);
    }
    
    /**
     * Returns a string in standard PDF date format representing the date 
     * contained by the specified calendar.
     * 
     * @param calendar  the date and timezone ({@code null} not permitted).
     * 
     * @return A string in standard PDF date format. 
     */
    public static String toPDFDateFormat(Calendar calendar) {
        Date d = calendar.getTime(); 
        DateFormat df1 = new SimpleDateFormat("yyyyMMddHHmmss");
        DateFormat df2 = new SimpleDateFormat("Z");
        String part1 = df1.format(d);
        String part2 = df2.format(d);
        String tzinfo;
        if (part2.equals("z")) {
            tzinfo = "Z00'00'";
        } else {
            tzinfo = part2.substring(0, 3) + "'" + part2.substring(4) + "'";
        }
        return "D:" + part1 + tzinfo;
    }
 
    /**
     * A utility method to convert a string to US-ASCII byte format.
     * 
     * @param s  the string.
     * 
     * @return The corresponding byte array.
     */
    public static byte[] toBytes(String s) {
        byte[] result = null;
        try {
            result = s.getBytes("US-ASCII");
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
        return result;
    }
    
}
