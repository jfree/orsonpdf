/* =====================================================================
 * OrsonPDF : a fast, light-weight PDF library for the Java(tm) platform
 * =====================================================================
 * 
 * (C)opyright 2013-2015, by Object Refinery Limited.  All rights reserved.
 *
 * Project Info:  http://www.object-refinery.com/orsonpdf/index.html
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
import com.orsonpdf.Page;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Renders a Swing component to a PDF file, but via an image.
 */
public class PDFSwingComponentDemo2 extends JFrame {
    
    public PDFSwingComponentDemo2(String title) {
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
        int w = c.getWidth();
        int h = c.getHeight();
        BufferedImage img = new BufferedImage(w, h, 
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D img2 = img.createGraphics();
        demo.getContentPane().paint(img2);
        Page page = pdf.createPage(new Rectangle(w, h));
        PDFGraphics2D g2 = page.getGraphics2D();
        g2.drawImage(img, 0, 0, null); 
        pdf.writeToFile(new File("PDFSwingComponentDemo2.pdf"));
    }
}
