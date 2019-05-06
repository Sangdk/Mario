package images;

import javax.swing.*;
import java.awt.*;

public class ImageLoader {
    public static Image getImage(String name) {
        ImageIcon image = new ImageIcon(new ImageLoader().getClass().getResource("/images/" + name));
        return image.getImage();
    }
}
