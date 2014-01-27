/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.ebi.osra;

import java.awt.Dimension;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesParser;
import uk.ac.ebi.CDKUtil;
import uk.ac.ebi.result.ResultMolecule;
import uk.ac.ebi.utils.GenericCmdLogObserver;
import uk.ac.ebi.utils.GenericCmdOutputObserver;
import uk.ac.ebi.utils.GenericCmdv2;
import uk.ac.ebi.tools.ImageRenderer;
import uk.ac.ebi.tools.MultipleImagePanel;

/**
 *
 * @author vishalkpp
 */
public class OSRA {

    public String getResult(String filepath) {

        int p = filepath.lastIndexOf(".");
        String extension = filepath.substring(p + 1, filepath.length());
        List<String> tags = Arrays.asList("AVI", "BMP", "BMP2", "BMP3", "CALS", "CGM", "CIN", "DDS", "DJVU", "DNG", "DOT", "DPX", "EXR", "FIG", "FITS", "GIF", "HDR", "JNG", "JP2", "JPC", "JPG", "JPEG", "JXR", "MIFF", "MNG", "M2V", "MPEG", "MTV", "MVG", "PAM", "PBM", "PCX", "PFM", "PGM", "PNG", "PNG8", "PNG00", "PNG24", "PNG32", "PNG48", "PNG64", "PNM", "PPM", "PSB", "PSD", "PWP", "SCT", "SFW", "SVG", "TIF", "TIFF", "TTF", "VIFF", "WBMP", "WDP", "WEBP", "WMF", "WPG", "XBM", "XPM", "XWD");
        if (tags.contains(extension.toUpperCase())) {
            // simple command line execution of OSRA
            GenericCmdv2 runOSRA = new GenericCmdv2("OSRA Simple Run");
            runOSRA.setCommand("/usr/local/Cellar/osra/2.0.0/bin/osra");
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
                //rawOut = rawOut.replaceAll("\\s", "");
                // System.out.println(rawOut);
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

    public void displayImagesInPanel(List<String> resultSmiles, JPanel panel) throws CDKException {

        // get all images to a list
        ImageRenderer renderer = new ImageRenderer();
        renderer.setHeight(300);
        renderer.setWidth(300);
        panel.setSize(300, 300);
        panel.setAlignmentX(0);
        panel.setAlignmentY(0);
        List<Image> images = Arrays.asList(renderer.getImageArray(resultSmiles, 300, 300));

        // add the images to a JList within JScrollPane
        MultipleImagePanel imPanel = new MultipleImagePanel();
        DefaultListModel imageModel = imPanel.createModel(images);
        JList imageList = imPanel.createImageList(imageModel, 300, 300);
        // add JList to System.out.println(h);
        JScrollPane jScrollPane = new JScrollPane(imageList);
        jScrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));

        // create a JFrame
        JFrame frame = new JFrame("Structures Extracted");
        frame.setSize((3 * 300), 336);
        frame.setLocationByPlatform(true);
        frame.getContentPane().add(jScrollPane);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

}
