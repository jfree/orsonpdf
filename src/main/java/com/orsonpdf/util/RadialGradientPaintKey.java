/* =====================================================================
 * OrsonPDF : a fast, light-weight PDF library for the Java(tm) platform
 * =====================================================================
 * 
 * (C)opyright 2013, by Object Refinery Limited.  All rights reserved.
 *
 * Project Info:  http://www.object-refinery.com/pdf/index.html
 */

package com.orsonpdf.util;

import java.awt.RadialGradientPaint;
import java.util.Arrays;

/**
 * A key.
 */
public class RadialGradientPaintKey {
    
    private RadialGradientPaint paint;
    
    float f = 0.0f;
    
    public RadialGradientPaintKey(RadialGradientPaint mgp) {
        Args.nullNotPermitted(mgp, "mgp");
        this.paint = mgp;
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
