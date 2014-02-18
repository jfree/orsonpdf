/* =====================================================================
 * OrsonPDF : a fast, light-weight PDF library for the Java(tm) platform
 * =====================================================================
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.  All rights reserved.
 *
 * Project Info:  http://www.object-refinery.com/orsonpdf/index.html
 * 
 */

package com.orsonpdf.util;

import java.awt.RadialGradientPaint;
import java.util.Arrays;

/**
 * A wrapper for a <code>RadialGradientPaint</code> that can be used as the key 
 * for a <code>HashMap</code>.  This class is used internally by 
 * <code>PDFGraphics2D</code> to track and re-use gradient definitions.  
 * <code>GradientPaint</code> itself does not implement the equals() and 
 * hashCode() methods, so it doesn't make a good key for a <code>Map</code>.
 */
public class RadialGradientPaintKey {
    
    private RadialGradientPaint paint;
    
    float f = 0.0f;
    
    /**
     * Creates a new key instance.
     * 
     * @param rgp  the radial gradient paint.
     */
    public RadialGradientPaintKey(RadialGradientPaint rgp) {
        Args.nullNotPermitted(rgp, "rgp");
        this.paint = rgp;
    }

    /**
     * Returns the <code>RadialGradientPaint</code> that was supplied to the 
     * constructor.
     * 
     * @return The <code>RadialGradientPaint</code> (never <code>null</code>). 
     */
    public RadialGradientPaint getPaint() {
        return this.paint;
    }
    
    /**
     * Tests this instance for equality with an arbitrary object.
     * 
     * @param obj  the object to test (<code>null</code> permitted).
     * 
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (! (obj instanceof RadialGradientPaint)) {
            return false;
        }
        RadialGradientPaint that = (RadialGradientPaint) obj;
        if (!this.paint.getCenterPoint().equals(that.getCenterPoint())) {
            return false;
        }
        if (!this.paint.getFocusPoint().equals(that.getCenterPoint())) {
            return false;
        }
        if (!Arrays.equals(this.paint.getColors(), that.getColors())) {
            return false;
        }
        if (!Arrays.equals(this.paint.getFractions(), that.getFractions())) {
            return false;
        }
        return true;
    }
    
    /**
     * Returns a hash code for this instance.
     * 
     * @return A hash code. 
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + this.paint.getCenterPoint().hashCode();
        hash = 47 * hash + this.paint.getFocusPoint().hashCode();
        hash = 47 * hash + Float.floatToIntBits(this.paint.getRadius());
        hash = 47 * hash + Arrays.hashCode(this.paint.getColors());
        hash = 47 * hash + Arrays.hashCode(this.paint.getFractions());
        return hash;
    }

}
