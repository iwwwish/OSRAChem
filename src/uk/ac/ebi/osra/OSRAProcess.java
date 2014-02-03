/*
 * Copyright (C) 2014. EMBL, European Bioinformatics Institute
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
 */
package uk.ac.ebi.osra;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openscience.cdk.exception.CDKException;
import uk.ac.ebi.tools.PDFExtractor;
import uk.ac.ebi.tools.TimeManager;

/**
 *
 * @author Vishal Siramshetty <vishal[at]ebi.ac.uk>
 */
public class OSRAProcess extends OSRA {

    private static final Logger OSRAlogger = Logger.getLogger("com.lia.core");

    PDFExtractor extractor = new PDFExtractor();
    String osraPath;
    
    public OSRAProcess(String osraPath){
        this.osraPath = osraPath;
    }

    /**
     * Extracts the structures from PDF document provided as input.
     *
     * @param pdfFilePath
     * @return
     * @throws java.io.IOException
     */
    public List<String> getStructuresFromPdf(String pdfFilePath) throws IOException {
        double start = System.currentTimeMillis();
        OSRAlogger.log(Level.INFO, "OSRA process started");

        HashMap<String, String> allImages = extractor.getImages(pdfFilePath);
        // filter images to obtain those with structures
        List<String> validImages = getValidImages(allImages);
        // process each image and get the resulting SMILES into a list
        List<String> results = new ArrayList<>();
        for (String smile : validImages) {
            // now we have all the OSRA results in a list

            if (!smile.isEmpty()) {
                // check if the each result contains multiple smiles
                if (consistsMultipleSmiles(smile)) {
                    results.addAll(getIndividualSmiles(smile));
                } else {
                    results.add(smile);
                }

            }

        }
        // remove duplicates
        HashSet set = new HashSet(results);
        results.clear();
        results.addAll(set);

        OSRAlogger.log(Level.INFO, "OSRA process ended");

        Double elapsed = new TimeManager().getTimeElapsed(start, "seconds");
        System.out.println("OSRA process completed in " + elapsed + " seconds.");

        return results;
    }

    public List<String> getStructuresFromImage(String imageFilePath) {

        double start = System.currentTimeMillis();
        OSRAlogger.log(Level.INFO, "OSRA process started");

        String result = getResult(imageFilePath,osraPath);

        OSRAlogger.log(Level.INFO, "OSRA process ended");

        Double elapsed = new TimeManager().getTimeElapsed(start, "seconds");
        System.out.println("OSRA process completed in: " + elapsed + " seconds.");

        return getIndividualSmiles(result);

    }

    /**
     * Returns only those images that depict chemical structures.
     *
     * @param images
     * @return
     * @throws IOException
     */
    private List<String> getValidImages(HashMap<String, String> imagePaths) throws IOException {

        List<String> valid = new ArrayList<>();
        // parse each image with osra and add to the valid images map
        for (Map.Entry<String, String> entry : imagePaths.entrySet()) {
            String filePath = entry.getKey().concat(".").concat(entry.getValue());
            String result = getResult(entry.getKey().concat(".").concat(entry.getValue()),osraPath);
            if (result != null) {
                valid.add(result);
            }
            File file = new File(filePath);
            file.delete();
        }
        // finally return valid images
        // NOTE: few images might contain multiple structures
        return valid;
    }

    private List<String> getIndividualSmiles(String result) {
        String[] results;
        List<String> smiles = new ArrayList<>();
        if (consistsMultipleSmiles(result)) {
            results = result.split(System.getProperty("line.separator"));
            for (String smile : results) {
                if (!smile.isEmpty()) {
                    smiles.add(smile);
                }
            }
        }
        return smiles;
    }

    private boolean consistsMultipleSmiles(String result) {

        String newline = System.getProperty("line.separator");
        boolean hasNewline = result.contains(newline);

        return hasNewline;
    }

    public static void main(String[] args) throws IOException, CDKException {
        String imageFilePath = "/Users/vishalkpp/Downloads/test3.pdf";
        String osraPath = "/usr/local/Cellar/osra/2.0.0/bin/osra";
        List<String> smiles = new OSRAProcess(osraPath).getStructuresFromPdf(imageFilePath);
        System.out.println(smiles.toString());
        //new OSRA().displayImagesInPanel(smiles, new JPanel());
    }
}
