/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.orsonpdf.demo;

import com.orsonpdf.PDFDocument;
import com.orsonpdf.PDFGraphics2D;
import com.orsonpdf.Page;
import com.orsonpdf.util.TextAnchor;
import com.orsonpdf.util.TextUtils;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.MultipleGradientPaint;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.io.File;
import java.io.IOException;

/**
 * A test for PDF output.
 */
public class PDFTest {
 
    private static void drawTextUtilsTest(Graphics2D g2) {
        g2.setPaint(Color.RED);
        g2.setFont(new Font("Dialog", Font.PLAIN, 14));
        TextUtils.drawAlignedString("Hello World", g2, 100f, 10f, TextAnchor.CENTER);
        TextUtils.drawAlignedString("Hello World", g2, 100f, 40f, TextAnchor.CENTER_LEFT);
        TextUtils.drawAlignedString("Hello World", g2, 100f, 70f, TextAnchor.CENTER_RIGHT);
    }
    
    private static void drawClipTest(Graphics2D g2) {
        g2.translate(10, 20);
        g2.setColor(Color.RED);
        g2.fillRect(10, 10, 100, 100);
        g2.clip(new Rectangle(0, 0, 60, 60));
        g2.setPaint(Color.BLUE);
        g2.fillRect(10, 10, 100, 100);
        g2.setClip(null);
        g2.setPaint(Color.GREEN);
        g2.fillRect(60, 60, 50, 50);        
    }
    
    private static void drawGradientPaintTest(Graphics2D g2) {
        g2.setPaint(new GradientPaint(10f, 10f, Color.RED, 10f, 60f, Color.YELLOW));
        g2.fillRect(10, 10, 50, 50);
        g2.setPaint(Color.BLUE);
        g2.fillRect(60, 10, 50, 50);
    }
    
    private static void drawRadialGradientPaintTest(Graphics2D g2) {
        RadialGradientPaint rgp = new RadialGradientPaint(50, 50, 40, 30, 30, 
                new float[] {0f, 0.75f, 1f}, new Color[] {Color.RED, 
                Color.GREEN, Color.BLUE}, 
                MultipleGradientPaint.CycleMethod.NO_CYCLE);

        g2.setPaint(rgp);
        Ellipse2D circle = new Ellipse2D.Double(10, 10, 80, 80);
        g2.fill(circle);
    }

    private static void drawArcTest(Graphics2D g2) {
        g2.setPaint(Color.GREEN);
        g2.drawRect(0, 20, 70, 50);
        g2.setPaint(Color.RED);
        g2.drawArc(0, 20, 70, 50, 105, 105);
        g2.setPaint(Color.BLUE);
        Path2D arc = createArc(0, 20, 70, 50, 105, 105);
        g2.draw(arc);
        
        
//        Path2D path1 = new Path2D.Double();
//        double[] pts = calculateReferenceArc(90);
//        path1.moveTo(pts[0], pts[1]);
//        path1.curveTo(pts[2], pts[3], pts[4], pts[5], pts[6], pts[7]);
//        AffineTransform t = new AffineTransform();
//        t.translate(35, 45);
//        t.scale(35, 25);
//        t.rotate(Math.PI / 4);
//        path1.transform(t);
//        g2.draw(path1);
//
//        Path2D path2 = new Path2D.Double();
//        path2.moveTo(pts[0], pts[1]);
//        path2.curveTo(pts[2], pts[3], pts[4], pts[5], pts[6], pts[7]);
//        AffineTransform t2 = new AffineTransform();
//        t2.rotate(3 * Math.PI / 4);
//        t2.scale(35, 25);
//        t2.translate(35, 35);
//        path2.transform(t2);
//        //g2.draw(path2);
//        Path2D arc = new Path2D.Double();
//        arc.append(path1, false);
//        arc.append(path2, false);
//        //g2.draw(arc);
//        //g2.draw(path1);
//        //g2.transform(t);
//        g2.setPaint(Color.BLUE);
//        g2.drawArc(0, 20, 70, 50, 0, -270);  
//        //Arc2D arc2d = new Arc2D.Double(0d, 20d, 70d, 50d, 0d, -270, Arc2D.OPEN);
//        //g2.draw(arc2d);
    }
    
    private static double mod(double x, double y) {
        double result = x % y;
        if (result < 0) {
            result += y;
        }
        return result;
    }
    
    private static Path2D createArc(double x, double y, double w, double h, 
            double startAngle, double extent) {
        double start = mod(startAngle, 360.0);
        if (extent > 0.0) {
            return createPositiveArc(x, y, w, h, start, extent);
        } else {
            return createNegativeArc(x, y, w, h, start, -extent);
        }        
    }
    
