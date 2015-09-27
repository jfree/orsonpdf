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

import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Image;
import java.awt.MultipleGradientPaint;
import java.awt.RadialGradientPaint;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.orsonpdf.Pattern.ShadingPattern;
import com.orsonpdf.filter.FlateFilter;
import com.orsonpdf.shading.AxialShading;
import com.orsonpdf.shading.RadialShading;
import com.orsonpdf.shading.Shading;
import com.orsonpdf.util.Args;
import com.orsonpdf.util.GradientPaintKey;
import com.orsonpdf.util.RadialGradientPaintKey;

/**
 * Represents a page in a {@link PDFDocument}.  Our objective is to be able
 * to write to the page using the {@link PDFGraphics2D} class (see the
 * {@link #getGraphics2D()} method).
 */
public class Page extends PDFObject {
    
    /** The pages of the document. */
    private Pages parent;
 
    /** The page bounds. */
    private Rectangle2D bounds;
    
    /** The page contents. */
    private GraphicsStream contents;
    
    /** The Graphics2D for writing to the page contents. */
    private PDFGraphics2D graphics2d;
    
    /**
     * The list of font (names) used on the page.  We let the parent take
     * care of tracking the font objects.
     */
    private List<String> fontsOnPage;
    
    /**
     * A map between gradient paints and the names used to define the
     * associated pattern in the page resources.
     */
    private Map<GradientPaintKey, String> gradientPaintsOnPage;
    
    private Map<RadialGradientPaintKey, String> radialGradientPaintsOnPage;
    
    /** The pattern dictionary for this page. */
    private Dictionary patterns;
    
    /** The ExtGState dictionary for the page. */
    private Dictionary graphicsStates;
    
    /** 
     * The transform between Page and Java2D coordinates, used in Shading 
     * patterns. 
     */
    private AffineTransform j2DTransform;

    private Dictionary xObjects = new Dictionary();

    /**
     * Creates a new page.
     * 
     * @param number  the PDF object number.
     * @param generation  the PDF object generation number.
     * @param parent  the parent (manages the pages in the {@code PDFDocument}).
     * @param bounds  the page bounds ({@code null} not permitted).
     */
    Page(int number, int generation, Pages parent, Rectangle2D bounds) {
        this(number, generation, parent, bounds, true);
    }
    
    /**
     * Creates a new page.
     * 
     * @param number  the PDF object number.
     * @param generation  the PDF object generation number.
     * @param parent  the parent (manages the pages in the {@code PDFDocument}).
     * @param bounds  the page bounds ({@code null} not permitted).
     * @param filter  a flag that controls whether or not the graphics stream
     *     for the page has a FlateFilter applied.
     * 
     * @since 1.4
     */
    Page(int number, int generation, Pages parent, Rectangle2D bounds, 
            boolean filter) {

        super(number, generation);
        Args.nullNotPermitted(bounds, "bounds");
        this.parent = parent;
        this.bounds = (Rectangle2D) bounds.clone();
        this.fontsOnPage = new ArrayList<String>();
        int n = this.parent.getDocument().getNextNumber();
        this.contents = new GraphicsStream(n, this);
        if (filter) {
            this.contents.addFilter(new FlateFilter());
        }
        this.gradientPaintsOnPage = new HashMap<GradientPaintKey, String>();
        this.radialGradientPaintsOnPage = new HashMap<RadialGradientPaintKey,
                String>();
        this.patterns = new Dictionary();
        this.graphicsStates = new Dictionary();
        
        this.j2DTransform = AffineTransform.getTranslateInstance(0.0, 
                bounds.getHeight());
        this.j2DTransform.concatenate(AffineTransform.getScaleInstance(1.0, 
                -1.0));        
    }

    /**
     * Returns a new rectangle containing the bounds for this page (as supplied
     * to the constructor).
     * 
     * @return The page bounds. 
     */
    public Rectangle2D getBounds() {
        return (Rectangle2D) this.bounds.clone();
    }
    
    /**
     * Returns the {@code PDFObject} that represents the page content.
     * 
     * @return The {@code PDFObject} that represents the page content.
     */
    public PDFObject getContents() {
        return this.contents;
    }
    
    /**
     * Returns the {@link PDFGraphics2D} instance for drawing to the page.
     * 
     * @return The {@code PDFGraphics2D} instance for drawing to the page.
     */
    public PDFGraphics2D getGraphics2D() {
        if (this.graphics2d == null) {
            this.graphics2d = new PDFGraphics2D(this.contents, 
                    (int) this.bounds.getWidth(), 
                    (int) this.bounds.getHeight());
        }
        return this.graphics2d;
    }

