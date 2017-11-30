OrsonPDF
========

Version 1.8, by David Gilbert, 30 November 2017.

(C)opyright 2013-2017, by Object Refinery Limited.  All rights reserved.

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.orsonpdf/orsonpdf/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.orsonpdf/orsonpdf)

Overview
--------
OrsonPDF is a PDF generation library for the Java(tm) platform that allows you to create content in PDF format using the standard Java2D drawing API (`Graphics2D`).  OrsonPDF is light-weight, fast, and has no dependencies other than the Java runtime (1.6 or later).  The home page for the project is:

http://www.object-refinery.com/orsonpdf/

Getting Started
---------------
The Javadoc page for the PDFDocument class gives an example of typical usage and, if you are already familiar with the Java2D APIs, then all you need to do is add orsonpdf-1.8.jar to your classpath and start coding.

Oracle provides tutorials for Java2D here:

http://docs.oracle.com/javase/tutorial/2d/

There are some demonstration applications included in the [JFree Demos](https://github.com/jfree/jfree-demos) project. 


Dual Licensing
--------------
OrsonPDF is dual licensed.  You can use OrsonPDF under the terms of the GNU General Public License version 3 (GPLv3) or later.  If you prefer not to be bound by the terms of the GPLv3, there is an option to buy a commercial license from Object Refinery Limited - see the OrsonPDF web page for details.

OrsonPDF integrates the Ascii85OutputStream class written by Ben Upsavs and distributed freely under the (BSD-style) terms listed in the Ascii85OutputStream-license.txt file.


Change History
--------------

Version 1.8 (30 November 2017)

- fix for exception in `drawImage()` with null transform;
- removed Ant build support;
- moved demo code to https://github.com/jfree/jfree-demos;


Version 1.7 (28 September 2015)

- implemented `PDFGraphics2D.create()` method;
- added support for image transparency;
- fixed broken `PDFGraphics2D.drawImage()` method;
- added GPLv3 or later as a license option.


Version 1.6 (31 March 2014)

- added support for shape outlining with arbitrary `Stroke` implementations (previously only `BasicStroke` was recognised);
- minor Javadoc updates.


Version 1.5 (5 March 2014)

- added rendering hint `DRAW_STRING_TYPE` which provides an option to render text as vector graphics, which allows the inclusion of Unicode characters in the output without font embedding;
- improve support for alpha transparency;
- minor additions to default font mapping.


Version 1.4 (18 December 2013)

- fixed invalid `XREF` table which caused Acrobat Reader to prompt for saving when closing PDFs;
- added debug mode to generate PDF file without filtering;
- fixed a bug in the graphics stream generation that resulted in malformed PDF output in some locales.


Version 1.3 (8 November 2013)

- implemented `getDeviceConfiguration()`;
- fixed transform bug that could result in malformed PDF output;
- fixed a bug in the `getClipBounds()` method when the clip is null;
- corrected the `Producer` version info;
- fixed a bug with the date formatter for the document creation date.


Version 1.2 (12 September 2013)

- fixed bug in `RadialGradientPaint` support.


Version 1.1 (3 September 2013)

- reimplemented `drawString(AttributedCharacterIterator, float, float)` using `TextLayout` and modified `drawGlyphVector()` to fill rather than stroke shapes;
- added degree elevation to the quadratic segments of `Path2D` objects to ensure correct output quality;
- fixed `Page` so it does not add `/XObject` to resources if there are no `xObjects`;
- fixed bug affecting switch between `GradientPaint` and `Color`;
- fixed clipping bug;
- moved PDF classes into OrsonPDF project (changing the root package to `com.orsonpdf.*`);


Version 1.0 (31 July 2013)

- Initial public release (`JFreeGraphics2D`).
