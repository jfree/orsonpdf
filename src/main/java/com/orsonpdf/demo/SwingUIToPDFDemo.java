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

package com.orsonpdf.demo;

import com.orsonpdf.PDFDocument;
import com.orsonpdf.PDFGraphics2D;
import com.orsonpdf.PDFHints;
import com.orsonpdf.Page;
import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * This demo shows how to export a Swing UI to PDF.
 */
public class SwingUIToPDFDemo extends JFrame implements ActionListener {
    
    public SwingUIToPDFDemo(String title) {
        super(title);
        add(createContent());
    }
    
    private JComponent createContent() {
        JPanel content = new JPanel(new BorderLayout());
        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Tab 1", new JButton("First Tab"));
        tabs.add("Tab 2", new JButton("Second Tab"));
        JButton button = new JButton("Save to PDF");
        button.addActionListener(this);
        content.add(tabs);
        content.add(button, BorderLayout.SOUTH);
        return content;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        JComponent c = (JComponent) getContentPane().getComponent(0);
        PDFDocument pdfDoc = new PDFDocument();
        pdfDoc.setTitle("SwingUIToPDFDemo.java");
        pdfDoc.setAuthor("Object Refinery Limited");
        Page page = pdfDoc.createPage(new Rectangle(c.getWidth(), c.getHeight()));
        PDFGraphics2D g2 = page.getGraphics2D();
        g2.setRenderingHint(PDFHints.KEY_DRAW_STRING_TYPE, 
                PDFHints.VALUE_DRAW_STRING_TYPE_VECTOR);
        c.paint(g2);
        File f = new File("SwingUIToPDFDemo.pdf");
        pdfDoc.writeToFile(f);
    }

    public static void main(String[] args) {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }

        SwingUIToPDFDemo app = new SwingUIToPDFDemo("SwingUIToPDFDemo.java");
        app.pack();
        app.setVisible(true);
    }

}
