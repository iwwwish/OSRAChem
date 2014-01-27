/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.ebi.result;

import java.io.Serializable;
import java.util.TreeMap;

/**
 *
 * @author vishalkpp
 */
public class ResultMolecule implements Serializable {

    
    // create a serialization ID 
    private final long serialVersionUID = -1L;

    /**
     * Properties
     */
    private TreeMap<Object,Object> Properties;
    
    // ******** static fields **********
    /**
     * SMILES for the molecule.
     */
    private String SMILE;

    /**
     * InChI for the molecule.
     */
    private String INCHI;

    /**
     * Standard InChI for the molecule.
     */
    private String STD_INCHI;

    /**
     * Topological surface area for the molecule.
     */
    private Double TPSA;

    /**
     * Calculated LogP for the molecule.
     */
    private Double XLOGP;

    /**
     * Molecular weight for the molecule.
     */
    private Double MOL_WEIGHT;

    /**
     * Name (if exists) of the molecule.
     */
    private String NAME;

    /**
     * SMILES for Bemis-Murcko fragment of the molecule.
     */
    private String BEMIS_MURCKO_SMILE;

    /**
     * ChEBI ID (if exists) of the molecule.
     */
    private String CHEBI_ID;

    // ********** getters and setters
    public String getSMILE() {
        return SMILE;
    }

    public void setSMILE(String SMILE) {
        this.SMILE = SMILE;
    }

    public String getINCHI() {
        return INCHI;
    }

    public void setINCHI(String INCHI) {
        this.INCHI = INCHI;
    }

    public String getSTD_INCHI() {
        return STD_INCHI;
    }

    public void setSTD_INCHI(String STD_INCHI) {
        this.STD_INCHI = STD_INCHI;
    }

    public Double getTPSA() {
        return TPSA;
    }

    public void setTPSA(Double TPSA) {
        this.TPSA = TPSA;
    }

    public Double getXLOGP() {
        return XLOGP;
    }

    public void setXLOGP(Double XLOGP) {
        this.XLOGP = XLOGP;
    }

    public Double getMOL_WEIGHT() {
        return MOL_WEIGHT;
    }

    public void setMOL_WEIGHT(Double MOL_WEIGHT) {
        this.MOL_WEIGHT = MOL_WEIGHT;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getBEMIS_MURCKO_SMILE() {
        return BEMIS_MURCKO_SMILE;
    }

    public void setBEMIS_MURCKO_SMILE(String BEMIS_MURCKO_SMILE) {
        this.BEMIS_MURCKO_SMILE = BEMIS_MURCKO_SMILE;
    }

    public String getCHEBI_ID() {
        return CHEBI_ID;
    }

    public void setCHEBI_ID(String CHEBI_ID) {
        this.CHEBI_ID = CHEBI_ID;
    }

    public TreeMap<Object, Object> getProperties() {
        return Properties;
    }

    public void setProperties(TreeMap<Object, Object> Properties) {
        this.Properties = Properties;
    }
    
}