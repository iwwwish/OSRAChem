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
package uk.ac.ebi.tools;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import uk.ac.ebi.utils.CDKUtil;
import uk.ac.ebi.osra.OSRAResult;
import uk.ac.ebi.utils.Utility;

/**
 *
 * @author Vishal Siramshetty <vishal[at]ebi.ac.uk>
 */
public class StructureListener {

    String imageFilePath = null;
    String smiles = null;

    public StructureListener(String smiles) {
        this.smiles = smiles;
    }

    public void setdoubleClickToSaveImage(final ImageIcon icon, final JComponent label) {

        label.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getClickCount() == 2 && !e.isConsumed()) {
                    e.consume();
                    saveImageAsFile(icon, label);
                }

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    public void setClickToSaveImage(final JList list) {

        list.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getClickCount() == 2 && !e.isConsumed()) {
                    e.consume();
                    ImageIcon icon = (ImageIcon) list.getSelectedValue();
                    saveImageAsFile(icon, list);
                }

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    public void setClickToDisplayProperties(final JList list) {

        list.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && !e.isConsumed()) {
                    e.consume();
                    // String selectedItem = (String) label.getSelectedValue();
                    ImageIcon selectedIcon = (ImageIcon) list.getSelectedValue();
                    System.out.println(selectedIcon.getDescription());
                    String smile = selectedIcon.getDescription();
                    IAtomContainer mol = CDKUtil.IO.loadMoleculeFromSMILES(smile);

                    OSRAResult propertiesPanel = new OSRAResult();
                    // starting a property panel with all properties calculated
                    int h = propertiesPanel.getStructure().getHeight();
                    int w = propertiesPanel.getStructure().getWidth();
                    ImageRenderer renderer = new ImageRenderer();
                    renderer.setHeight(h);
                    renderer.setWidth(w);
                    ImageIcon icon;
                    try {

                        // set ImageIcon
                        icon = new ImageIcon(renderer.getImageFromSmile(smile));
                        propertiesPanel.getStructure().setIcon(icon);
                        propertiesPanel.setIcon(icon);

                        // set SMILES
                        propertiesPanel.getSmiles1().setText(smile);
                        propertiesPanel.getSmiles1().setCaretPosition(0);
                        propertiesPanel.getSmiles1().setToolTipText(smile);
                        propertiesPanel.getSmiles1().setEditable(false);

                        String inchi = CDKUtil.Molecule.getInChI(mol);
                        if (inchi == null) {
                            AtomContainerManipulator.convertImplicitToExplicitHydrogens(mol);
                            inchi = CDKUtil.Molecule.getInChI(mol);

                            // set InChI
                            propertiesPanel.getInchi1().setText(inchi);
                            propertiesPanel.getInchi1().setCaretPosition(0);
                            propertiesPanel.getInchi1().setToolTipText(inchi);
                            propertiesPanel.getInchi1().setEditable(false);

                            //set InChI Key
                            String inchi_key = CDKUtil.Molecule.getInChIKey(mol);
                            propertiesPanel.getInchiKey1().setText(inchi_key);
                            propertiesPanel.getInchiKey1().setCaretPosition(0);
                            propertiesPanel.getInchiKey1().setToolTipText(inchi_key);
                            propertiesPanel.getInchiKey1().setEditable(false);
                        } else {
                            // set InChI
                            propertiesPanel.getInchi1().setText(inchi);
                            propertiesPanel.getInchi1().setCaretPosition(0);
                            propertiesPanel.getInchi1().setToolTipText(inchi);
                            propertiesPanel.getInchi1().setEditable(false);

                            //set InChI Key
                            String inchi_key = CDKUtil.Molecule.getInChIKey(mol);
                            propertiesPanel.getInchiKey1().setText(inchi_key);
                            propertiesPanel.getInchiKey1().setCaretPosition(0);
                            propertiesPanel.getInchiKey1().setToolTipText(inchi_key);
                            propertiesPanel.getInchiKey1().setEditable(false);

                        }
                        // set XLogP
                        propertiesPanel.getLogP1().setText(String.valueOf(CDKUtil.Molecule.getXLogP(mol)));
                        propertiesPanel.getLogP1().setCaretPosition(0);
                        propertiesPanel.getLogP1().setEditable(false);

                        // set TPSA
                        propertiesPanel.getTpsa1().setText(String.valueOf(CDKUtil.Molecule.getTPSA(mol)));
                        propertiesPanel.getTpsa1().setEditable(false);
                        // set Mass
                        propertiesPanel.getMass1().setText(String.valueOf(CDKUtil.Molecule.getExactMass(mol)));
                        propertiesPanel.getMass1().setEditable(false);

                        propertiesPanel.setVisible(true);
                        propertiesPanel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    } catch (CDKException ex) {
                        Logger.getLogger(OSRAResult.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

    }

    public void setSingleClickToDisplayProperties(final JComponent label) {

        label.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && !e.isConsumed()) {
                    e.consume();

                    String smile = getResultSMILES();
                    IAtomContainer mol = CDKUtil.IO.loadMoleculeFromSMILES(smile);

                    OSRAResult propertiesPanel = new OSRAResult();
                    // starting a property panel with all properties calculated
                    int h = propertiesPanel.getStructure().getHeight();
                    int w = propertiesPanel.getStructure().getWidth();
                    ImageRenderer renderer = new ImageRenderer();
                    renderer.setHeight(h);
                    renderer.setWidth(w);
                    ImageIcon icon;
                    try {

                        // set ImageIcon
                        icon = new ImageIcon(renderer.getImageFromSmile(smile));
                        propertiesPanel.getStructure().setIcon(icon);
                        propertiesPanel.setIcon(icon);

                        // set SMILES
                        propertiesPanel.getSmiles1().setText(smile);
                        propertiesPanel.getSmiles1().setCaretPosition(0);
                        propertiesPanel.getSmiles1().setToolTipText(smile);
                        propertiesPanel.getSmiles1().setEditable(false);

                        String inchi = CDKUtil.Molecule.getInChI(mol);
                        if (inchi == null) {
                            AtomContainerManipulator.convertImplicitToExplicitHydrogens(mol);
                            inchi = CDKUtil.Molecule.getInChI(mol);

                            // set InChI
                            propertiesPanel.getInchi1().setText(inchi);
                            propertiesPanel.getInchi1().setCaretPosition(0);
                            propertiesPanel.getInchi1().setToolTipText(inchi);
                            propertiesPanel.getInchi1().setEditable(false);

                            //set InChI Key
                            String inchi_key = CDKUtil.Molecule.getInChIKey(mol);
                            propertiesPanel.getInchiKey1().setText(inchi_key);
                            propertiesPanel.getInchiKey1().setCaretPosition(0);
                            propertiesPanel.getInchiKey1().setToolTipText(inchi_key);
                            propertiesPanel.getInchiKey1().setEditable(false);
                        } else {
                            // set InChI
                            propertiesPanel.getInchi1().setText(inchi);
                            propertiesPanel.getInchi1().setCaretPosition(0);
                            propertiesPanel.getInchi1().setToolTipText(inchi);
                            propertiesPanel.getInchi1().setEditable(false);

                            //set InChI Key
                            String inchi_key = CDKUtil.Molecule.getInChIKey(mol);
                            propertiesPanel.getInchiKey1().setText(inchi_key);
                            propertiesPanel.getInchiKey1().setCaretPosition(0);
                            propertiesPanel.getInchiKey1().setToolTipText(inchi_key);
                            propertiesPanel.getInchiKey1().setEditable(false);

                        }
                        // set XLogP
                        propertiesPanel.getLogP1().setText(String.valueOf(CDKUtil.Molecule.getXLogP(mol)));
                        propertiesPanel.getLogP1().setCaretPosition(0);
                        propertiesPanel.getLogP1().setEditable(false);

                        // set TPSA
                        propertiesPanel.getTpsa1().setText(String.valueOf(CDKUtil.Molecule.getTPSA(mol)));
                        propertiesPanel.getTpsa1().setEditable(false);
                        // set Mass
                        propertiesPanel.getMass1().setText(String.valueOf(CDKUtil.Molecule.getExactMass(mol)));
                        propertiesPanel.getMass1().setEditable(false);

                        propertiesPanel.setVisible(true);
                        propertiesPanel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    } catch (CDKException ex) {
                        Logger.getLogger(OSRAResult.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

    }

    private void saveImageAsFile(ImageIcon icon, JComponent component) {
        File fileToSave = Utility.UI.saveFile(component);
        Image img = icon.getImage();
        BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bi.createGraphics();
        g2.drawImage(img, 0, 0, null);
        g2.dispose();
        try {
            ImageIO.write(bi, "png", fileToSave);
        } catch (IOException ex) {
            Logger.getLogger(OSRAResult.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private ImageIcon getImageIcon(int width, int height, String smiles) throws CDKException {
        ImageRenderer renderer = new ImageRenderer();
        renderer.setWidth(width);
        renderer.setHeight(height);
        if (smiles != null) {

            ImageIcon icon = new ImageIcon(renderer.getImageFromSmile(smiles));
            //System.out.println("ImageIcon generated with dimensions: " + icon.getIconHeight() + "x" + icon.getIconWidth());
            return icon;
        } else {
            //System.out.println("There was an error processing the image.");
            return null;
        }

    }

    private String getResultSMILES() {

        return this.smiles;
    }

}
