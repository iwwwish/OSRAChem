package uk.ac.ebi.tools;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.layout.StructureDiagramGenerator;
import org.openscience.cdk.renderer.AtomContainerRenderer;
import org.openscience.cdk.renderer.font.AWTFontManager;
import org.openscience.cdk.renderer.generators.BasicSceneGenerator;
import org.openscience.cdk.renderer.visitor.AWTDrawVisitor;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesParser;
import uk.ac.ebi.utils.ImageUtility;

/**
 * This class is used to render images of chemical structures when corresponding
 * SMILES are provided as inputs.
 *
 * @author vishal
 */
public class ImageRenderer {

    /* default width and height for image renderer can be changed whenever needed */
    private int width = 300;
    private int height = 300;

    public Image getImageFromSmile(String smiles) throws InvalidSmilesException, CDKException {
        
        BufferedImage image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
        SmilesParser smilesParser = new SmilesParser(SilentChemObjectBuilder.getInstance());
        IAtomContainer molecule = smilesParser.parseSmiles(smiles);

        StructureDiagramGenerator sdg = new StructureDiagramGenerator();
        sdg.setMolecule(molecule);
        sdg.generateCoordinates();
        molecule = sdg.getMolecule();
        
        List generators = Arrays.asList(new SmoothGenerator(),
                new BasicSceneGenerator()
        );

        AtomContainerRenderer renderer = new AtomContainerRenderer(generators, new AWTFontManager());
        renderer.getRenderer2DModel().set(BasicSceneGenerator.FitToScreen.class, true);
        //renderer.getRenderer2DModel().set(BasicBondGenerator.BondWidth.class, 2.0d);
        // renderer.getRenderer2DModel().set(BasicAtomGenerator.ColorByType.class, true);
        Graphics2D g2 = image.createGraphics();

        g2.fillRect(0, 0, width, height);
        renderer.paint(molecule, new AWTDrawVisitor(g2),
                new Rectangle2D.Double(0, 0, width, height), true);
        g2.dispose();

        return image;
    }

    /**
     * This method returns an array of images when provided a list of SMILES.
     *
     * @param smiles
     * @param height
     * @param width
     * @return Array containing images of molecules
     */
    public Image[] getImageArray(List<String> smiles, int height, int width) {
        this.height = height;
        this.width = width;

        Image[] allImages = new Image[smiles.size()];
        int i = 0;
        for (String s : smiles) {
            try {
                Image img = getImageFromSmile(s);
                allImages[i] = img;
                i++;
            } catch (CDKException ex) {
                Logger.getLogger(ImageRenderer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return allImages;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public static void main(String[] args) throws CDKException, IOException {
        String s = "COC1=C(OC)C=C2C(=O)C(CC3CCN(CC4=CC=CC=C4)CC3)CC2=C1";
        Image img = new ImageRenderer().getImageFromSmile(s);
        BufferedImage bi = ImageUtility.getBufferedImaged(img, 400, 400);
        ImageIO.write(bi, "PNG", new File("/Users/vishalkpp/Desktop/test.png"));

    }

}
