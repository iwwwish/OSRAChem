OSRAChem
========

OSRAChem is an open source desktop application for automated extraction and visualization of chemical structures from scientific articles (in PDF format). Structures can also be extracted from images depicting structures. All the image file [formats](http://www.graphicsmagick.org/formats.html) supported by [GraphicsMagick](http://www.graphicsmagick.org/) are valid input formats.

The work is a part of an internship project under the supervison of [Dr. Christoph Steinbeck](http://www.ebi.ac.uk/about/people/christoph-steinbeck), Head of Chemoinformatics and Metabolism at [European Bioinformatics Institute](http://www.ebi.ac.uk).

###### Dependencies:
OSRAChem is built on top of the following tools and packages:

1. [OSRA](cactus.nci.nih.gov/osra/)- Optical Structure Recognition Application
2. [CDK](http://sourceforge.net/projects/cdk/)- Chemistry Development Kit
3. [OPSIN](opsin.ch.cam.ac.uk)- Open Parser for Systematic IUPAC Nomenclature
4. [Apache PDFBox](http://pdfbox.apache.org/)- A Java PDF Library
5. [JPedal](http://sourceforge.net/projects/jpedal/)- An open source library with fully-featured PDF viewer

###### Requirements:
The following are manadatory to use OSRAChem:

1. Operating System- Mac OS X or Linux (Ubuntu 12.04 or later)
2. Java Development Kit (JDK)- version 6 or later
3. OSRA- must be installed using Homebrew

###### OSRA installation steps (on Terminal):

1. Install Homebrew, if not installed previously (command: ruby -e "$(curl -fsSL https://raw.github.com/mxcl/homebrew/go)")
2. Add cheminformatics tap via brew (command: brew tap mcs07/cheminformatics)
3. Make sure you have python installed (if not intall (brew install python))
4. Install osra (stable 2.0.0) package (command: brew install osra)
5. OSRA will be installed in the path "/usr/local/Cellar/osra"
6. type osra and you should see something like below (if yes, installation is complete)
******************************************************************************************************
![alt tag](http://i41.tinypic.com/121v582.png)
******************************************************************************************************
7. In case /usr/local denies to install, run command "sudo chown -R `username` /usr/local"

###### Download/Distribution:
Feel free to download or distribute the source code and/or the executable JAR file.

© 2014 [Vishal Siramshetty](http://vishalkpp.blogspot.co.uk) Some Rights Reserved.
