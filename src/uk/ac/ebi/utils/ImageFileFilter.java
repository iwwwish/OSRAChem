/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.ebi.utils;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author vishalkpp
 */
public class ImageFileFilter extends FileFilter {

    //public static List<String> imageTags = Utility.getNamesInFile(new File("/Users/vishalkpp/NetBeansProjects/OSRAChem/Data/image_formats.txt"));
    public static List<String> imageTags = Arrays.asList("AVI", "BMP", "BMP2", "BMP3", "CALS", "CGM", "CIN", "DDS", "DJVU", "DNG", "DOT", "DPX", "EXR", "FIG", "FITS", "GIF", "HDR", "JNG", "JP2", "JPC", "JPG", "JPEG", "JXR", "MIFF", "MNG", "M2V", "MPEG", "MTV", "MVG", "PAM", "PBM", "PCX", "PFM", "PGM", "PNG", "PNG8", "PNG00", "PNG24", "PNG32", "PNG48", "PNG64", "PNM", "PPM", "PSB", "PSD", "PWP", "SCT", "SFW", "SVG", "TIF", "TIFF", "TTF", "VIFF", "WBMP", "WDP", "WEBP", "WMF", "WPG", "XBM", "XPM", "XWD");

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
