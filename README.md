OSRAChem
========

OSRAChem is a desktop application that facilitates a semi-automated work-flow for extracting chemical structures (from images) in full-text scientific articles. It is built on top of the open-source OSRA utility (many thanks to [Igor Filippov](https://cactus.nci.nih.gov/osra/)). The extracted structures are displayed as 2D depictions. The application also takes image and text inputs. All image file [formats](http://www.graphicsmagick.org/formats.html) supported by [GraphicsMagick](http://www.graphicsmagick.org/) are valid.

![alt tag](http://oi57.tinypic.com/308fsjo.jpg)

The work was part of an internship project under the supervison of [Dr. Christoph Steinbeck](https://cheminf.uni-jena.de/members/steinbeck/), at the European Bioinformatics Institute.

###### Dependencies:

1. [OSRA](https://cactus.nci.nih.gov/osra/)- Optical Structure Recognition Application
2. [CDK](http://sourceforge.net/projects/cdk/)- Chemistry Development Kit
3. [OPSIN](opsin.ch.cam.ac.uk)- Open Parser for Systematic IUPAC Nomenclature
4. [Apache PDFBox](http://pdfbox.apache.org/)- A Java PDF Library
5. [JPedal](http://sourceforge.net/projects/jpedal/)- An open source library with fully-featured PDF viewer

###### Requirements:
The following are manadatory:

1. Operating System: Mac OS X or Linux (Ubuntu 12.04 or later)
2. Java 6 or later
3. OSRA must be installed before using OSRAChem

###### OSRA installation steps (Mac OS) (on Terminal):

1. Install Homebrew, if not installed previously (command: /usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)")
2. Tap the [cheminformatics](https://github.com/mcs07/homebrew-cheminformatics) repository (thanks to Matt Swain) (command: brew tap mcs07/cheminformatics)
3. Make sure you have Python installed (if not install (command: brew install python))
4. Install osra (command: brew install osra)
5. type osra and you should see something like below (if yes, installation is complete)
******************************************************************************************************
![alt tag](http://i41.tinypic.com/121v582.png)
******************************************************************************************************
7. In case /usr/local denies to install, run command "sudo chown -R `username` /usr/local"

###### OSRA compilation (Linux) (on Terminal):

Find the instructions [here](https://cactus.nci.nih.gov/osra/).
