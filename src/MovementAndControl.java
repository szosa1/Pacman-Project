import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MovementAndControl implements KeyListener {
    public boolean left, right, up, down, close;
    UIManager uiManager;

    public MovementAndControl(UIManager uiManager){
        this.uiManager = uiManager;

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode(); //przypisuje przyciskom na klawiaturze ich wartosc

        if(keyCode == KeyEvent.VK_A){
            left = true;
        }

        if(keyCode == KeyEvent.VK_D){
            right = true;
        }

        if(keyCode == KeyEvent.VK_W){
            up = true;
        }

        if(keyCode == KeyEvent.VK_S){
            down = true;
        }

        if(keyCode == KeyEvent.VK_SPACE){
            uiManager.togglePause();
        }

        if(keyCode == KeyEvent.VK_ESCAPE){
            close = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode(); //przypisuje przyciskom na klawiaturze ich wartosc

        if(keyCode == KeyEvent.VK_A){
            left = false;
        }

        if(keyCode == KeyEvent.VK_D){
            right = false;
        }

        if(keyCode == KeyEvent.VK_W){
            up = false;
        }

        if(keyCode == KeyEvent.VK_S){
            down = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    public void disableKeyboard(){
        left =false;
        right = false;
        up = false;
        down = false;
    }
}
