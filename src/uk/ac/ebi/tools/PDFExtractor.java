/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.ebi.tools;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;
import uk.ac.ebi.utils.Utility;

/**
 *
 * @author vishalkpp
 */
public class PDFExtractor {

    // public String imageFormat = null;
    private static final Logger PDFlogger = Logger.getLogger("com.lia.core");

    /**
     * Extracts images from a PDF file and returns them in a list.
     *
     * @param filePath
     * @return
     * @throws java.io.IOException
     */
    public HashMap<String, String> getImages(String filePath) throws IOException {

        HashMap<String, String> imagePaths = new HashMap<>();
        try {

            if (new File(filePath).exists()) {
                PDDocument document = PDDocument.load(filePath);
                List<PDPage> list = document.getDocumentCatalog().getAllPages();

                for (PDPage page : list) {
                    PDResources pdResources = page.getResources();

                    Map pageImages = pdResources.getImages();
                    if (pageImages.size() > 0) {
                        Iterator imageIter = pageImages.keySet().iterator();
                        while (imageIter.hasNext()) {
                            String key = (String) imageIter.next();
                            PDXObjectImage pdxObjectImage = (PDXObjectImage) pageImages.get(key);
                            String uniqueName = PDFDoc.generateUniqueName();
                            StringBuilder builder = new StringBuilder();

                            // set the imageFormat
                            String imageFormat = pdxObjectImage.getSuffix();

                            builder = builder.append(System.getProperty("user.home")).append("/").append(uniqueName);
                            imagePaths.put(builder.toString(), imageFormat);
                            pdxObjectImage.write2file(builder.toString());
                            builder.delete(0, builder.length());
                        }
                    }
                }

                document.close();
            } else {
                System.err.println("File not exists");
            }

        } catch (IOException ex) {
            PDFlogger.log(Level.SEVERE, "Error while extracting: Please check the input.", ex.getMessage());
        }

        return imagePaths;
    }

    /**
     * Provides few utility methods on PDF document.
     */
    public static class PDFDoc {

        /**
         * Returns the name of the document.
         *
         * @param fileName
         * @return
         */
        public static String getName(String fileName) {

            File file = new File(fileName);

            return file.getName();
        }

        /**
         * OSRAPDF.java:142 Returns the path of the document.
         *
         * @param fileName
         * @return
         */
        public static String getSafePath(String fileName) {
            File file = new File(fileName);
            return file.getAbsolutePath();
        }

        /**
         * Returns the extension of the document.
         *
         * @param fileName
         * @return
         */
        public static String getExtension(String fileName) {
            File file = new File(fileName);

            return Utility.getExtension(file);
        }

        /**
         * Returns the size of the document in KiloBytes (KB),
         *
         * @param fileName
         * @return
         */
        public static Double getSizeInKB(String fileName) {
            double bytes = 0;
            File file = new File(fileName);
            if (file.exists()) {
                bytes = file.length();
            }
            return bytes / 1024;
        }

        /**
         * Returns the size of the document in MegaBytes(MB),
         *
         * @param fileName
         * @return
         */
        public static Double getSizeInMB(String fileName) {
            double kiloBytes = getSizeInKB(fileName);
            return kiloBytes / 1024;
        }

        /**
         * Returns a unique alphanumeric string of length 8.
         *
         * @return
         */
        public static String generateUniqueName() {
            Random random = new Random();
            String tag = Long.toString(Math.abs(random.nextLong()), 36);
            return tag.substring(0, 8);
        }

        private PDFDoc() {

        }

    }

}
