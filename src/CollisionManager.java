public class CollisionManager {
    public Map map;
    public GameManager gameManager;
    public boolean collisionPlayer;
    public boolean collisionGhost;
    private boolean pointCollision;
    public boolean collisionGhostPlayer = false;
    private final int distance = 9;

    public CollisionManager(Map map, boolean collisionPlayer, boolean itemCollision, GameManager gameManager){
        this.map = map;
        this.collisionPlayer = collisionPlayer;
        this.pointCollision = itemCollision;
        this.gameManager = gameManager;
    }

    // kolizja gracza
    public void checkPlayerCollisionTop(Player player){
        int topLeftX = player.playerX;
        int topLeftY = player.playerY;
        int topRightX = player.playerX + player.playerTileSize;
        int topRightY = player.playerY;

        int tile1 = map.mapTiles[(topLeftY- map.yTilt-distance)/map.tileSize][(topLeftX-map.xTilt)/map.tileSize]; //left up top
        int tile2 = map.mapTiles[(topRightY-map.yTilt-distance)/map.tileSize][(topRightX-map.xTilt)/map.tileSize]; //right up top

        handlePlayerCollision(tile1, tile2, topLeftX, topLeftY);
    }
    public void checkPlayerCollisionBottom(Player player){
        int bottomLeftX = player.playerX;
        int bottomLeftY = player.playerY + player.playerTileSize;
        int bottomRightX = player.playerX + player.playerTileSize;
        int bottomRightY = player.playerY + player.playerTileSize;

        int tile5 = map.mapTiles[(bottomRightY-map.yTilt+distance)/ map.tileSize][(bottomRightX-map.xTilt)/ map.tileSize]; //right down bottom
        int tile6 = map.mapTiles[(bottomLeftY-map.yTilt+distance)/ map.tileSize][(bottomLeftX-map.xTilt)/ map.tileSize]; //left down bottom

        handlePlayerCollision(tile5, tile6, bottomLeftX, bottomLeftY);
    }
    public void checkPlayerCollisionRight(Player player){
        int topRightX = player.playerX + player.playerTileSize;
        int topRightY = player.playerY;
        int bottomRightX = player.playerX + player.playerTileSize;
        int bottomRightY = player.playerY + player.playerTileSize;

        int tile3 = map.mapTiles[(topRightY-map.yTilt)/ map.tileSize][(topRightX-map.xTilt+distance)/ map.tileSize]; //right right top
        int tile4 = map.mapTiles[(bottomRightY-map.yTilt)/ map.tileSize][(bottomRightX-map.xTilt+distance)/map.tileSize]; //right right bottom

        handlePlayerCollision(tile3, tile4, topRightX, topRightY);
    }
    public void checkPlayerCollisionLeft(Player player){
        int bottomLeftX = player.playerX;
        int bottomLeftY = player.playerY + player.playerTileSize;
        int topLeftX = player.playerX;
        int topLeftY = player.playerY;

        int tile7 = map.mapTiles[(topLeftY-map.yTilt)/ map.tileSize][(topLeftX-map.xTilt-distance)/ map.tileSize]; //left left top
        int tile8 = map.mapTiles[(bottomLeftY-map.yTilt)/ map.tileSize][(bottomLeftX-map.xTilt-distance)/ map.tileSize]; //left left bottom

        handlePlayerCollision(tile7, tile8, topLeftX, topLeftY);
    }

    //kolizja duszka
    public void checkGhostCollisionTop(Ghost ghost){
        int topLeftX = ghost.ghostX;
        int topLeftY = ghost.ghostY;
        int topRightX = ghost.ghostX + ghost.ghostTileSize;
        int topRightY = ghost.ghostY;

        int tile1 = map.mapTiles[(topLeftY-map.yTilt-distance)/map.tileSize][(topLeftX-map.xTilt)/map.tileSize]; //left up top
        int tile2 = map.mapTiles[(topRightY-map.yTilt-distance)/map.tileSize][(topRightX-map.xTilt)/map.tileSize];//right up top

        handleGhostCollision(tile1, tile2);
    }
    public void checkGhostCollisionRight(Ghost ghost){

        int topRightX = ghost.ghostX + ghost.ghostTileSize;
        int topRightY = ghost.ghostY;
        int bottomRightX = ghost.ghostX + ghost.ghostTileSize;
        int bottomRightY = ghost.ghostY + ghost.ghostTileSize;

        int tile3 = map.mapTiles[(topRightY-map.yTilt)/ map.tileSize][(topRightX-map.xTilt+distance)/ map.tileSize];//right right top
        int tile4 = map.mapTiles[(bottomRightY-map.yTilt)/ map.tileSize][(bottomRightX-map.xTilt+distance)/map.tileSize]; //right right bottom
        handleGhostCollision(tile3, tile4);
    }
    public void checkGhostCollisionBottom(Ghost ghost){
        int bottomLeftX = ghost.ghostX;
        int bottomLeftY = ghost.ghostY + ghost.ghostTileSize;
        int bottomRightX = ghost.ghostX + ghost.ghostTileSize;
        int bottomRightY = ghost.ghostY + ghost.ghostTileSize;

        int tile5 = map.mapTiles[(bottomRightY-map.yTilt+distance)/ map.tileSize][(bottomRightX-map.xTilt)/ map.tileSize]; //right down bottom
        int tile6 = map.mapTiles[(bottomLeftY-map.yTilt+distance)/ map.tileSize][(bottomLeftX-map.xTilt)/ map.tileSize]; //left down bottom

        handleGhostCollision(tile5, tile6);
    }
    public void checkGhostCollisionLeft(Ghost ghost){

        int bottomLeftX = ghost.ghostX;
        int bottomLeftY = ghost.ghostY + ghost.ghostTileSize;
        int topLeftX = ghost.ghostX;
        int topLeftY = ghost.ghostY;

        int tile7 = map.mapTiles[(topLeftY-map.yTilt)/ map.tileSize][(topLeftX-map.xTilt-distance)/ map.tileSize]; //left left top
        int tile8 = map.mapTiles[(bottomLeftY-map.yTilt)/ map.tileSize][(bottomLeftX-map.xTilt-distance)/ map.tileSize]; //left left bottom

        handleGhostCollision(tile7, tile8);
    }

    private void handlePlayerCollision(int tile1, int tile2, int x, int y) {

        if(tile1 ==1 || tile2 == 1) {
            collisionPlayer = true;
        }else {
            collisionPlayer = false;
        }

        if (!collisionPlayer) {
            if(tile1 == 0 || tile2 == 0) {
                pointCollision = true;
            }else {
                pointCollision = false;
            }
            if (pointCollision) {
                removePoint(x, y);
            }
        }
    }
    private void handleGhostCollision(int tile1, int tile2) {
        if(tile1 ==1 || tile2 == 1){
            collisionGhost = true;
        }else{
            collisionGhost = false;
        }
    }
    public void handleGhostPlayerCollision(Ghost ghost, Player player){
        if (Math.abs(player.playerX - ghost.ghostX) <= 20 && Math.abs(player.playerY - ghost.ghostY) <= 20) {
            collisionGhostPlayer = true;
        } else {
            collisionGhostPlayer = false;
        }
    }
    private void removePoint(int x, int y){
        int mapX = (x-map.xTilt)/ map.tileSize;
        int mapY = (y-map.yTilt)/map.tileSize;
        int baseScore;

        if(map.mapTiles[mapY][mapX] == 0){
            map.mapTiles[mapY][mapX] = 3;

            if(gameManager.player.X2PowerActive){
                baseScore = 200;
            }else baseScore = 100;
            gameManager.playerScore = gameManager.playerScore + baseScore;
            gameManager.basePlayerScore += 100;
        }
    }
}
