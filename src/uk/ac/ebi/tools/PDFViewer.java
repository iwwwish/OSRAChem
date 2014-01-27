/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.ebi.tools;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import org.jpedal.examples.viewer.Commands;
import org.jpedal.examples.viewer.Viewer;

/**
 *
 * @author vishalkpp
 */
public class PDFViewer {

    private static JFrame frame = new JFrame();

    public JFrame getFrame() {
        return frame;
    }

    public static void loadPDFInViewer(String filepath) {

        int index = filepath.lastIndexOf("/");
        String fileName = filepath.substring(index + 1);

        //Create a JFrame
        //JFrame frame = new JFrame();
        frame.getContentPane().setLayout(new BorderLayout());

        JInternalFrame rootContainer = new JInternalFrame(fileName);
        Viewer viewer = new Viewer(rootContainer, null);
        viewer.setupViewer();

        frame.add(rootContainer, BorderLayout.CENTER);
        rootContainer.setVisible(true);
        frame.setTitle("PDF Viewer");

        frame.setSize(600, 800);
        frame.addWindowListener(new WindowListener() {
            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowOpened(WindowEvent e) {
            }
        });

        frame.setVisible(true);

        Object[] input;
        input = new Object[]{filepath};

        viewer.executeCommand(Commands.OPENFILE, input);
    }

    public static void main(String[] args) {
        String filePath = "/Users/vishalkpp/Downloads/test.pdf";
        loadPDFInViewer(filePath);
    }
}
