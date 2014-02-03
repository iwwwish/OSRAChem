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
package uk.ac.ebi.tools;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import org.jpedal.examples.viewer.Commands;
import org.jpedal.examples.viewer.Viewer;

/**
 *
 * @author Vishal Siramshetty <vishal[at]ebi.ac.uk>
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
