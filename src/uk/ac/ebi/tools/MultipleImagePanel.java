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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 * This class provides methods to display multiple images in a single JPanel.
 * Some of the methods were shamelessly copied from stackoverflow post
 * "http://stackoverflow.com/questions/7222988/java-drag-and-drop-images-in-a-list"
 * nicely answered by "Ingo Kegel".
 *
 * @author Vishal Siramshetty <vishal[at]ebi.ac.uk>
 */
public class MultipleImagePanel extends JPanel {

    public static int height;
    public static int width;

    public JList createImageList(DefaultListModel model, int w, int h) {

        height = h;
        width = w;

        JList imageList = new JList(model);
        imageList.setCellRenderer(new ImageCellRenderer());
        imageList.setLayoutOrientation(JList.VERTICAL_WRAP);
        imageList.setVisibleRowCount(-1);
        imageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        imageList.setFixedCellWidth(w);
        imageList.setFixedCellHeight(h);
        imageList.setDragEnabled(false);
        imageList.setDropMode(DropMode.INSERT);

        return imageList;
    }

    public DefaultListModel createModel(List<Image> images) {
        DefaultListModel model = new DefaultListModel();
        for (Image i : images) {
            ImageIcon icon = new ImageIcon(i);

            model.addElement(icon);
        }

        return model;
    }

    public DefaultListModel createModelMap(HashMap<String, Image> imageMap) {
        DefaultListModel model = new DefaultListModel();

        for (Map.Entry<String, Image> entry : imageMap.entrySet()) {
            ImageIcon icon = new ImageIcon(entry.getValue());
            icon.setDescription(entry.getKey());
            model.addElement(icon);
        }
        return model;
    }

    static class ImageCellRenderer extends JPanel implements ListCellRenderer {

        DefaultListCellRenderer defaultListCellRenderer = new DefaultListCellRenderer();

        JLabel imageLabel = new JLabel();
        //JLabel descriptionLabel = new JLabel();

        ImageCellRenderer() {
            setLayout(new BorderLayout());
            Border emptyBorder = BorderFactory.createEmptyBorder(1, 1, 1, 1);
            imageLabel.setBorder(emptyBorder);

            add(imageLabel, BorderLayout.CENTER);

        }

        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            defaultListCellRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            setBorder(new LineBorder(Color.DARK_GRAY, 1, true));
            setBackground(defaultListCellRenderer.getBackground());
            imageLabel.setIcon((Icon) value);
            return this;
        }
    }

    public static void main(String[] args) {
        int h = 400;
        int w = 400;

        String s1 = "C1CCCCC1";
        String s2 = "C1CCCC1";
        String s3 = "C1NCCC1";
        String s4 = "C1OCCC1";

        List<String> s = new ArrayList<>();
        s.add(s3);
        s.add(s2);
        s.add(s1);
        s.add(s4);

        Image[] images = new ImageRenderer().getImageArray(s, h, w);

        JFrame frame = new JFrame("Structures Extracted");
        frame.setSize(3 * w, h);
        frame.setLocationByPlatform(true);

//        DefaultListModel imageModel = createModel(images);
//        JList imageList = createImageList(imageModel, w, h + 10);
//        frame.getContentPane().add(new JScrollPane(imageList));
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setVisible(true);
    }

}
