import javax.swing.*;
import java.awt.*;

public class EndScreenLabel extends JLabel {
    private boolean gameOver;

    public EndScreenLabel(UIManager uiManager) {
        this.gameOver = false;
        setForeground(Color.WHITE);
        setFont(uiManager.readFont().deriveFont(Font.PLAIN, 150));
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
        updateEndScreen();
    }

    private void updateEndScreen() {
        if (gameOver) {
            setText("GAME OVER");
        } else {
            setText("");
        }
    }
}