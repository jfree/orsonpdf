/* =====================================================================
 * OrsonPDF : a fast, light-weight PDF library for the Java(tm) platform
 * =====================================================================
 * 
 * (C)opyright 2013, by Object Refinery Limited.  All rights reserved.
 *
 * Project Info:  http://www.object-refinery.com/pdf/index.html
 */

package com.orsonpdf;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;

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
     * @return The graphics device (never <code>null</code>).
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
     * @return 
     */
    @Override
    public ColorModel getColorModel() {
        return getColorModel(Transparency.TRANSLUCENT);
    }

    /**
     * Returns the color model for the specified transparency type, or 
     * <code>null</code>.
     * 
     * @param transparency  the transparency type.
     * 
     * @return A color model (possibly <code>null</code>).
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
    
}
