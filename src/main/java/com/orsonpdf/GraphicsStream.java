/* =====================================================================
 * OrsonPDF : a fast, light-weight PDF library for the Java(tm) platform
 * =====================================================================
 * 
 * (C)opyright 2013-2020, by Object Refinery Limited.  All rights reserved.
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

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Image;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import com.orsonpdf.util.Args;

/**
 * A {@code Stream} that contains graphics for the PDF document that
 * can be generated via the {@link PDFGraphics2D} class.  The {@link Page}
 * class will create a {@code GraphicsStream} instance to represent its
 * content.  You won't normally interact directly with this class, it is 
 * intended that the {@code PDFGraphics2D} class drives the calls to the 
 * methods of this class.
 */
public class GraphicsStream extends Stream {

    /** 
     * The page the graphics stream belongs to.  We need this reference to
     * our "parent" so that we can access fonts in the document.
     */
    private Page page;
    
    /** The stream content. */
    private ByteArrayOutputStream content;
    
    /** The most recent font applied. */
    private Font font;
    
    /** The most recent alpha transparency value (in the range 0 to 255). */
    private int alpha;
    
    private AffineTransform prevTransInv;
    
    /**
     * The decimal formatter for coordinates of geometrical shapes.
     */
    private DecimalFormat geometryFormat;
    
    /**
     * The decimal formatter for transform matrices.
     */
    private DecimalFormat transformFormat;
    
    /**
     * Creates a new instance.
     * 
     * @param number  the PDF object number.
     * @param page  the parent page ({@code null} not permitted).
     */
    GraphicsStream(int number, Page page) {
        super(number);
        this.page = page;
        this.content = new ByteArrayOutputStream();
        this.font = new Font("Dialog", Font.PLAIN, 12);
        this.alpha = 255;
        // force the formatters to use a '.' for the decimal point
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        this.geometryFormat = new DecimalFormat("0.##", dfs);
        this.transformFormat = new DecimalFormat("0.######", dfs);
    }
    
