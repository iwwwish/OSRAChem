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
package uk.ac.ebi.result;

import java.io.Serializable;
import java.util.TreeMap;

/**
 *
 * @author Vishal Siramshetty <vishal[at]ebi.ac.uk>
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
