import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class HeartLabel extends JLabel {
    public HeartLabel() {
        try {
            ImageIcon heartImage = new ImageIcon(ImageIO.read(new File("PNGs/V2/HeartFull.png")));
            Image scaledImage = heartImage.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            setIcon(scaledIcon);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}