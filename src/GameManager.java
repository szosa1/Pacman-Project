import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class GameManager extends JPanel {
    private int tileSize = 30;
    private int screenPixHeight = 840;
    private int screenPixWidth = 1080;
    private final double FPS = 60;
    public int playerScore = 0;
    private int defaultPlayerX = 0;
    private int defaultPlayerY = 0;
    private int defaultGhost1X, defaultGhost2X, defaultGhost3X, defaultGhost4X;
    private int defaultGhost1Y, defaultGhost2Y, defaultGhost3Y, defaultGhost4Y;
    private int powerCount = 0;
    private int rand = 0;
    private boolean allowToGenerate = true;
    private List<int[]> drawnPowers = new ArrayList<>();
    private boolean isRunning;
    private boolean isFrozen = false;
    private boolean isGameOverWindow = false;
    public int basePlayerScore = 0;
    private CollisionManager collisionManager;
    private UIManager uiManager;
    private MovementAndControl movement;
    private Map map = new Map();
    public Player player;
    private Ghost ghost, ghost1, ghost2, ghost3, ghost4;
    private Thread animationThread;
    private Thread timerThread;
    private Thread ghostThread1;
    private Thread ghostThread2;
    private Thread ghostThread3;
    private Thread ghostThread4;
    private Thread modeSwitchThread;
    private Thread powerUpThread;
    private Thread UiThread;
    private JPanel heartPanel = new JPanel();
    private HeartLabel heartLabel1 = new HeartLabel();
    private HeartLabel heartLabel2 = new HeartLabel();
    private HeartLabel heartLabel3 = new HeartLabel();
    private MainMenu mainMenu;
    private JFrame frame;

    public GameManager(JFrame frame) {
        //ustawienia okna
        this.frame = frame;
        this.setPreferredSize(new Dimension(screenPixWidth, screenPixHeight));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.requestFocus();
        this.setLayout(new BorderLayout());

        //inicjalizacja obiektów innych klas
        mainMenu = new MainMenu();
        collisionManager = new CollisionManager(map, false, false, this);
        uiManager = new UIManager();
        movement = new MovementAndControl(uiManager);
        ghost = new Ghost(this, collisionManager, -100, -100); // pierwszy duszek domyslny ktory nigdy nie pojawi sie na ekranie
        this.addKeyListener(movement);

        //ustawinie panelu gry
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout());
        gamePanel.setOpaque(false);

        uiManager.getEndScreenLabel().setHorizontalAlignment(SwingConstants.CENTER);
        gamePanel.add(uiManager.getEndScreenLabel());

        //ustawienia statsPanelu
        JPanel statsPanel = new JPanel(new GridLayout(1,3));
        statsPanel.setPreferredSize(new Dimension(screenPixWidth, 50));
        statsPanel.setBackground(Color.BLACK);

        uiManager.getTimerLabel().setHorizontalAlignment(SwingConstants.CENTER);
        statsPanel.add(uiManager.getTimerLabel());

        uiManager.getScoreLabel().setHorizontalAlignment(SwingConstants.CENTER);
        statsPanel.add(uiManager.getScoreLabel());

        heartPanel.setBackground(Color.BLACK);
        heartPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        heartPanel.add(heartLabel1);
        heartPanel.add(heartLabel2);
        heartPanel.add(heartLabel3);
        statsPanel.add(heartPanel);

        //ustawienia infoPanelu
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setPreferredSize(new Dimension(screenPixWidth, 100));
        infoPanel.setBackground(Color.BLACK);

        uiManager.getPauseLabel().setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.add(uiManager.getPauseLabel());

        uiManager.getInfoLabel().setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.add(uiManager.getInfoLabel());

        //dodanie paneli do okna
        add(infoPanel, BorderLayout.SOUTH);
        add(statsPanel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        map.drawMap(graphics);
        map.drawPoints(graphics);
        player.draw(graphics);

        for (int[] power : drawnPowers) {
            int x = power[0];
            int y = power[1];
            int rand = power[2];
            drawPower(graphics, x, y, rand);
        }

        ghost1.drawGhost1(graphics);
        ghost2.drawGhost2(graphics);
        ghost3.drawGhost3(graphics);
        ghost4.drawGhost4(graphics);

        if (basePlayerScore % (map.playerPoints * 100) == 0 && basePlayerScore != 0) {
            map.redrawPoints(graphics);
        }

        if(movement.close) exit();
    }
    public void loadMap(String mapFilePath){
        map.getTileImage();
        map.loadMap(mapFilePath);
        map.setCordsArr();
    }
    private void resetPlayerPosition(){
        player.playerX = defaultPlayerX;
        player.playerY = defaultPlayerY;
    }
    private void resetGhostPosition(){
        ghost1.ghostX = defaultGhost1X;
        ghost1.ghostY = defaultGhost1Y;

        ghost2.ghostX = defaultGhost2X;
        ghost2.ghostY = defaultGhost2Y;

        ghost3.ghostX = defaultGhost3X;
        ghost3.ghostY = defaultGhost3Y;

        ghost4.ghostX = defaultGhost4X;
        ghost4.ghostY = defaultGhost4Y;
    }
    public void setUpGhosts() {
            ghost1 = new Ghost(this, collisionManager, map.cordsArrX[1], map.cordsArrY[1]);
            defaultGhost1X = ghost1.ghostX;
            defaultGhost1Y = ghost1.ghostY;
            ghost2 = new Ghost(this, collisionManager, map.cordsArrX[1], map.cordsArrY[map.cordsArrY.length-2]);
            defaultGhost2X = ghost2.ghostX;
            defaultGhost2Y = ghost2.ghostY;
            ghost3 = new Ghost(this, collisionManager, map.cordsArrX[map.cordsArrX.length-2], map.cordsArrY[1]);
            defaultGhost3X = ghost3.ghostX;
            defaultGhost3Y = ghost3.ghostY;
            ghost4 = new Ghost(this, collisionManager, map.cordsArrX[map.cordsArrX.length-2], map.cordsArrY[map.cordsArrY.length-2]);
            defaultGhost4X = ghost4.ghostX;
            defaultGhost4Y = ghost4.ghostY;
    }
    public void setUpPlayer(){
        player = new Player(this, movement, map, 350, 390);
        defaultPlayerX = player.playerX;
        defaultPlayerY = player.playerY;
    }

    //onbsluga wątków
    public void stopGameThreads(){
        isRunning = false;
    }
    public void startGameThreads(){
        if(!isRunning){
            isRunning = true;
        }

        uiManager.resetTimer();

        timerThread = new Thread(TimerRunnable);
        timerThread.start();

        animationThread = new Thread(AnimationRunnable);
        animationThread.start();

        modeSwitchThread = new Thread(ModeSwitchRunnable);
        modeSwitchThread.start();

        powerUpThread = new Thread(PowerUpRunnable);
        powerUpThread.start();

        UiThread = new Thread(UiRunnable);
        UiThread.start();

        ghostThread1 = new Thread(new GhostRunnable(ghost1));
        ghostThread1.start();

        ghostThread2 = new Thread(new GhostRunnable(ghost2));
        ghostThread2.start();

        ghostThread3 = new Thread(new GhostRunnable(ghost3));
        ghostThread3.start();

        ghostThread4 = new Thread(new GhostRunnable(ghost4));
        ghostThread4.start();
    }
    Runnable UiRunnable = () -> {
        while (isRunning) {

            removeForPowers();
            map.calculateTilt();

            if (uiManager.pause){
                uiManager.getPauseLabel().setPaused(true);
                uiManager.getInfoLabel().InfoPaused();
            }else{
                uiManager.getInfoLabel().InfoUnpaused();
                uiManager.getPauseLabel().setPaused(false);
            }

            if (player.playerLifes == 0) {
                uiManager.getEndScreenLabel().setGameOver(true);
                freezeAll();
                SwingUtilities.invokeLater(() -> {
                    if(!isGameOverWindow){
                        GameOverWindow gameOverWindow = new GameOverWindow(GameManager.this, playerScore, frame);
                        gameOverWindow.setVisible(true);
                        stopGameThreads();
                        isGameOverWindow = true;
                    }
                });
            } else {
                uiManager.getEndScreenLabel().setGameOver(false);
            }

            uiManager.getScoreLabel().setText("Score: " + playerScore);
        }
    };
    Runnable AnimationRunnable = () -> {
        while (isRunning){
            try{
                Thread.sleep(1000/(int)FPS);

                if(!uiManager.pause && !isFrozen){
                    player.updatePlayerPosition();
                }
                if(!isFrozen) {
                    repaint();
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    };
    Runnable TimerRunnable = () -> {
        while (isRunning) {
            try {
                Thread.sleep(100);
                if(!isFrozen) uiManager.updateTimer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
    Runnable ModeSwitchRunnable = () -> {
        while (isRunning){
            try{
                if(!player.swordPowerActive) Thread.sleep(5000);
                Thread.sleep(10);
                switchGhostMode();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    };
    Runnable PowerUpRunnable = () -> {
        while (isRunning){
            try{
                Thread.sleep(5000);
                if(!uiManager.pause){
                    generatePower();
                    if(powerCount == 1 && allowToGenerate) addPower((ghost1.ghostX - map.xTilt) / map.tileSize, (ghost1.ghostY - map.yTilt) / map.tileSize); // to zmiany na normalne wartosci zamiast 200
                    generatePower();

                    if(powerCount == 1 && allowToGenerate) addPower((ghost2.ghostX - map.xTilt) / map.tileSize, (ghost2.ghostY - map.yTilt) / map.tileSize); // to zmiany na normalne wartosci zamiast 200

                    generatePower();
                    if(powerCount == 1 && allowToGenerate) addPower((ghost3.ghostX - map.xTilt) / map.tileSize, (ghost3.ghostY - map.yTilt) / map.tileSize); // to zmiany na normalne wartosci zamiast 200

                    generatePower();
                    if(powerCount == 1 && allowToGenerate) addPower((ghost4.ghostX - map.xTilt) / map.tileSize, (ghost4.ghostY - map.yTilt) / map.tileSize);
                }
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    };
    private class GhostRunnable implements Runnable{
        private Ghost ghost;
        public GhostRunnable(Ghost ghost){
            this.ghost = ghost;
        }

        public void run(){
            while (isRunning) {
                try {
                    collisionManager.handleGhostPlayerCollision(ghost, player);
                    if(collisionManager.collisionGhostPlayer){
                        if(player.playerLifes == 3) heartPanel.remove(heartLabel1);
                        if(player.playerLifes == 2) heartPanel.remove(heartLabel2);
                        if(player.playerLifes == 1) heartPanel.remove(heartLabel3);

                        player.playerLifes--;
                        resetGhostPosition();
                        resetPlayerPosition();
                    }

                    if(ghost.mode == 1){
                        if(!uiManager.pause){
                            ghost.updateGhostPositionScatter();
                        }
                        Thread.sleep(20);
                    }

                    if(ghost.mode == 2){
                        if(!uiManager.pause){
                            ghost.calculateGhostMoveHunt();
                            ghost.updateGhostPositionHunt();
                        }
                        Thread.sleep(20);
                    }

                    if(ghost.mode == 3){
                        if(!uiManager.pause){
                            ghost.calculateGhostMoveRun();
                            ghost.updateGhostPositionRun();
                        }
                        Thread.sleep(20);
                    }
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }
    private void switchGhostMode() {
        int newMode = 1;

        if (ghost1.getMode() == 1 || ghost1.getMode() ==3 && !player.swordPowerActive) {
            newMode = 2;
        } else if(ghost1.getMode() == 2 && !player.swordPowerActive){
            newMode = 1;
        }
        ghost1.setMode(newMode);
        ghost2.setMode(newMode);
        ghost3.setMode(newMode);
        ghost4.setMode(newMode);
    }

    // obsluga powerup
    private void generatePower(){
        powerCount = (int)(Math.random()* 4 + 1);
    }
    private void addPower(int x, int y) {
        if(!powerExists(x, y)){
            rand = (int)(Math.random() * 5 + 4);
            drawnPowers.add(new int[]{x, y, rand});
            map.powerTiles[y][x] = rand;

        }
    }
    private void drawPower(Graphics graphics, int x, int y, int power){
        graphics.drawImage(map.tileContainer[power], map.cordsArrX[x] + tileSize/2 - map.itemSize/2, map.cordsArrY[y] + tileSize/2 - map.itemSize/2 ,map.itemSize, map.itemSize, null);

    }
    private void removeForPowers(){

        int x = (player.playerX -map.xTilt)/map.tileSize;
        int y = (player.playerY -map.yTilt)/map.tileSize;

        for(int i=0; i< drawnPowers.size(); i++){
            int[] power = drawnPowers.get(i);

            if(power[0] == x && power[1] == y){
                drawnPowers.remove(i);
            }
            thunderPower();
            swordPower();
            switchModeRun();
            heartPower();
            X2Power();
            watchPower();
        }

        if(map.powerTiles[y][x] != 0){
            map.powerTiles[y][x] =0;
        }

    }
    private void heartPower(){

        int x = (player.playerX - map.xTilt)/ map.tileSize;
        int y = (player.playerY - map.yTilt)/ map.tileSize;

        if(map.powerTiles[y][x] == 4 && player.playerLifes < 3){
            if(player.playerLifes == 2) {
                player.playerLifes = 3;
                heartPanel.add(heartLabel1);
            }
            if(player.playerLifes == 1) {
                player.playerLifes = 2;
                heartPanel.add(heartLabel2);
            }
        }
    }
    private void thunderPower() {
        int x = (player.playerX - map.xTilt) / map.tileSize;
        int y = (player.playerY - map.yTilt) / map.tileSize;

        if (map.powerTiles[y][x] == 5 && !player.thunderPowerActive ) {
            player.playerSpeed = 7;
            player.thunderPowerActive = true;
            player.thunderActivatedTime = System.currentTimeMillis();
        }

        if (player.thunderPowerActive) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - player.thunderActivatedTime >= 3000) { //3 sekundy
                player.playerSpeed = 4;
                player.thunderPowerActive = false;
            }
        }
    }
    private void swordPower(){
        int x = (player.playerX - map.xTilt) / map.tileSize;
        int y = (player.playerY - map.yTilt) / map.tileSize;

        if (map.powerTiles[y][x] == 6 && !player.swordPowerActive ) {
            player.swordPowerActive = true;
            player.swordActivatedTime = System.currentTimeMillis();
        }

        long currentTime = System.currentTimeMillis();
        if(currentTime - player.swordActivatedTime >= 2500  && player.swordPowerActive){
            player.swordPowerActive =false; // 2,5 sekundy
        }
    }
    private void switchModeRun(){
        if(player.swordPowerActive){
            int newMode = 3;
            ghost1.setMode(newMode);
            ghost2.setMode(newMode);
            ghost3.setMode(newMode);
            ghost4.setMode(newMode);
        }
    }
    private void X2Power(){
        int x = (player.playerX - map.xTilt) / map.tileSize;
        int y = (player.playerY - map.yTilt) / map.tileSize;

        if (map.powerTiles[y][x] == 7 && !player.X2PowerActive ) {
            player.X2PowerActive = true;
            player.X2ActivatedTime = System.currentTimeMillis();
        }

        long currentTime = System.currentTimeMillis();
        if(currentTime - player.X2ActivatedTime >=5000  && player.X2PowerActive){
            player.X2PowerActive = false;
        }
    }
    private void watchPower(){
        int x = (player.playerX - map.xTilt) / map.tileSize;
        int y = (player.playerY - map.yTilt) / map.tileSize;

        if(map.powerTiles[y][x] == 8 && !player.watchPowerActive ) {
            player.watchPowerActive = true;
            allowToGenerate = false;
            player.watchActivatedTime = System.currentTimeMillis();
        }

        long currentTime = System.currentTimeMillis();

        if(currentTime - player.watchActivatedTime >=2000  && player.watchPowerActive){
            player.watchPowerActive = false;
            allowToGenerate = true;
        }

        if(player.watchPowerActive){
            ghost1.ghostSpeed = 0;
            ghost2.ghostSpeed = 0;
            ghost3.ghostSpeed = 0;
            ghost4.ghostSpeed = 0;
        }else{
            ghost1.ghostSpeed = ghost.ghostSpeed;
            ghost2.ghostSpeed = ghost.ghostSpeed;
            ghost3.ghostSpeed = ghost.ghostSpeed;
            ghost4.ghostSpeed = ghost.ghostSpeed;
        }

    }
    private boolean powerExists(int x, int y) {
        for (int[] power : drawnPowers) {
            if (power[0] == x && power[1] == y) {
                return true;
            }
        }
        return false;
    }

    private void freezeAll(){
        ghost1.ghostSpeed = 0;
        ghost2.ghostSpeed = 0;
        ghost3.ghostSpeed = 0;
        ghost4.ghostSpeed = 0;
        player.playerSpeed =0;
        isFrozen = true;
        player.movement.disableKeyboard();
    }

    // obluga zatrzymania gry
    private void exit() {
        frame.setContentPane(mainMenu);
        frame.pack();
        stopGameThreads();
    }
}

