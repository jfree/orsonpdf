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

import java.awt.AWTException;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.ImageCapabilities;
import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.awt.image.VolatileImage;

/**
 * A graphics configuration for the {@link PDFGraphics2D} class.
 */
public class PDFGraphicsConfiguration extends GraphicsConfiguration {

    private GraphicsDevice device;
    
    private int width, height;
    
    /**
     * Creates a new instance.
     * 
     * @param width  the width of the bounds.
     * @param height  the height of the bounds.
     */
    public PDFGraphicsConfiguration(int width, int height) {
      super(); 
      this.width = width;
      this.height = height;
    }
    
    /**
     * Returns the graphics device that this configuration is associated with.
     * 
     * @return The graphics device (never {@code null}).
     */
    @Override
    public GraphicsDevice getDevice() {
        if (this.device == null) {
            this.device = new PDFGraphicsDevice("OrsonPDF-GraphicsDevice", 
                    this);
        }
        return this.device;
    }

    /**
     * Returns the color model for this configuration.
     * 
     * @return The color model.
     */
    @Override
    public ColorModel getColorModel() {
        return getColorModel(Transparency.TRANSLUCENT);
    }

    /**
     * Returns the color model for the specified transparency type, or 
     * {@code null}.
     * 
     * @param transparency  the transparency type.
     * 
     * @return A color model (possibly {@code null}).
     */
    @Override
    public ColorModel getColorModel(int transparency) {
        if (transparency == Transparency.TRANSLUCENT) {
            return ColorModel.getRGBdefault();
        } else if (transparency == Transparency.OPAQUE) {
            return new DirectColorModel(32, 0x00ff0000, 0x0000ff00, 0x000000ff);
        } else {
            return null;
        }
    }

    /**
     * Returns the default transform.
     * 
     * @return The default transform. 
     */
    @Override
    public AffineTransform getDefaultTransform() {
        return new AffineTransform();
    }

    /**
     * Returns the normalizing transform.
     * 
     * @return The normalizing transform. 
     */
    @Override
    public AffineTransform getNormalizingTransform() {
        return new AffineTransform();
    }
    
    /**
     * Returns the bounds for this configuration.
     * 
     * @return The bounds. 
     */
    @Override
    public Rectangle getBounds() {
        return new Rectangle(this.width, this.height);
    }
        
    private BufferedImage img = new BufferedImage(10, 10, 
            BufferedImage.TYPE_INT_ARGB);
    
    @Override
    public VolatileImage createCompatibleVolatileImage(int width, int height, 
            ImageCapabilities caps, int transparency) throws AWTException {
        return img.createGraphics().getDeviceConfiguration()
                .createCompatibleVolatileImage(width, height, caps, 
                        transparency);
    }

}
