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
 * A utility class that performs checks for method argument validity.
 */
public class Args {

    private Args() {
        // no need to instantiate this
    }
 
    /**
     * Checks that an argument is non-<code>null</code> and throws an 
     * <code>IllegalArgumentException</code> otherwise.
     * 
     * @param obj  the object to check for <code>null</code>.
     * @param ref  the text name for the parameter (to include in the exception
     *     message).
     */
    public static void nullNotPermitted(Object obj, String ref) {
        if (obj == null) {
            throw new IllegalArgumentException("Null '" + ref + "' argument.");
        }
    }
    
    /**
     * Checks an array to ensure it has the correct length and throws an
     * <code>IllegalArgumentException</code> if it does not.
     * 
     * @param length  the required length.
     * @param array  the array to check.
     * @param ref  the text name of the array parameter (to include in the 
     *     exception message).
     */
    public static void arrayMustHaveLength(int length, boolean[] array, 
            String ref) {
        nullNotPermitted(array, "array");
        if (array.length != length) {
            throw new IllegalArgumentException("Array '" + ref 
                    + "' requires length " + length);
        }
    }

    /**
     * Checks an array to ensure it has the correct length and throws an
     * <code>IllegalArgumentException</code> if it does not.
     * 
     * @param length  the required length.
     * @param array  the array to check.
     * @param ref  the text name of the array parameter (to include in the 
     *     exception message).
     */
    public static void arrayMustHaveLength(int length, double[] array, 
            String ref) {
        nullNotPermitted(array, "array");
        if (array.length != length) {
            throw new IllegalArgumentException("Array '" + ref 
                    + "' requires length " + length);
        }
    }
}

