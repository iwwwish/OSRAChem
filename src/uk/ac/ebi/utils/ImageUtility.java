/*
 * This is an image utility written by Vishal Siramshetty. It includes methods 
 * that users can use to manipulate images to their own will. The type of images
 * supported herein are PNG, BMP, JPG, GIF.
 * 
 * This utiltiy will be constantly updated and for any clarifications, contact 
 * the author on srmshtty@gmail.com personally.
 *
 * @author Vishal (s0visira@uni-bonn.de)
 */
package uk.ac.ebi.utils;

import uk.ac.ebi.tools.ImageRenderer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.openscience.cdk.exception.CDKException;

public class ImageUtility {

    /**
     * This method is used for loading an image.
     *
     * @param filepath
     * @return
     */
    public static BufferedImage loadImageFromFile(String filepath) {

        BufferedImage bufferedImg = null;
        try {
            bufferedImg = ImageIO.read(new File(filepath));
        } catch (IOException ex) {
            Logger.getLogger(ImageUtility.class.getName()).log(Level.SEVERE,
                    null, ex);
        }

        return bufferedImg;
    }

    /**
     * This method is used for converting a java.awt.image.Image to scaled
     * BufferedImage of desired width and height.
     *
     * @param img
     * @param height
     * @param width
     * @return
     */
    public static BufferedImage getBufferedImaged(Image img, int width, int height) {

        Image image = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage bufferedImg = new BufferedImage(image.getWidth(null),
                image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        bufferedImg.getGraphics().drawImage(image, 0, 0, null);

        return bufferedImg;
    }

    /**
     * This method is used for drawing an image to JFrame.
     *
     * @param frame
     * @param image
     */
    public static void loadImageToFrame(JFrame frame, BufferedImage image) {

        // set the boundaries of the JFrame and make it visible
        frame.setBounds(0, 0, image.getWidth(), image.getHeight());
        frame.setVisible(true);
        // get the Graphics"d object  
        Graphics2D g = (Graphics2D) frame.getRootPane().getGraphics();
        // draw the image
        g.drawImage(image, null, 0, 0);
    }

    public static void loadIMageIconToPanel(JFrame frame, JPanel panel, ImageIcon icon) {

        JLabel label = new JLabel();
        label.setIcon(icon);
        panel.add(label);
        frame.add(panel);
        frame.setVisible(true);
    }

    /**
     * This method is used for saving an image as PNG to a specified path.
     *
     * @param img
     * @param outFileName
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void saveImageasPNG(BufferedImage img, String outFileName) throws FileNotFoundException, IOException {
        File outF = new File(outFileName);
        ImageIO.write((RenderedImage) img, "PNG", outF);
    }

    public static void saveImage(BufferedImage img, String outFileName, String format) throws IOException {
        File outF = new File(outFileName);
        ImageIO.write((RenderedImage) img, format, outF);
    }

    /**
     * This method is used for resizing an image to desired size.
     *
     * @param img
     * @param newHeight
     * @param newWidth
     * @return
     */
    public static BufferedImage resizeImage(BufferedImage img, int newHeight, int newWidth) {
        int height = img.getHeight();
        int width = img.getWidth();

        BufferedImage newImg = new BufferedImage(newWidth, newHeight, img.getType());
        Graphics2D g = newImg.createGraphics();
        // g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(img, 0, 0, newWidth, newHeight, null);
        g.dispose();

        return newImg;
    }

    /**
     * This method is used to get the byte array of an image. When you want to
     * save an image to database in blob datatype, you need to get the bytes
     * array of the image.
     *
     * @param imageFile
     * @return
     */
    public static byte[] getBytesArray(File imageFile) {

        BufferedImage img;
        try {
            img = ImageIO.read(imageFile);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(img, getImageExtension(imageFile), baos);
            // see below for getImageExtension method
            baos.flush();
            byte[] imageData = baos.toByteArray();
            baos.close();
            return imageData;
        } catch (IOException ex) {
            Logger.getLogger(ImageUtility.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * This method is used for retrieving the extension of an image file.
     *
     * @param imageFile
     * @return
     */
    public static String getImageExtension(File imageFile) {
        String fName = imageFile.getName();
        String extension = fName.substring(fName.indexOf(".") + 1, fName.length());
        return extension;
    }

    /**
     * This method is used for obtaining image icon from image.
     *
     * @param img
     * @return
     */
    public static ImageIcon getImageIcon(BufferedImage img) {
        ImageIcon icon = new ImageIcon(img);
        return icon;
    }

    /**
     * This method is used for obtaining a preferred size from height and width
     *
     * @param width
     * @param height
     * @return
     */
    public static Dimension getPreferredSize(int width, int height) {
        return new Dimension(width, height);
    }

    /**
     * This method is used for obtaining a scaled Dimension depending upon the
     * dimensions of the boundary.
     *
     * @param imgSize
     * @param boundary
     * @return
     */
    public static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {

        int original_width = imgSize.width;
        int original_height = imgSize.height;
        int bound_width = boundary.width;
        int bound_height = boundary.height;
        int new_width = original_width;
        int new_height = original_height;

        // first check if we need to scale width
        if (original_width > bound_width) {
            //scale width to fit
            new_width = bound_width;
            //scale height to maintain aspect ratio
            new_height = (new_width * original_height) / original_width;
        }

        // then check if we need to scale even with the new height
        if (new_height > bound_height) {
            //scale height to fit instead
            new_height = bound_height;
            //scale width to maintain aspect ratio
            new_width = (new_height * original_width) / original_height;
        }

        return new Dimension(new_width, new_height);
    }

 
    public static void setBackground(BufferedImage img, Color color) {
        Graphics2D g = img.createGraphics();
        g.setBackground(color);
        g.drawImage(img, 0, 0, g.getBackground(), null);
    }

    public static Dimension getDimension(BufferedImage img) {
        int h = img.getHeight();
        int w = img.getWidth();

        Dimension dim = new Dimension(w, h);

        return dim;
    }


    public static void getCompressedImage(BufferedImage image, String path) throws IOException {

        Iterator iter = ImageIO.getImageWritersByFormatName("png");
        ImageWriter writer = (ImageWriter) iter.next();
        ImageWriteParam iwp = writer.getDefaultWriteParam();
        iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        iwp.setCompressionQuality(1);

        File file = new File(path);
        FileImageOutputStream output = new FileImageOutputStream(file);
        writer.setOutput(output);
        IIOImage newimage = new IIOImage(image, null, null);
        writer.write(null, newimage, iwp);
        writer.dispose();
    }

    public static BufferedImage getAntiAliasedImage(BufferedImage image) {

        BufferedImage bimage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = bimage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        g2.drawImage(image, 0, 0, null);
        g2.dispose();

        return bimage;
    }

    public static Graphics2D getAntiAliasedGraphics(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        return g2;

    }

    public static void main(String[] args) {

        String smile = "c1ccccc1";
        ImageRenderer renderer = new ImageRenderer();
        try {
            ImageIcon icon = new ImageIcon(renderer.getImageFromSmile(smile));
            loadIMageIconToPanel(new JFrame(), new JPanel(), icon);
        } catch (CDKException ex) {
            Logger.getLogger(ImageUtility.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
