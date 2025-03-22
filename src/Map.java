import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Map {
    public int mapTiles[][];
    public int powerTiles[][];
    public int cordsArrX[];
    public int cordsArrY[];
    public int tileSize = 40;
    public int itemSize = 30;
    public int playerPoints =0;
    public int xTilt =200;
    public int yTilt = 200;
    private int xSize = 1080;
    private int ySize = 840;
    public BufferedImage tileContainer[] = new BufferedImage[10];
    private BufferedImage  tile0, tile01, point, defaultTile, powerUp1, powerUp2, powerUp3, powerUp4, powerUp5;

    public void getTileImage(){
        try {
            tile0 = ImageIO.read(new File("PNGs/V2/Tile0(4).png"));
            tileContainer[0] = tile0;

            tile01 = ImageIO.read(new File("PNGs/V2/Tile01(1).png"));
            tileContainer[1] = tile01;

            point = ImageIO.read(new File("PNGs/V2/PacPoint.png"));
            tileContainer[2] = point;

            defaultTile = ImageIO.read(new File("PNGs/V2/Tile0(4).png"));
            tileContainer[3] = defaultTile;

            powerUp1 = ImageIO.read(new File("PNGs/V2/HeartFull.png"));
            tileContainer[4] = powerUp1;

            powerUp2 = ImageIO.read(new File("PNGs/V2/PowerThunder.png"));
            tileContainer[5] = powerUp2;

            powerUp3 = ImageIO.read(new File("PNGs/V2/PowerSword.png"));
            tileContainer[6] = powerUp3;

            powerUp4 = ImageIO.read(new File("PNGs/V2/PowerX2(1).png"));
            tileContainer[7] = powerUp4;

            powerUp5 = ImageIO.read(new File("PNGs/V2/PowerStopWatch(1).png"));
            tileContainer[8] = powerUp5;

        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public int[][] loadMap(String filePath){
        List<int[]> rows = new ArrayList<>();

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))){
            String line;

            while((line = bufferedReader.readLine()) != null){

                String[] values = line.split(" ");
                int[] row = new int[values.length];

                for(int i=0; i<values.length; i++){
                    row[i] = Integer.parseInt(values[i]);
                }

                rows.add(row);
            }

        }catch (IOException e){
            e.printStackTrace();
        }

        mapTiles = new int[rows.size()][];

        for (int i = 0; i < rows.size(); i++) {
            mapTiles[i] = rows.get(i);
        }

        powerTiles = new int[mapTiles.length][mapTiles[0].length];

        for(int i =0; i<powerTiles.length; i++){
            for(int j=0; j< powerTiles[i].length; j++){
                powerTiles[i][j] = 0;
            }
        }
        countPoints();
        calculateTilt();
        return mapTiles;
    }
    public void setCordsArr(){
        int xCoord = xTilt;
        int yCoord = yTilt;
        cordsArrX = new int[mapTiles[0].length];
        cordsArrY = new int[mapTiles.length];

        for(int i=0; i< mapTiles[0].length; i++){
            cordsArrX[i] = xCoord;
            xCoord = xCoord + tileSize;
        }

        for(int i=0; i< mapTiles.length; i++){
            cordsArrY[i] = yCoord;
            yCoord = yCoord + tileSize;
        }
    }
    public void countPoints(){
        for(int i =0; i<mapTiles.length; i++){
            for(int j=0; j<mapTiles[i].length; j++){
                if(mapTiles[i][j] == 0) playerPoints++;
            }
        }
    }
    public void calculateTilt(){
        xTilt = (xSize - mapTiles[0].length*tileSize)/2;
        yTilt = (ySize - mapTiles.length*tileSize)/2;
    }
    public void drawPoints(Graphics graphics){
        for(int i=0; i<mapTiles.length; i++){
            for(int j =0; j< mapTiles[i].length; j++){
                if(mapTiles[i][j] == 0){
                    graphics.drawImage(tileContainer[2], cordsArrX[j], cordsArrY[i],tileSize, tileSize, null);
                }
            }
        }
    }
    public void redrawPoints(Graphics graphics){
        for(int i=0; i<mapTiles.length; i++){
            for(int j =0; j< mapTiles[i].length; j++){
                if(mapTiles[i][j] == 3){
                    graphics.drawImage(tileContainer[2], cordsArrX[j], cordsArrY[i],tileSize, tileSize, null);
                    mapTiles[i][j] = 0;
                }
            }
        }
    }
    public void drawMap(Graphics graphics) {
        for (int i = 0; i < mapTiles.length; i++) {
            for (int j = 0; j < mapTiles[i].length; j++) {
                graphics.drawImage(tileContainer[mapTiles[i][j]], cordsArrX[j], cordsArrY[i], tileSize, tileSize, null);
                if (mapTiles[i][j] == 0) {
                    graphics.drawImage(tileContainer[2], cordsArrX[j], cordsArrY[i], tileSize, tileSize, null);
                }
            }
        }
    }
}
