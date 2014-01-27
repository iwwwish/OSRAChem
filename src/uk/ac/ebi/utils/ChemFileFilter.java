/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.ebi.utils;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author vishalkpp
 */
public class ChemFileFilter extends FileFilter {

    public final static String mol = ".mol";
    public final static String cml = ".cml";
    public final static String sdf = ".sdf";

    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = Utility.getExtension(f);
        if (extension != null) {
            switch (extension) {
                case ChemFileFilter.mol:
                    return true;
                case ChemFileFilter.sdf:
                    return true;
                case ChemFileFilter.cml:
                    return true;
                default:
                    return false;
            }
        }

        return false;
    }

    @Override
    public String getDescription() {
        return ".mol,.sdf,.cml";

    }

}
