import java.awt.*;
import java.io.File;

public class UIManager {
    boolean pause = false;
    private TimerLabel timerLabel;
    private ScoreLabel scoreLabel;
    private EndScreenLabel endScreenLabel;
    private InfoLabel infoLabel;
    private PauseLabel pauseLabel;
    public UIManager() {
        timerLabel = new TimerLabel(this);
        scoreLabel = new ScoreLabel(this);
        endScreenLabel = new EndScreenLabel(this);
        infoLabel = new InfoLabel(this);
        pauseLabel = new PauseLabel(this);
    }

    public Font readFont() {
        Font font = new Font("Font", Font.PLAIN, 30);

        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/PixelifySans-Regular.ttf")).deriveFont(Font.PLAIN, 50);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return font;
    }

    //obsluga timera
    public void togglePause() {
        timerLabel.togglePause();
        if(!pause) {
            pause = true;
        }else {
            pause = false;
        }
    }
    public void updateTimer() {
        timerLabel.updateTimer();
    }
    public void resetTimer() {
        timerLabel.resetTimer();
    }
    public TimerLabel getTimerLabel() {
        return timerLabel;
    }

    public ScoreLabel getScoreLabel() {
        return scoreLabel;
    }

    public EndScreenLabel getEndScreenLabel() {
        return endScreenLabel;
    }

    public InfoLabel getInfoLabel() {
        return infoLabel;
    }

    public PauseLabel getPauseLabel() {
        return pauseLabel;
    }
    
}