    /**
     * Finds the font reference corresponding to the given Java2D font, 
     * creating a new one if there isn't one already.
     * 
     * @param font  the AWT font.
     * 
     * @return The font reference.
     */
    String findOrCreateFontReference(Font font) {
        String ref = this.parent.findOrCreateFontReference(font);
        if (!this.fontsOnPage.contains(ref)) {
            this.fontsOnPage.add(ref);
        }
        return ref;
    }
    
    private Dictionary createFontDictionary() {
        Dictionary d = new Dictionary();
        for (String name : this.fontsOnPage) {
            PDFFont f = this.parent.getFont(name);
            d.put(name, f.getReference());
        }
        return d;
    }
    
    /**
     * Returns the name of the pattern for the specified {@code GradientPaint}, 
     * reusing an existing pattern if possible, otherwise creating a new 
     * pattern if necessary.
     * 
     * @param gp  the gradient ({@code null} not permitted).
     * 
     * @return The pattern name. 
     */
    String findOrCreatePattern(GradientPaint gp) {
        GradientPaintKey key = new GradientPaintKey(gp);
        String patternName = this.gradientPaintsOnPage.get(key);
        if (patternName == null) {
            PDFDocument doc = this.parent.getDocument();
            Function f = new ExponentialInterpolationFunction(
                    doc.getNextNumber(), 
                    gp.getColor1().getRGBColorComponents(null), 
                    gp.getColor2().getRGBColorComponents(null));
            doc.addObject(f);
            double[] coords = new double[4];
            coords[0] = gp.getPoint1().getX();
            coords[1] = gp.getPoint1().getY();
            coords[2] = gp.getPoint2().getX();
            coords[3] = gp.getPoint2().getY();
            Shading s = new AxialShading(doc.getNextNumber(), coords, f);
            doc.addObject(s);
            Pattern p = new ShadingPattern(doc.getNextNumber(), s, 
                    this.j2DTransform);
            doc.addObject(p);
            patternName = "/P" + (this.patterns.size() + 1);
            this.patterns.put(patternName, p);
            this.gradientPaintsOnPage.put(key, patternName);
        }
        return patternName; 
    }
    
    /**
     * Returns the name of the pattern for the specified 
     * {@code RadialGradientPaint}, reusing an existing pattern if 
     * possible, otherwise creating a new pattern if necessary.
     * 
     * @param gp  the gradient ({@code null} not permitted).
     * 
     * @return The pattern name. 
     */
    String findOrCreatePattern(RadialGradientPaint gp) {
        RadialGradientPaintKey key = new RadialGradientPaintKey(gp);
        String patternName = this.radialGradientPaintsOnPage.get(key);
        if (patternName == null) {
            PDFDocument doc = this.parent.getDocument();
            Function f = createFunctionForMultipleGradient(gp);
            doc.addObject(f);
            double[] coords = new double[6];
            coords[0] = gp.getFocusPoint().getX();
            coords[1] = gp.getFocusPoint().getY();
            coords[2] = 0.0;
            coords[3] = gp.getCenterPoint().getX();
            coords[4] = gp.getCenterPoint().getY();
            coords[5] = gp.getRadius();
            Shading s = new RadialShading(doc.getNextNumber(), coords, f);
            doc.addObject(s);
            Pattern p = new ShadingPattern(doc.getNextNumber(), s, 
                    this.j2DTransform);
            doc.addObject(p);
            patternName = "/P" + (this.patterns.size() + 1);
            this.patterns.put(patternName, p);
            this.radialGradientPaintsOnPage.put(key, patternName);
        }
        return patternName; 
    }
    
    private Function createFunctionForMultipleGradient(
            MultipleGradientPaint mgp) {
        PDFDocument doc = this.parent.getDocument();

        if (mgp.getColors().length == 2) {
            Function f = new ExponentialInterpolationFunction(
                    doc.getNextNumber(),
                    mgp.getColors()[0].getRGBColorComponents(null), 
                    mgp.getColors()[1].getRGBColorComponents(null));
            return f;
        } else {
            int count = mgp.getColors().length - 1;
            Function[] functions = new Function[count];
            float[] fbounds = new float[count - 1];
            float[] encode = new float[count * 2];
            for (int i = 0; i < count; i++) {
                // create a linear function for each pair of colors
                functions[i] = new ExponentialInterpolationFunction(
                    doc.getNextNumber(),
                    mgp.getColors()[i].getRGBColorComponents(null), 
                    mgp.getColors()[i + 1].getRGBColorComponents(null));
                doc.addObject(functions[i]);
                if (i < count - 1) {
                    fbounds[i] = mgp.getFractions()[i + 1];
                }
                encode[i * 2] = 0;
                encode[i * 2 + 1] = 1;
            }
            return new StitchingFunction(doc.getNextNumber(), functions, 
                    fbounds, encode);
        }
    }
    
