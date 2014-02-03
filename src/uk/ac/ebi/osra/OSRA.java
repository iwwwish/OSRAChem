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

import java.awt.Dimension;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesParser;
import uk.ac.ebi.utils.CDKUtil;
import uk.ac.ebi.result.ResultMolecule;
import uk.ac.ebi.utils.GenericCmdLogObserver;
import uk.ac.ebi.utils.GenericCmdOutputObserver;
import uk.ac.ebi.utils.GenericCmdv2;
import uk.ac.ebi.tools.ImageRenderer;
import uk.ac.ebi.tools.MultipleImagePanel;
import uk.ac.ebi.tools.StructureListener;

/**
 *
 * @author Vishal Siramshetty <vishal[at]ebi.ac.uk>
 */
public class OSRA {
    
    public String getResult(String filepath, String osraPath) {
        
        int p = filepath.lastIndexOf(".");
        String extension = filepath.substring(p + 1, filepath.length());
        List<String> tags = Arrays.asList("AVI", "BMP", "BMP2", "BMP3", "CALS", "CGM", "CIN", "DDS", "DJVU", "DNG", "DOT", "DPX", "EXR", "FIG", "FITS", "GIF", "HDR", "JNG", "JP2", "JPC", "JPG", "JPEG", "JXR", "MIFF", "MNG", "M2V", "MPEG", "MTV", "MVG", "PAM", "PBM", "PCX", "PFM", "PGM", "PNG", "PNG8", "PNG00", "PNG24", "PNG32", "PNG48", "PNG64", "PNM", "PPM", "PSB", "PSD", "PWP", "SCT", "SFW", "SVG", "TIF", "TIFF", "TTF", "VIFF", "WBMP", "WDP", "WEBP", "WMF", "WPG", "XBM", "XPM", "XWD");
        if (tags.contains(extension.toUpperCase())) {
            // simple command line execution of OSRA
            GenericCmdv2 runOSRA = new GenericCmdv2("OSRA Simple Run");
            //runOSRA.setCommand("/usr/local/Cellar/osra/2.0.0/bin/osra");
            runOSRA.setCommand(osraPath);
            runOSRA.addFlag("", filepath);

            // create log obsevers
            GenericCmdOutputObserver rawOutputObserver = new GenericCmdOutputObserver();
            GenericCmdLogObserver cmdLogObserver = new GenericCmdLogObserver();

            // attach observers to command
            runOSRA.attachObserver(rawOutputObserver);
            runOSRA.attachObserver(cmdLogObserver);

            // run the command
            runOSRA.runCommand(false); // run the command with boolean batch processing set to false

            String rawOut = null;
            try {
                rawOut = rawOutputObserver.waitForOutput(); // osra result 
            } catch (InterruptedException ex) {
                Logger.getLogger(OSRA.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return rawOut;
        } else {
            System.err.println("Image format not accepted.");
        }
        return null;
        
    }
    
    public List<ResultMolecule> getResultMolecules(List<String> resultSmiles) throws InvalidSmilesException {
        
        List<ResultMolecule> molecules = new ArrayList<>();
        SmilesParser parser = new SmilesParser(SilentChemObjectBuilder.getInstance());
        for (String smile : resultSmiles) {
            try {
                IAtomContainer mol = parser.parseSmiles(smile);
                ResultMolecule molecule = new ResultMolecule();
                molecule.setSMILE(smile);
                molecule.setINCHI(CDKUtil.Molecule.getInChI(mol));
                molecule.setSTD_INCHI(CDKUtil.Molecule.getInChIKey(mol));
                molecule.setXLOGP(CDKUtil.Molecule.getXLogP(mol));
                molecule.setTPSA(CDKUtil.Molecule.getTPSA(mol));
                molecule.setMOL_WEIGHT(CDKUtil.Molecule.getExactMass(mol));
                molecules.add(molecule);
            } catch (InvalidSmilesException ex) {
                System.err.println("Invalid smiles" + ex.getLocalizedMessage());
            }
            
        }
        
        return molecules;
        
    }
    
    public void displayImagesInPanel(List<String> resultSmiles, JInternalFrame frame) throws CDKException {
        
        int w = frame.getWidth() - 30;
        int h = frame.getHeight() - 30;
        // get all images to a list
        ImageRenderer renderer = new ImageRenderer();
        renderer.setHeight(250);
        renderer.setWidth(250);

        //List<Image> images = Arrays.asList(renderer.getImageArray(resultSmiles, 250, 250));
        HashMap<String, Image> imageMap = renderer.getImageMap(resultSmiles, 250, 250);

        // add the images to a JList within JScrollPane
        MultipleImagePanel imPanel = new MultipleImagePanel();
        DefaultListModel imageModel = imPanel.createModelMap(imageMap);
        JList imageList = imPanel.createImageList(imageModel, 250, 250);
        imageList.setSize(w, h);
        imageList.setToolTipText("Double click to save image (or) Single click to open in a new window.");
        
        StructureListener listener = new StructureListener("listener");
        listener.setClickToDisplayProperties(imageList);
        //listener.setClickToSaveImage(imageList);
        
        JScrollPane scrollPane = new JScrollPane(imageList);
        scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));
        frame.setContentPane(scrollPane);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
    }
    
}
