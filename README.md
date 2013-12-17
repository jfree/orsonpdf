OrsonPDF
========

Version 1.4, 17 December 2013

(C)opyright 2013, by Object Refinery Limited.  All rights reserved.


Overview
--------
OrsonPDF is a graphics library for the Java(tm) platform that allows you to generate content in PDF format using the standard Java2D drawing API (Graphics2D).  OrsonPDF is light-weight, fast, and has no dependencies other than the Java runtime (1.6 or later).  The home page for the project is:

    http://www.object-refinery.com/pdf/

Version 1.0 was release on 31 July 2013 and the most recent version (1.4) was released on 17 December 2013.  Changes since the initial release are listed towards the end of this file.


Getting Started
---------------
The Javadoc page for the PDFDocument class gives an example of typical usage and, if you are already familiar with the Java2D APIs, then all you need to do is add orsonpdf-1.3.jar to your classpath and start coding.

Oracle provides tutorials for Java2D here:

    http://docs.oracle.com/javase/tutorial/2d/

There are some demonstration applications included in the com.orsonpdf.demo.* package.  These applications make use of JFreeChart, so you'll find two additional jars in the 'lib' directory (jfreechart-1.0.17.jar and jcommon-1.0.21.jar).  These are required for the demo code only.


License
-------
OrsonPDF is proprietary software.  The evaluation version is free, but places a watermark on any PDF files generated.  You can purchase a license from Object Refinery Limited and obtain a version without the watermark for inclusion in your own projects.

Other code distributed with OrsonPDF:

- OrsonPDF integrates the Ascii85OutputStream class written by Ben Upsavs and distributed freely under the (BSD-style) terms listed in the Ascii85OutputStream-license.txt file in the 'lib' folder;

- the JFreeChart and JCommon libraries (required for the demos only) are licensed under the terms of the GNU Lesser General Public License (GNU LGPL), a copy of this license can also be found in the 'lib' folder.  To get the source code and other information about JFreeChart and JCommon, please visit http://www.jfree.org/jfreechart/


Change History
--------------

Version 1.4 (17 December 2013)

- added debug mode to generate PDF file without filtering;
- fixed a bug in the graphics stream generation that resulted in malformed PDF output in some cases.

Version 1.3 (8 November 2013)

- implemented getDeviceConfiguration();
- fixed transform bug that could result in malformed PDF output;
- fixed a bug in the getClipBounds() method when the clip is null;
- corrected the Producer version info;
- fixed a bug with the date formatter for the document creation date.

Version 1.2 (12 September 2013)

- fixed bug in RadialGradientPaint support.

Version 1.1 (3 September 2013)

- reimplemented drawString(AttributedCharacterIterator, float, float) using TextLayout and modified drawGlyphVector() to fill rather than stroke shapes;
- added degree elevation to the quadratic segments of Path2D objects to ensure correct output quality;
- fixed Page so it does not add /XObject to resources if there are no xObjects;
- fixed bug affecting switch between GradientPaint and Color;
- fixed clipping bug;
- moved PDF classes into OrsonPDF project (changing the root package to com.orsonpdf.*);

Version 1.0 (31 July 2013)

- Initial public release (JFreeGraphics2D).