    private static Path2D createPositiveArc(double x, double y, double w, double h, 
            double startAngle, double extent) {
        
        Path2D result = new Path2D.Double();
        // what is the number of full 90 degree segments
        int quarters = (int) extent / 90;
        double remainder = extent - (90 * quarters);
        double startRadians = startAngle / 180 * Math.PI;
        double[] pts = quarterArc();
        for (int q = 0; q < quarters; q++) {
            Path2D qpath = new Path2D.Double();
            qpath.moveTo(pts[6], pts[7]);
            qpath.curveTo(pts[4], pts[5], pts[2], pts[3], pts[0], pts[1]);
            double rotate = - Math.PI / 4 - startRadians - q * Math.PI / 2;
            AffineTransform r = AffineTransform.getRotateInstance(rotate);
            qpath.transform(r);
            result.append(qpath, false);
        }
        if (remainder > 0.01) {
            double remRads = remainder / 180 * Math.PI;
            // add the remaining small arc
            pts = calculateReferenceArc(remainder);
            Path2D path = new Path2D.Double();
            path.moveTo(pts[6], pts[7]);
            path.curveTo(pts[4], pts[5], pts[2], pts[3], pts[0], pts[1]);
            double rotate = - remRads / 2 - startRadians - quarters * Math.PI / 2;
            AffineTransform r = AffineTransform.getRotateInstance(rotate);
            path.transform(r);
            result.append(path, false);           
        }
        AffineTransform t = new AffineTransform();
        t.translate(x + (w / 2), y + (h / 2));
        t.scale(w / 2, h / 2);
        result.transform(t);

        return result;
    }
    
    private static Path2D createNegativeArc(double x, double y, double w, double h, 
            double startAngle, double extent) {
        
        Path2D result = new Path2D.Double();
        // what is the number of full 90 degree segments
        int quarters = (int) extent / 90;
        double remainder = extent - (90 * quarters);
        double startRadians = startAngle / 180 * Math.PI;
        double[] pts = quarterArc();
        for (int q = 0; q < quarters; q++) {
            Path2D qpath = new Path2D.Double();
            qpath.moveTo(pts[0], pts[1]);
            qpath.curveTo(pts[2], pts[3], pts[4], pts[5], pts[6], pts[7]);
            double rotate = -(startRadians - Math.PI / 4) + q * Math.PI / 2;
            AffineTransform r = AffineTransform.getRotateInstance(rotate);
            qpath.transform(r);
            result.append(qpath, false);
        }
        if (remainder > 0.01) {
            double remRads = remainder / 180 * Math.PI;
            // add the remaining small arc
            pts = calculateReferenceArc(remainder);
            Path2D path = new Path2D.Double();
            path.moveTo(pts[0], pts[1]);
            path.curveTo(pts[2], pts[3], pts[4], pts[5], pts[6], pts[7]);
            double rotate = -(startRadians - remRads / 2) + quarters * Math.PI / 2;
            AffineTransform r = AffineTransform.getRotateInstance(rotate);
            path.transform(r);
            result.append(path, false);           
        }
        AffineTransform t = new AffineTransform();
        t.translate(x + (w / 2), y + (h / 2));
        t.scale(w / 2, h / 2);
        result.transform(t);

        return result;
    }
    
    
    private static double[] quarterArc() {
        return calculateReferenceArc(90.0);    
    }
 
    /**
     * Returns the cubic bezier coordinates for a reference arc of the 
     * specified angle.
     * 
     * @param angle  the angle in degrees (in the range 0 < angle <= 90).
     * 
     * @return 8 coordinates.
     */
    private static double[] calculateReferenceArc(double angle) {
        double a = (angle / 180 * Math.PI) / 2.0;
        double x0 = Math.cos(a);
        double y0 = Math.sin(a);
        double x1 = (4 - x0) / 3;
        double y1 = (1 - x0) * (3 - x0) / (3 * y0);
        double x2 = x1;
        double y2 = - y1;
        double x3 = x0;
        double y3 = -y0;
        return new double[] { x0, y0, x1, y1, x2, y2, x3, y3 };
    } 

    /**
     * Starting point for the demo.
     * 
     * @param args  ignored.
     * 
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        PDFDocument pdfDoc = new PDFDocument();
        Page page = pdfDoc.createPage(new Rectangle(612, 468));
        PDFGraphics2D g2 = page.getGraphics2D();
        drawTextUtilsTest(g2);
        //drawClipTest(g2);
        //drawGradientPaintTest(g2);
        //drawRadialGradientPaintTest(g2);
        //drawArcTest(g2);
        File f = new File("PDFTest.pdf");
        pdfDoc.writeToFile(f);
    }

}
