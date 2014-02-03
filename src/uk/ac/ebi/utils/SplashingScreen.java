/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.ebi.utils;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.SplashScreen;
import java.awt.Toolkit;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JWindow;

/**
 *
 * @author vishalkpp
 */
public class SplashingScreen extends JWindow {

    public Image img;
    public SplashingScreen(Image img) {
        try {
            this.img = img;
            setSize(300, 189);
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
