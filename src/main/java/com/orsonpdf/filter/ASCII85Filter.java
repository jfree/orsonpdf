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

package com.orsonpdf.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * A filter that can encode in ASCII-85 format.
 */
public class ASCII85Filter implements Filter {
    
    /**
     * Default contructor.
     */
    public ASCII85Filter() {   
    }
    
    /**
     * Returns the filter type.
     * 
     * @return {@link FilterType#ASCII85}. 
     */
    @Override
    public FilterType getFilterType() {
        return FilterType.ASCII85;
    }

    @Override
    public byte[] encode(byte[] source) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Ascii85OutputStream out = new Ascii85OutputStream(baos);
        try {
            out.write(source);
            out.flush();
            out.close();
        } catch (IOException e) {
            // didn't expect this...
            throw new RuntimeException(e);
        }
        return baos.toByteArray();
    }
    
}