    private Map<Integer, String> alphaDictionaries 
            = new HashMap<Integer, String>();
    
    /**
     * Returns the name of the Graphics State Dictionary that can be used
     * for the specified alpha value - if there is no existing dictionary
     * then a new one is created.
     * 
     * @param alpha  the alpha value in the range 0 to 255.
     * 
     * @return The graphics state dictionary reference. 
     */
    String findOrCreateGSDictionary(int alpha) {
        Integer key = Integer.valueOf(alpha);
        float alphaValue = alpha / 255f;
        String name = this.alphaDictionaries.get(key);
        if (name == null) {
            PDFDocument pdfDoc = this.parent.getDocument();
            GraphicsStateDictionary gsd = new GraphicsStateDictionary(
                    pdfDoc.getNextNumber());
            gsd.setNonStrokeAlpha(alphaValue);
            gsd.setStrokeAlpha(alphaValue);
            pdfDoc.addObject(gsd);
            name = "/GS" + (this.graphicsStates.size() + 1);
            this.graphicsStates.put(name, gsd);
            this.alphaDictionaries.put(key, name);
        }
        return name;
    }

    /**
     * Adds a soft mask image to the page.  This is called from the 
     * {@link #addImage(java.awt.Image)} method to support image transparency.
     * 
     * @param img  the image ({@code null} not permitted).
     * 
     * @return The soft mask image reference.
     */
    String addSoftMaskImage(Image img) {
        Args.nullNotPermitted(img, "img");
        PDFDocument pdfDoc = this.parent.getDocument();
        PDFSoftMaskImage softMaskImage = new PDFSoftMaskImage(
                pdfDoc.getNextNumber(), img);
        softMaskImage.addFilter(new FlateFilter());
        pdfDoc.addObject(softMaskImage);
        String reference = "/Image" + this.xObjects.size();
        this.xObjects.put(reference, softMaskImage);
        return softMaskImage.getReference();
    }
    
    /**
     * Adds an image to the page.  This creates the required PDF object, 
     * as well as adding a reference in the {@code xObjects} resources.
     * You should not call this method directly, it exists for the use of the
     * {@link PDFGraphics2D#drawImage(java.awt.Image, int, int, int, int, java.awt.image.ImageObserver)} 
     * method.
     * 
     * @param img  the image ({@code null} not permitted).
     * 
     * @return The image reference name.
     */
    String addImage(Image img, boolean addSoftMaskImage) {
        Args.nullNotPermitted(img, "img");
        PDFDocument pdfDoc = this.parent.getDocument();
        String softMaskImageRef = null;
        if (addSoftMaskImage) {
            softMaskImageRef = addSoftMaskImage(img);
        }
        PDFImage image = new PDFImage(pdfDoc.getNextNumber(), img, 
                softMaskImageRef);
        image.addFilter(new FlateFilter());
        pdfDoc.addObject(image);
        String reference = "/Image" + this.xObjects.size();
        this.xObjects.put(reference, image);
        return reference;
    }
    
    @Override
    public byte[] getObjectBytes() {
        return createDictionary().toPDFBytes();
    }

    private Dictionary createDictionary() {
        Dictionary dictionary = new Dictionary("/Page");
        dictionary.put("/Parent", this.parent);
        dictionary.put("/MediaBox", this.bounds);
        dictionary.put("/Contents", this.contents);
        Dictionary resources = new Dictionary();
        resources.put("/ProcSet", "[/PDF /Text /ImageB /ImageC /ImageI]");
        if (!this.xObjects.isEmpty()) {
            resources.put("/XObject", this.xObjects);
        }
        if (!this.fontsOnPage.isEmpty()) {
            resources.put("/Font", createFontDictionary());
        }
        if (!this.patterns.isEmpty()) {
            resources.put("/Pattern", this.patterns);
        }
        if (!this.graphicsStates.isEmpty()) {
            resources.put("/ExtGState", this.graphicsStates);
        }        
        dictionary.put("/Resources", resources);
        return dictionary;
    }

}

