/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.ebi.utils;

import java.io.File;
import java.util.List;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author vishalkpp
 */
public class ImageFileFilter extends FileFilter {

    public static List<String> imageTags = Utility.getNamesInFile(new File("/Users/vishalkpp/NetBeansProjects/OSRAChem/Data/image_formats.txt"));

    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = Utility.getExtension(f);
        extension = extension.substring(1);
        extension = extension.toUpperCase();
        if (extension != null) {
            return imageTags.contains(extension);
        }

        return false;
    }

    @Override
    public String getDescription() {
        return ".png,.bmp,.jpg";
    }

}