    private void addContent(String s) {
        try {
            this.content.write(PDFUtils.toBytes(s));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Pushes the current graphics state onto a stack for later retrieval.
     */
    void pushGraphicsState() {
        addContent("q\n");    
    }
    
    /**
     * Pops the graphics state that was previously pushed onto the stack.
     */
    void popGraphicsState() {
        addContent("Q\n");
    }
    
    /**
     * Applies a graphics transform.
     * 
     * @param t  the transform ({@code null} not permitted).
     */
    void applyTransform(AffineTransform t) {
        StringBuilder b = new StringBuilder();
        b.append(transformDP(t.getScaleX())).append(" ");
        b.append(transformDP(t.getShearY())).append(" ");
        b.append(transformDP(t.getShearX())).append(" ");
        b.append(transformDP(t.getScaleY())).append(" ");
        b.append(transformDP(t.getTranslateX())).append(" ");
        b.append(transformDP(t.getTranslateY())).append(" cm\n");
        addContent(b.toString());
    }
    
    /**
     * Sets the transform.
     * 
     * @param t  the transform ({@code null} not permitted). 
     */
    void setTransform(AffineTransform t) {
        AffineTransform tt = new AffineTransform(t);
        try {
            AffineTransform inv = tt.createInverse();
            AffineTransform comb;
            if (this.prevTransInv != null) {
                comb = new AffineTransform(this.prevTransInv);
                comb.concatenate(tt);
            } else {
                comb = tt;
            }
            this.prevTransInv = inv;
            applyTransform(comb);
        } catch (NoninvertibleTransformException e) {
          // do nothing
        }
    }

    /**
     * Applies a text transform.
     * 
     * @param t  the transform ({@code null} not permitted). 
     */
    void applyTextTransform(AffineTransform t) {
        StringBuilder b = new StringBuilder();
        b.append(t.getScaleX()).append(" ");
        b.append(t.getShearY()).append(" ");
        b.append(t.getShearX()).append(" ");
        b.append(t.getScaleY()).append(" ");
        b.append(t.getTranslateX()).append(" ");
        b.append(t.getTranslateY()).append(" Tm\n");
        addContent(b.toString());
    }
    
    /**
     * Applies the specified clip to the current clip.
     * 
     * @param clip  the clip ({@code null} not permitted). 
     */
    void applyClip(Shape clip) {
        Args.nullNotPermitted(clip, "clip");
        StringBuilder b = new StringBuilder();
        Path2D p = new Path2D.Double(clip);
        b.append(getPDFPath(p));
        b.append("W n\n");
        addContent(b.toString());
    }
    
    /**
     * Applies a stroke.  If the stroke is not an instance of 
     * {@code BasicStroke} this method will do nothing.
     * 
     * @param s  the stroke. 
     */
    void applyStroke(Stroke s) {
        if (!(s instanceof BasicStroke)) {
            return;
        }
        BasicStroke bs = (BasicStroke) s;
        StringBuilder b = new StringBuilder();
        b.append(bs.getLineWidth()).append(" ").append("w\n");
        b.append(bs.getEndCap()).append(" J\n");
        b.append(bs.getLineJoin()).append(" j\n");
        float[] dashArray = bs.getDashArray();
        if (dashArray != null) {
            b.append(PDFUtils.toPDFArray(dashArray)).append(" 0 d\n");
        } else {
            b.append("[] 0 d\n");
        }
        addContent(b.toString());
    }
    
    /**
     * Applies a color for stroking.
     * 
     * @param c  the color ({@code null} not permitted). 
     */
    void applyStrokeColor(Color c) {
        float red = c.getRed() / 255f;
        float green = c.getGreen() / 255f;
        float blue = c.getBlue() / 255f;
        StringBuilder b = new StringBuilder();
        b.append(red).append(" ").append(green).append(" ").append(blue)
                .append(" RG\n");
        addContent(b.toString());
        applyAlpha(c.getAlpha());
    }
    
    /**
     * Applies a color for filling.
     * 
     * @param c  the color ({@code null} not permitted).
     */
    void applyFillColor(Color c) {
        float red = c.getRed() / 255f;
        float green = c.getGreen() / 255f;
        float blue = c.getBlue() / 255f;
        StringBuilder b = new StringBuilder();
        b.append(red).append(" ").append(green).append(" ").append(blue)
                .append(" rg\n");
        addContent(b.toString());
        applyAlpha(c.getAlpha());
    }
    
    /**
     * Applies a {@code GradientPaint} for stroking.
     * 
     * @param gp  the gradient paint ({@code null} not permitted). 
     */
    void applyStrokeGradient(GradientPaint gp) {
        // delegate arg checking
        String patternName = this.page.findOrCreatePattern(gp);
        StringBuilder b = new StringBuilder("/Pattern CS\n");
        b.append(patternName).append(" SCN\n");
        addContent(b.toString());
    }
    
    /**
     * Applies a {@code RadialGradientPaint} for stroking.
     * 
     * @param gp  the gradient paint ({@code null} not permitted). 
     */    
    void applyStrokeGradient(RadialGradientPaint rgp) {
        // delegate arg checking
        String patternName = this.page.findOrCreatePattern(rgp);
        StringBuilder b = new StringBuilder("/Pattern CS\n");
        b.append(patternName).append(" SCN\n");
        addContent(b.toString());        
    }

    /**
     * Applies a {@code GradientPaint} for filling.
     * 
     * @param gp  the gradient paint ({@code null} not permitted). 
     */
    void applyFillGradient(GradientPaint gp) {
        // delegate arg checking
        String patternName = this.page.findOrCreatePattern(gp);
        StringBuilder b = new StringBuilder("/Pattern cs\n");
        b.append(patternName).append(" scn\n");
        addContent(b.toString());
    }

    /**
     * Applies a {@code RadialGradientPaint} for filling.
     * 
     * @param gp  the gradient paint ({@code null} not permitted). 
     */
    void applyFillGradient(RadialGradientPaint rgp) {
        // delegate arg checking
        String patternName = this.page.findOrCreatePattern(rgp);
        StringBuilder b = new StringBuilder("/Pattern cs\n");
        b.append(patternName).append(" scn\n");
        addContent(b.toString());
    }

    private float alphaFactor = 1.0f;

    /**
     * Applies the specified alpha composite.
     * 
     * @param alphaComp  the alpha composite ({@code null} permitted). 
     */
    void applyComposite(AlphaComposite alphaComp) {
        if (alphaComp == null) {
            this.alphaFactor = 1.0f;
        } else {
            this.alphaFactor = alphaComp.getAlpha();
            int a = (int) (alphaComp.getAlpha() * 255f);
            if (this.alpha != a) {
                String name = this.page.findOrCreateGSDictionary(a);
                StringBuilder b = new StringBuilder();
                b.append(name).append(" gs\n");
                addContent(b.toString());
                this.alpha = a;
            }
        }
    }
    
    /**
     * Applies the alpha transparency.
     * 
     * @param alpha  the new alpha value (in the range {@code 0} 
     *     to {@code 255}). 
     */
    void applyAlpha(int alpha) {
        int a = (int) (alpha * this.alphaFactor);
        if (this.alpha != a) {
            String name = this.page.findOrCreateGSDictionary(a);
            StringBuilder b = new StringBuilder();
            b.append(name).append(" gs\n");
            addContent(b.toString());
            this.alpha = a;
        }
    }
    
    private String geomDP(double d) {
        if (this.geometryFormat != null) {
            return geometryFormat.format(d);            
        } else {
            return String.valueOf(d);
        }
    }
    
    private String transformDP(double d) {
        if (this.transformFormat != null) {
            return transformFormat.format(d);            
        } else {
            return String.valueOf(d);
        }
    }

    /**
     * Draws the specified line.
     * 
     * @param line  the line ({@code null} not permitted). 
     */
    void drawLine(Line2D line) {
        StringBuilder b = new StringBuilder();
        b.append(geomDP(line.getX1())).append(" ").append(geomDP(line.getY1()))
                .append(" ").append("m\n");
        b.append(geomDP(line.getX2())).append(" ").append(geomDP(line.getY2()))
                .append(" ").append("l\n");
        b.append("S\n");
        addContent(b.toString());
    }
    
    /**
     * Draws the specified path.
     * 
     * @param path  the path ({@code null} not permitted). 
     */
    void drawPath2D(Path2D path) {
        StringBuilder b = new StringBuilder();
        b.append(getPDFPath(path)).append("S\n");
        addContent(b.toString());
    }
    
    /**
     * Fills the specified path.
     * 
     * @param path  the path ({@code null} not permitted).
     */
    void fillPath2D(Path2D path) {
        StringBuilder b = new StringBuilder();
        b.append(getPDFPath(path)).append("f\n");
        addContent(b.toString());
    }
    
    /**
     * Applies the specified font (in fact, no change is made to the stream
     * until the next call 
     * to {@link #drawString(java.lang.String, float, float)}). 
     * 
     * @param font  the font.
     */
    void applyFont(Font font) {
        this.font = font;
    }
    
    /**
     * Draws a string at the specified location.
     * 
     * @param text  the text.
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     */
    void drawString(String text, float x, float y) {
        // we need to get the reference for the current font (creating a 
        // new font object if there isn't already one)
        String fontRef = this.page.findOrCreateFontReference(this.font);
        addContent("BT ");
        AffineTransform t = new AffineTransform(1.0, 0.0, 0.0, -1.0, 0.0, 
                y * 2); 
        applyTextTransform(t);
        StringBuilder b = new StringBuilder();
        b.append(fontRef).append(" ").append(this.font.getSize())
                .append(" Tf ");
        b.append(geomDP(x)).append(" ").append(geomDP(y)).append(" Td (")
                .append(text).append(") Tj ET\n");
        addContent(b.toString());
    }

    /**
     * Draws the specified image into the rectangle {@code (x, y, w, h)}.
     * 
     * @param img  the image.
     * @param x  the x-coordinate of the destination.
     * @param y  the y-coordinate of the destination.
     * @param w  the width of the destination.
     * @param h  the height of the destination.
     */
    void drawImage(Image img, int x, int y, int w, int h) {
        String imageRef = this.page.addImage(img, true);
        StringBuilder b = new StringBuilder();
        b.append("q\n");
        b.append(geomDP(w)).append(" 0 0 ").append(geomDP(h)).append(" ");
        b.append(geomDP(x)).append(" ").append(geomDP(y)).append(" cm\n");
        b.append(imageRef).append(" Do\n");
        b.append("Q\n");
        addContent(b.toString());
    }

    /**
     * A utility method to convert a {@code Path2D} instance to a PDF 
     * path string.
     * 
     * @param path  the path ({@code null} not permitted).
     * 
     * @return The string. 
     */
    private String getPDFPath(Path2D path) {
        StringBuilder b = new StringBuilder();   
        float[] coords = new float[6];
        float lastX = 0;
        float lastY = 0;
        PathIterator iterator = path.getPathIterator(null);
        while (!iterator.isDone()) {
            int type = iterator.currentSegment(coords);
            switch (type) {
            case (PathIterator.SEG_MOVETO):
                b.append(geomDP(coords[0])).append(" ");
                b.append(geomDP(coords[1])).append(" m\n");
                lastX = coords[0];
                lastY = coords[1];
                break;
            case (PathIterator.SEG_LINETO):
                b.append(geomDP(coords[0])).append(" ");
                b.append(geomDP(coords[1])).append(" l\n");                
                lastX = coords[0];
                lastY = coords[1];
                break;
            case (PathIterator.SEG_QUADTO):
                // PDF doesn't support quadratic bezier curves so we need to
                // perform "degree elevation":
                // http://www.cs.mtu.edu/~shene/COURSES/cs3621/NOTES/spline
                //       /Bezier/bezier-elev.html
                /*
                float x0 = 0.25f * lastX + 0.75f * coords[0];
                float y0 = 0.25f * lastY + 0.75f * coords[1];
                float x1 = 0.5f * coords[0] + 0.5f * coords[2];
                float y1 = 0.5f * coords[1] + 0.5f * coords[3];
                */
 
                // PDF doesn't support quadratic Bézier curves; it only supports
                // cubic Bézier curves, so we need to perform "degree elevation"
                // to convert the quadratic Bézier curve into a cubic Bézier
                // curve. This is the only way to preserve the original curve's
                // shape accurately for all arbitrary cases.
                //
                // https://pages.mtu.edu/~shene/COURSES/cs3621/NOTES/spline/Bezier/bezier-elev.html
                //
                // https://en.wikipedia.org/wiki/Bézier_curve#Degree_elevation
                //
                // A quadratic curve has degree 2; a cubic curve has degree 3.
                //
                // The code is simplified to improve performance and accuracy
                final float x0 = ( lastX + ( 2f * coords[0] ) ) / 3f;
                final float y0 = ( lastY + ( 2f * coords[1] ) ) / 3f;
                final float x1 = ( ( 2f * coords[0] ) + coords[2] ) / 3f;
                final float y1 = ( ( 2f * coords[1] ) + coords[3] ) / 3f;
                b.append(geomDP(x0)).append(" ");
                b.append(geomDP(y0)).append(" ");
                b.append(geomDP(x1)).append(" ");
                b.append(geomDP(y1)).append(" ");
                b.append(geomDP(coords[2])).append(" ");
                b.append(geomDP(coords[3])).append(" c\n");
                lastX = coords[2];
                lastY = coords[3];
                break;
            case (PathIterator.SEG_CUBICTO):
                b.append(geomDP(coords[0])).append(" ");
                b.append(geomDP(coords[1])).append(" ");
                b.append(geomDP(coords[2])).append(" ");
                b.append(geomDP(coords[3])).append(" ");
                b.append(geomDP(coords[4])).append(" ");
                b.append(geomDP(coords[5])).append(" c\n");
                lastX = coords[4];
                lastY = coords[5];
                break;
            case (PathIterator.SEG_CLOSE):
                b.append("h\n");
                break;
            default:
                break;
            }
            iterator.next();
        }
        return b.toString();
    }

    @Override
    public byte[] getRawStreamData() {
        return this.content.toByteArray();
    }

}
