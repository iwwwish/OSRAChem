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

import java.util.Arrays;
import java.util.List;
import uk.ac.cam.ch.wwmm.opsin.NameToInchi;
import uk.ac.cam.ch.wwmm.opsin.NameToStructure;
import uk.ac.cam.ch.wwmm.opsin.NameToStructureConfig;
import uk.ac.cam.ch.wwmm.opsin.OpsinResult;

/**
 *
 * @author Vishal Siramshetty <vishal[at]ebi.ac.uk>
 */
public class ChemicalNameToStructure {

    static final NameToStructure nts = NameToStructure.getInstance();
    static final NameToStructureConfig ntsconfig = new NameToStructureConfig();

    public static final String SMILE = "smile";
    public static final String INCHI = "inchi";
    public static final String STD_INCHI = "s_inchi";
    public static final List<String> formats = Arrays.asList("smile", "inchi", "std_inchi");

    /**
     * Returns a chemical named entity in specified structural format. Formats
     * supported are: "smile", "inchi" and "std_inchi".
     *
     * @param name
     * @param format
     * @return
     */
    public static String getStructure(String name, String format) {

        OpsinResult result = nts.parseChemicalName(name, ntsconfig);

        // check if the entity cannot be parsed by OPSIN
        if (result.getStatus().equals(OpsinResult.OPSIN_RESULT_STATUS.FAILURE)) {
            System.err.println("Error! OPSIN could not parse the named entity.");
            return result.getStatus().toString();
        }
        // check if the output format is provided incorrectly
        if (!formats.contains(format)) {
            System.err.println("Error! Please check the output format. It should be one of the following: \n"
                    + "1. smile \n"
                    + "2. inchi \n"
                    + "3. s_inchi");

        } else {

            switch (format) {
                // if format specified is SMILE
                case SMILE:
                    return result.getSmiles();
                // if format specified is INCHI
                case INCHI:
                    return NameToInchi.convertResultToInChI(result);
                // if format specified is S_INCHI
                case STD_INCHI:
                    return NameToInchi.convertResultToStdInChI(result);
            }
        }

        return null;
    }
    
    public static String getStatus(String name){
        OpsinResult result = nts.parseChemicalName(name, ntsconfig);
        // check if the entity cannot be parsed by OPSIN
        if (result.getStatus().equals(OpsinResult.OPSIN_RESULT_STATUS.FAILURE)) {
            System.err.println("Error! OPSIN could not parse the named entity.");
            return result.getStatus().toString();
        }
        
        return null;
    }


}
