/* =====================================================================
 * OrsonPDF : a fast, light-weight PDF library for the Java(tm) platform
 * =====================================================================
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.  All rights reserved.
 *
 * Project Info:  http://www.object-refinery.com/orsonpdf/index.html
 * 
 */

package com.orsonpdf.demo;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JComponent;
import com.orsonpdf.PDFDocument;
import com.orsonpdf.PDFGraphics2D;
import com.orsonpdf.PDFHints;
import com.orsonpdf.Page;

/**
 * Renders a Swing component to a PDF file.
 */
public class PDFSwingComponentDemo1 extends JFrame {
    
    public PDFSwingComponentDemo1(String title) {
        super(title);
        JPanel content = new JPanel(new BorderLayout());
        content.add(new JButton("Hello"));
        content.add(new JLabel("This is a label"), BorderLayout.EAST);
        setContentPane(content);
    }
    
    public static void main(String[] args) {
        PDFSwingComponentDemo1 demo = new PDFSwingComponentDemo1(
                "PDFSwingComponentDemo1.java");
        demo.pack();
        demo.setVisible(true);
        PDFDocument pdf = new PDFDocument();
        JComponent c = (JComponent) demo.getContentPane();
        Page page = pdf.createPage(new Rectangle(c.getWidth(), c.getHeight()));
        PDFGraphics2D g2 = page.getGraphics2D();
        g2.setRenderingHint(PDFHints.KEY_DRAW_STRING_TYPE, PDFHints.VALUE_DRAW_STRING_TYPE_VECTOR);
        demo.getContentPane().paint(g2); 
        pdf.writeToFile(new File("PDFSwingComponentDemo1.pdf"));
    }
}
