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

import java.awt.Image;
import javax.swing.ImageIcon;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import uk.ac.ebi.utils.CDKUtil;
import uk.ac.ebi.result.ResultMolecule;

/**
 * A class for generation of ResultMolecule from name of the molecule. Name
 * formats supported are: IUPAC name; Traditional name
 *
 * @author Vishal Siramshetty <vishal[at]ebi.ac.uk>
 */
public class OpsinConverter {

    private static String input;
    private static ResultMolecule molecule;
    private static String Smile;

    public OpsinConverter(String input) throws CDKException {
        OpsinConverter.input = input;
        OpsinConverter.molecule = getResultMolecule();
    }

    public ResultMolecule getResultMolecule() throws CDKException {

        ResultMolecule mol = new ResultMolecule();
        // set the SMILE
        mol.setSMILE(ChemicalNameToStructure.getStructure(input, "smile"));
        // set the INCHI
        mol.setINCHI(ChemicalNameToStructure.getStructure(input, "inchi"));
        // set the STD_INCHI
        mol.setINCHI(ChemicalNameToStructure.getStructure(input, "std_inchi"));
        // set the name
        mol.setNAME(input);
        // get molecule object
        IAtomContainer container = CDKUtil.IO.loadMoleculeFromSMILES(mol.getSMILE());
        // set the CalculatedLogP
        mol.setTPSA(CDKUtil.Molecule.getXLogP(container));
        // set the TPSA
        mol.setXLOGP(CDKUtil.Molecule.getXLogP(container));
        // set the molecular weight
        mol.setMOL_WEIGHT(CDKUtil.Molecule.getExactMass(container));
        // set Bemis-Murcko SMILES
        mol.setBEMIS_MURCKO_SMILE(CDKUtil.Molecule.getBemisMurckoSMILES(container));
        // set the properties
        mol.setProperties(CDKUtil.Molecule.getProperties(container));

        return mol;
    }

    public String getSmile() throws CDKException {

        return molecule.getSMILE();
    }

    public ImageIcon getImageIcon(int w, int h) throws CDKException {
        ImageRenderer renderer = new ImageRenderer();
        renderer.setHeight(h);
        renderer.setWidth(w);
        Image im = renderer.getImageFromSmile(getSmile());
        return new ImageIcon(im);
    }

    public static void main(String[] args) throws CDKException {
        OpsinConverter converter = new OpsinConverter("aspirin");
        
        System.out.println(converter.getSmile());
    }

}
