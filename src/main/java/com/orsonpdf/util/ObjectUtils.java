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

/**
 * Some utility methods.
 */
public class ObjectUtils {
    
    private ObjectUtils() {
        // no need to instantiate
    }
    
    /**
     * Returns <code>true</code> if the objects are equal or both 
     * <code>null</code>, and <code>false</code> otherwise. In Java 7, we 
     * could use the <code>Objects</code> class instead.
     * 
     * @param obj1  object 1.
     * @param obj2  object 2.
     * 
     * @return A boolean. 
     */
    public static boolean equals(Object obj1, Object obj2) {
        if (obj1 == null) {
            return obj2 == null;
        } else {
            return obj1.equals(obj2);
        }
    }
    
    /**
     * Returns the hash code for the object, or 0 if the object is 
     * <code>null</code>.  In Java 7, we could use the <code>Objects</code> 
     * class instead.
     * 
     * @param obj  the object (<code>null</code> permitted).
     * 
     * @return The hash code or 0. 
     */
    public static int hashCode(Object obj) {
        if (obj == null) {
            return 0;
        }
        return obj.hashCode();
    }
}
