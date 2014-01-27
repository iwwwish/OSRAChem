/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.ebi.utils;

import java.util.Arrays;
import java.util.List;
import uk.ac.cam.ch.wwmm.opsin.NameToInchi;
import uk.ac.cam.ch.wwmm.opsin.NameToStructure;
import uk.ac.cam.ch.wwmm.opsin.NameToStructureConfig;
import uk.ac.cam.ch.wwmm.opsin.OpsinResult;

/**
 *
 * @author vishalkpp
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

}
