import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimerLabel extends JLabel {
    private long startTime;
    private long passedTime;
    private boolean paused;
    private long totalPausedTime;
    private long pauseStartTime;

    public TimerLabel(UIManager uiManager) {
        this.startTime = System.currentTimeMillis();
        this.paused = false;
        this.totalPausedTime = 0;
        this.pauseStartTime = 0;
        setForeground(Color.WHITE);
        setFont(uiManager.readFont().deriveFont(Font.PLAIN, 30));
        updateTimer();
    }

    public void updateTimer() {
        long currentTime = System.currentTimeMillis();

        if (paused) {
            passedTime = pauseStartTime - startTime - totalPausedTime;
        } else {
            passedTime = currentTime - startTime- totalPausedTime;
        }

        long min = (passedTime/1000)/60;
        long sec = (passedTime/1000)%60;
        String time = String.format("%02d:%02d", min, sec);
        setText(time);

    }

    public void togglePause() {
        if (!paused) {
            paused = true;
            pauseStartTime = System.currentTimeMillis();
        } else {
            paused = false;
            totalPausedTime += System.currentTimeMillis() - pauseStartTime;
            startTime = System.currentTimeMillis() - passedTime - totalPausedTime;
        }
    }

    public void resetTimer() {
        startTime = System.currentTimeMillis();
        totalPausedTime = 0;
        paused = false;
        updateTimer();
    }
}