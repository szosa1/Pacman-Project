import javax.swing.*;
import java.awt.*;

public class PauseLabel extends JLabel {
    private boolean isPaused;

    public PauseLabel(UIManager uiM) {
        this.isPaused = false;
        setForeground(Color.WHITE);
        setFont(uiM.readFont());
        setHorizontalAlignment(JLabel.CENTER);
        setVerticalAlignment(JLabel.BOTTOM);
        updatePauseLabel();
    }

    public void setPaused(boolean isPaused) {
        this.isPaused = isPaused;
        updatePauseLabel();
    }
    private void updatePauseLabel() {
        if (isPaused) {
            setText("GAME PAUSED");
        } else {
            setText("");
        }
    }
}