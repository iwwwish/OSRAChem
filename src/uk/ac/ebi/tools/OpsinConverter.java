/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.ebi.tools;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import uk.ac.ebi.CDKUtil;
import uk.ac.ebi.result.ResultMolecule;
import uk.ac.ebi.utils.ChemicalNameToStructure;

/**
 * A class for generation of ResultMolecule from name of the molecule. Name
 * formats supported are: IUPAC name; Traditional name
 *
 * @author vishalkpp
 */
public class OpsinConverter {

    static String input;

    public OpsinConverter(String input) {
        OpsinConverter.input = input;
    }

    public ResultMolecule getResult() throws CDKException {
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

    public static void main(String[] args) throws CDKException {
        OpsinConverter converter = new OpsinConverter("phenol");
        System.out.println(converter.getResult().getSMILE());
    }

}
