import javax.swing.*;
import java.awt.*;

public class InfoLabel extends JLabel {
    private String infoText;
    private String infoText2;

    public InfoLabel(UIManager uiManager) {
        this.infoText = "Press space to pause";
        this.infoText2 = "Press space to unpause";
        setForeground(Color.WHITE);
        setFont(uiManager.readFont().deriveFont(Font.PLAIN, 30));
    }

    public void InfoPaused() {
        setText(infoText2);
    }
    public void InfoUnpaused(){
        setText(infoText);
    }
}