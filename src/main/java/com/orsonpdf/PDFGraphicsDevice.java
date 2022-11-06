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

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;

/**
 * A graphics device for use in the {@link PDFGraphicsConfiguration} class.
 */
public class PDFGraphicsDevice extends GraphicsDevice {

    private String id;
    
    GraphicsConfiguration defaultConfig;
    
    /**
     * Creates a new instance.
     * 
     * @param id  the id.
     * @param defaultConfig  the default configuration.
     */
    public PDFGraphicsDevice(String id, GraphicsConfiguration defaultConfig) {
        this.id = id;
        this.defaultConfig = defaultConfig;
    }
    
    /**
     * Returns the device type.  
     * 
     * @return The device type.
     */
    @Override
    public int getType() {
        return GraphicsDevice.TYPE_PRINTER;
    }

    /**
     * Returns the id string.
     * 
     * @return The id string. 
     */
    @Override
    public String getIDstring() {
        return this.id;
    }

    /**
     * Returns all configurations for this device.
     * 
     * @return All configurations for this device.
     */
    @Override
    public GraphicsConfiguration[] getConfigurations() {
        return new GraphicsConfiguration[] { getDefaultConfiguration() };
    }

    /**
     * Returns the default configuration for this device.
     * 
     * @return The default configuration for this device. 
     */
    @Override
    public GraphicsConfiguration getDefaultConfiguration() {
        return this.defaultConfig;
    }
    
}

