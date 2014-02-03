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
public class PDFFileFilter extends FileFilter {

    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = Utility.getExtension(f);
        if (extension != null) {
            return extension.equals(".pdf");
        }

        return false;
    }

    @Override
    public String getDescription() {
        return ".pdf";
    }

}
