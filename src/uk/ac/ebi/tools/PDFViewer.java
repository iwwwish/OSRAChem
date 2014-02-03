/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.ebi.tools;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import org.jpedal.examples.viewer.Commands;
import org.jpedal.examples.viewer.Viewer;

/**
 *
 * @author vishalkpp
 */
public class PDFViewer {

    public Viewer viewer;
    public Object[] input;

    public void loadPDFInViewer(String filepath, JPanel panel) {

        JInternalFrame frame = new JInternalFrame();
        this.viewer = new Viewer(frame, null);
        viewer.setRootContainer(panel);
        viewer.setupViewer();

        //Object[] input;
        input = new Object[]{filepath};

        viewer.executeCommand(Commands.OPENFILE, input);
    }

    public void disposeViewer() {
        viewer.executeCommand(Commands.DELETE, input);
    }

}
