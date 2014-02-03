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

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JWindow;

/**
 *
 * @author Vishal Siramshetty <vishal[at]ebi.ac.uk>
 */
public class SplashingScreen extends JWindow {

    public Image img;
    public SplashingScreen(Image img) {
        try {
            this.img = img;
            setSize(484, 176);
            setLocationRelativeTo(null);
            show();
            Thread.sleep(4000);
            dispose();

        } catch (InterruptedException exception) {
            javax.swing.JOptionPane.showMessageDialog((java.awt.Component) null, "Error" + exception.getMessage(), "Error:",
                    javax.swing.JOptionPane.DEFAULT_OPTION);
        }
    }

    public void paint(Graphics g) {
        g.drawImage(img, 0, 0, this);
    }

}
