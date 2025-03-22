import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Ghost {
    public int ghostX;
    public int ghostY;
    private int move; // top =1  right = 2  down = 3 left = 4
    public int ghostSpeed = 3;
    public int ghostTileSize = 30;
    private BufferedImage imageGhost1, imageGhost2, imageGhost3, imageGhost4;
    private int distance= 0;
    int mode = 1; // 1 - scatter  2 - hunt  3 - run
    private GameManager gm;
    private CollisionManager collisionManager;
    public Ghost(GameManager gm, CollisionManager collisionManager, int ghostX, int ghostY){
        this.gm = gm;
        this.collisionManager = collisionManager;
        this.ghostX = ghostX;
        this.ghostY = ghostY;
        this.move = generateMoveScatter();
        getGhostImage();
    }
    private void getGhostImage(){
        try{
            imageGhost1 = ImageIO.read(new File("PNGs/V2/Ghost1V1.png"));
            imageGhost2 = ImageIO.read(new File("PNGs/V2/Ghost2V1.png"));
            imageGhost3 = ImageIO.read(new File("PNGs/V2/Ghost3V1.png"));
            imageGhost4 = ImageIO.read(new File("PNGs/V2/Ghost4V1.png"));
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private void changeDirectionOnCollision(){
        boolean collisionDetected = true;
        while (collisionDetected){
            move = generateMoveScatter();
            collisionDetected = checkCollision();
        }
    }
    private boolean checkCollision() {
        switch (move) {
            case 1: // Up
                collisionManager.checkGhostCollisionTop(this);
                return collisionManager.collisionGhost;
            case 2: // Right
                collisionManager.checkGhostCollisionRight(this);
                return collisionManager.collisionGhost;
            case 3: // Down
                collisionManager.checkGhostCollisionBottom(this);
                return collisionManager.collisionGhost;
            case 4: // Left
                collisionManager.checkGhostCollisionLeft(this);
                return collisionManager.collisionGhost;
        }
        return false;
    }

    public void calculateDistance(int x, int y){
        distance = (int)Math.sqrt(Math.abs(gm.player.playerX - x) * Math.abs(gm.player.playerX - x) + Math.abs(gm.player.playerY - y) * Math.abs(gm.player.playerY - y));
    }

    public  void calculateGhostMoveHunt(){
        int shortestDistance=1000;

        collisionManager.checkGhostCollisionTop(this);
        if(!collisionManager.collisionGhost){
            calculateDistance(ghostX, ghostY - ghostTileSize);
            if(shortestDistance > distance){
                shortestDistance = distance;
                move = 1;
            }
        }

        collisionManager.checkGhostCollisionRight(this);
        if(!collisionManager.collisionGhost){
            calculateDistance(ghostX + ghostTileSize, ghostY);
            if(shortestDistance > distance){
                shortestDistance = distance;
                move = 2;
            }
        }

        collisionManager.checkGhostCollisionBottom(this);
        if(!collisionManager.collisionGhost){
            calculateDistance(ghostX , ghostY + ghostTileSize);
            if(shortestDistance > distance){
                shortestDistance = distance;
                move = 3;
            }
        }


        collisionManager.checkGhostCollisionLeft(this);
        if(!collisionManager.collisionGhost){
            calculateDistance(ghostX - ghostTileSize, ghostY);
            if(shortestDistance > distance){
                shortestDistance = distance;
                move = 4;
            }
        }
    }
    public void updateGhostPositionHunt(){
        switch (move){
            case 1:
                ghostY -= ghostSpeed;
                break;
            case 2:
                ghostX += ghostSpeed;
                break;
            case 3:
                ghostY += ghostSpeed;
                break;
            case 4:
                ghostX -= ghostSpeed;
                break;
        }
    }

    public void calculateGhostMoveRun(){
        int longestDistance=0;

        collisionManager.checkGhostCollisionTop(this);
        if(collisionManager.collisionGhost == false){
            calculateDistance(ghostX, ghostY - ghostTileSize);
            if(longestDistance < distance){
                longestDistance = distance;
                move = 1;
            }
        }

        collisionManager.checkGhostCollisionRight(this);
        if(collisionManager.collisionGhost == false){
            calculateDistance(ghostX + ghostTileSize, ghostY);
            if(longestDistance < distance){
                longestDistance = distance;
                move = 2;
            }
        }

        collisionManager.checkGhostCollisionBottom(this);
        if(collisionManager.collisionGhost == false){
            calculateDistance(ghostX , ghostY + ghostTileSize);
            if(longestDistance < distance){
                longestDistance = distance;
                move = 3;
            }
        }


        collisionManager.checkGhostCollisionLeft(this);
        if(collisionManager.collisionGhost == false){
            calculateDistance(ghostX - ghostTileSize, ghostY);
            if(longestDistance < distance){
                longestDistance = distance;
                move = 4;
            }
        }
    }
    public  void updateGhostPositionRun(){
        switch (move){
            case 1:
                ghostY -= ghostSpeed;
                break;
            case 2:
                ghostX += ghostSpeed;
                break;
            case 3:
                ghostY += ghostSpeed;
                break;
            case 4:
                ghostX -= ghostSpeed;
                break;
        }
    }

    private int generateMoveScatter() {
        return (int)(Math.random() * 4) + 1;
    }
    public void updateGhostPositionScatter() {
        if (checkCollision()) {
            changeDirectionOnCollision();
        }

        switch (move) {
            case 1:
                ghostY -= ghostSpeed;
                break;
            case 2:
                ghostX += ghostSpeed;
                break;
            case 3:
                ghostY += ghostSpeed;
                break;
            case 4:
                ghostX -= ghostSpeed;
                break;
        }
    }

    public void setMode(int mode){
        this.mode = mode;
    }
    public int getMode() {
        return mode;
    }

    public void drawGhost1(Graphics graphics){
        graphics.drawImage(imageGhost1, ghostX, ghostY, ghostTileSize, ghostTileSize , null);
    }
    public void drawGhost2(Graphics graphics){
        graphics.drawImage(imageGhost2, ghostX, ghostY, ghostTileSize, ghostTileSize, null);
    }
    public void drawGhost3(Graphics graphics){
        graphics.drawImage(imageGhost3, ghostX, ghostY, ghostTileSize, ghostTileSize, null);
    }
    public void drawGhost4(Graphics graphics){
        graphics.drawImage(imageGhost4, ghostX, ghostY, ghostTileSize, ghostTileSize, null);
    }

}
