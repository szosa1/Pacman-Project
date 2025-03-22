import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player {
    private GameManager gameManager;
    public MovementAndControl movement;
    private Map map;
    private CollisionManager collisionManager;
    public int playerX;
    public int playerY;
    int playerSpeed = 4;
    public int playerTileSize = 30;
    public int playerLifes = 3;
    private int counter = 0;
    public boolean thunderPowerActive = false;
    public boolean swordPowerActive = false;
    public long thunderActivatedTime;
    public long swordActivatedTime;
    public boolean X2PowerActive = false;
    public long X2ActivatedTime;
    public boolean watchPowerActive = false;
    public long watchActivatedTime;
    private BufferedImage basicImage, left, right, up, down, stopRight,stopLeft, stopUp, stopDown, rigthMid, leftMid, upMid, downMid;

    public Player(GameManager gameManager, MovementAndControl movement, Map map, int playerX, int playerY){
        this.gameManager = gameManager;
        this.movement = movement;
        this.map = map;
        this.collisionManager = new CollisionManager(map, false, false, gameManager);
        this.playerX = playerX;
        this.playerY = playerY;
        getPlayerImage();
    }
    private void getPlayerImage(){
        try{
            basicImage = ImageIO.read(new File("PNGs/V2/PacBASIC.png"));

            left = ImageIO.read(new File("PNGs/V2/PacLEFT.png"));
            right = ImageIO.read(new File("PNGs/V2/PacRIGHT.png"));
            up = ImageIO.read(new File("PNGs/V2/PacUP.png"));
            down = ImageIO.read(new File("PNGs/V2/PacDOWN.png"));

            stopRight = ImageIO.read(new File("PNGs/V2/PacSTOP_RIGHT.png"));
            stopLeft = ImageIO.read(new File("PNGs/V2/PacSTOP_LEFT.png"));
            stopUp = ImageIO.read(new File("PNGs/V2/PacSTOP_UP.png"));
            stopDown = ImageIO.read(new File("PNGs/V2/PacSTOP_DOWN.png"));

            rigthMid = ImageIO.read(new File("PNGs/V2/PacRIGHT_MID.png"));
            leftMid = ImageIO.read(new File("PNGs/V2/PacLEFT_MID.png"));
            upMid = ImageIO.read(new File("PNGs/V2/PacUP_MID.png"));
            downMid = ImageIO.read(new File("PNGs/V2/PacDOWN_MID.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void updatePlayerPosition(){
        if(movement.left == true){
            collisionManager.checkPlayerCollisionLeft(this);
            if(collisionManager.collisionPlayer == false){
                playerX = playerX - playerSpeed;

                if(counter > 0 && counter <=3 ){
                    basicImage = left;
                }

                if(counter > 3 && counter <= 7){
                    basicImage = leftMid;
                }

                if(counter > 7){
                    basicImage = stopLeft;
                    counter = -4;
                }
                counter++;
            }
        }

        if(movement.right == true){
            collisionManager.checkPlayerCollisionRight(this);
            if(collisionManager.collisionPlayer == false){
                playerX = playerX + playerSpeed;

                if(counter > 0 && counter <=3 ){
                    basicImage = right;
                }

                if(counter > 3 && counter <= 7){
                    basicImage = rigthMid;
                }

                if(counter > 7){
                    basicImage = stopRight;
                    counter = -4;
                }
                counter++;
            }
        }

        if(movement.up == true){
            collisionManager.checkPlayerCollisionTop(this);
            if(collisionManager.collisionPlayer == false){
                playerY = playerY - playerSpeed;

                if(counter > 0 && counter <=3 ){
                    basicImage = up;
                }

                if(counter > 3 && counter <= 7){
                    basicImage = upMid;
                }

                if(counter > 7){
                    basicImage = stopUp;
                    counter = -4;
                }
                counter++;
            }
        }

        if(movement.down == true){
            collisionManager.checkPlayerCollisionBottom(this);
            if(collisionManager.collisionPlayer == false){
                playerY = playerY + playerSpeed;

                if(counter > 0 && counter <=3 ){
                    basicImage = down;
                }

                if(counter > 3 && counter <= 7){
                    basicImage = downMid;
                }

                if(counter > 7){
                    basicImage = stopDown;
                    counter = -4;
                }
                counter++;
            }
        }
    }

    public void draw(Graphics graphics){
        graphics.drawImage(basicImage, playerX, playerY, playerTileSize, playerTileSize, null);
    }
}
