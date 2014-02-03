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
package uk.ac.ebi.utils;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Vishal Siramshetty <vishal[at]ebi.ac.uk>
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
