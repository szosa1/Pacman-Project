import javax.swing.*;
import java.awt.*;

public class ScoreLabel extends JLabel {
    public ScoreLabel(UIManager uiManager) {
        setForeground(Color.WHITE);
        setFont(uiManager.readFont().deriveFont(Font.PLAIN, 30));
    }
}