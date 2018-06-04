import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImagePanel extends JComponent implements SwingConstants{
    private BufferedImage image;
    public ImagePanel(String x, int width, int height) {
        try {
            image = ImageIO.read(new File(x));
            resize(image,width,height);
            repaint();
        } catch (IOException ex) {
            new Exception("File can't be read");
        }
    }

    public  void resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

       image = dimg;
    }

    public BufferedImage getImage()
    {
        return image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this); // see javadoc for more info on the parameters
    }

}

