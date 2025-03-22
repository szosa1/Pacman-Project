import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MainMenu extends JPanel{
    private ImageIcon logo;
    private final DefaultListModel<String> highScoresListModel = new DefaultListModel<>(); // model danych do przechowywania stringow w formie listy
    private JList<String> highScoresList = new JList<>(highScoresListModel); // tworzy jList, przypisuje mu model stworzony wyzej

    public MainMenu(){
        initComponents();
    }
    private void initComponents(){
        Font font = new Font("Font", Font.PLAIN, 30); // tworzy domyślną czcionke

        try {                                                                                                                               // laduje czcionke z pliku : createFont(format, path),
            font = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/PixelifySans-Regular.ttf")).deriveFont(Font.PLAIN, 30); // deriveFont tworzy nową czcionke bazując na tej pobranej
        } catch (Exception e) {
            e.printStackTrace();
        }


        // ustawienia okna
        setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(1080, 840));
        this.setBackground(Color.BLACK);


        // ustawienia panelu loga
        JPanel logoPanel= new JPanel(new GridLayout(1,1));
        logoPanel.setPreferredSize(new Dimension(1080, 300));
        logoPanel.setOpaque(false);
        try {
             logo = new ImageIcon(ImageIO.read(new File("PNGs/V2/logo(1).png"))); // logo pobrane i zapisane jako ImageIcon
        }catch (IOException e){
            e.printStackTrace();
        }
        Image originalImage = logo.getImage();
        Image scaledImage = originalImage.getScaledInstance(100, 100, Image.SCALE_FAST); // przeskalowanie loga do prawidlowego rozmiaru
        ImageIcon scaledLogo = new ImageIcon(scaledImage);


        JLabel logoLabel = new JLabel();
        logoLabel.setIcon(scaledLogo);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        logoPanel.add(logoLabel);


        //ustawienia przycisków
        JButton Button1 = new JButton("New Game");
        JButton Button2 = new JButton("High Scores");
        JButton Button3 = new JButton("Exit");

        Button1.setBackground(Color.BLACK);
        Button1.setFocusPainted(false);
        Button1.setBorder(new LineBorder(Color.BLACK));
        Button1.setFont(font);
        Button1.setForeground(Color.WHITE);

        Button2.setBackground(Color.BLACK);
        Button2.setFocusPainted(false);
        Button2.setBorder(new LineBorder(Color.BLACK));
        Button2.setFont(font);
        Button2.setForeground(Color.WHITE);

        Button3.setBackground(Color.BLACK);
        Button3.setFocusPainted(false);
        Button3.setBorder(new LineBorder(Color.BLACK));
        Button3.setFont(font);
        Button3.setForeground(Color.WHITE);

        // panel dla przyciskow
        JPanel buttonPanel = new JPanel(new GridLayout(3,1));
        Button1.setHorizontalAlignment(SwingConstants.CENTER);
        Button2.setHorizontalAlignment(SwingConstants.CENTER);
        Button3.setHorizontalAlignment(SwingConstants.CENTER);
        buttonPanel.add(Button1);
        buttonPanel.add(Button2);
        buttonPanel.add(Button3);

        //dodanie paneli do okna
        this.add(logoPanel, BorderLayout.NORTH);
        this.add(buttonPanel, BorderLayout.CENTER);

        Font finalFont = font; // w lambdzie zmienne musza byc finalne

        // dodanie akcji dla przycisku 1 (new game)
        Button1.addActionListener(event -> {
            //ustawienia okna wyboru mapy
            JFrame mapSizeFrame = new JFrame("Select Map Size");
            mapSizeFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            mapSizeFrame.setSize(800, 600);
            mapSizeFrame.setLocationRelativeTo(null);
            mapSizeFrame.setLayout(new BoxLayout(mapSizeFrame.getContentPane(), BoxLayout.Y_AXIS));
            mapSizeFrame.getContentPane().setBackground(Color.BLACK);


            JButton button1 = new JButton("Map 1 (10x12)");
            button1.setBackground(Color.BLACK);
            button1.setFont(finalFont);
            button1.setBorder(new LineBorder(Color.BLACK));
            button1.setForeground(Color.white);
            button1.setAlignmentX(Component.CENTER_ALIGNMENT);


            button1.addActionListener(event1 -> { //dodanie akcji dla przycsku 1 (mapa 1)
                loadMap("map1.txt");
                mapSizeFrame.dispose();
            });

            JButton button2 = new JButton("Map 2(10x14)");
            button2.setBackground(Color.BLACK);
            button2.setFont(finalFont);
            button2.setBorder(new LineBorder(Color.BLACK));
            button2.setForeground(Color.WHITE);
            button2.setAlignmentX(Component.CENTER_ALIGNMENT);


            button2.addActionListener(event1 -> {
                loadMap("map2.txt");
                mapSizeFrame.dispose();

            });

            JButton button3 = new JButton("Map 3 (10x16)");
            button3.setBackground(Color.BLACK);
            button3.setFont(finalFont);
            button3.setBorder(new LineBorder(Color.BLACK));
            button3.setForeground(Color.WHITE);
            button3.setAlignmentX(Component.CENTER_ALIGNMENT);


            button3.addActionListener(event1 -> {
                loadMap("map3.txt");
                mapSizeFrame.dispose();
            });


            JButton button4 = new JButton("Map 4 (12x18)");
            button4.setBackground(Color.BLACK);
            button4.setFont(finalFont);
            button4.setBorder(new LineBorder(Color.BLACK));
            button4.setForeground(Color.WHITE);
            button4.setAlignmentX(Component.CENTER_ALIGNMENT);



            button4.addActionListener(event1 -> {
                loadMap("map4.txt");
                mapSizeFrame.dispose();
            });


            JButton button5 = new JButton("Map 5 (12x22)");
            button5.setBackground(Color.BLACK);
            button5.setFont(finalFont);
            button5.setBorder(new LineBorder(Color.BLACK));
            button5.setForeground(Color.WHITE);
            button5.setAlignmentX(Component.CENTER_ALIGNMENT);


            button5.addActionListener(event1 -> {
                loadMap("map5.txt");
                mapSizeFrame.dispose();
            });

            mapSizeFrame.add(button1);
            mapSizeFrame.add(button2);
            mapSizeFrame.add(button3);
            mapSizeFrame.add(button4);
            mapSizeFrame.add(button5);
            mapSizeFrame.setVisible(true);
        });

        // dodanie akcji dla przycisku w (highscore)
        Button2.addActionListener(event -> {
            // ustawienia okna Highscores
            JFrame highScoresFrame = new JFrame("High Scores");
            highScoresFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            highScoresFrame.setSize(1000, 800);
            highScoresFrame.setLocationRelativeTo(null);

            JPanel scoresPanel = new JPanel(new GridLayout(1, 1)); // panel dla wynikow
            scoresPanel.setBackground(Color.BLACK);


            JScrollPane scrollPane = new JScrollPane(highScoresList); // pasek przewijania dla listy wynikow
            highScoresList.setFont(finalFont);
            highScoresList.setBackground(Color.BLACK);
            highScoresList.setForeground(Color.WHITE);

            scrollPane.setBackground(Color.BLACK);
            scrollPane.setForeground(Color.WHITE);
            scoresPanel.add(scrollPane);

            highScoresFrame.add(scoresPanel);
            highScoresFrame.setVisible(true);

            updateHighScores(); // aktualizacja wynikow
        });

        //zamyka okno po klinieciu
        Button3.addActionListener(event -> System.exit(0));
    }

    // laduje mape i uruchamia gre
    private void loadMap(String mapFilePath) {
        JFrame gameWindow = new JFrame(); // tworzy okno dla gry
        GameManager gameManager = new GameManager(gameWindow);
        gameManager.loadMap(mapFilePath);
        gameManager.setUpPlayer();  // ustawia gracza i duszki na mapie
        gameManager.setUpGhosts();

        gameWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameWindow.setResizable(true);
        gameWindow.add(gameManager);
        gameWindow.pack();
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setVisible(true);
        gameManager.startGameThreads(); // zaczyna wątki gry

        SwingUtilities.getWindowAncestor(this).dispose(); // zamyka poprzednie okno (main menu)
    }

    private void updateHighScores() {
        highScoresListModel.clear(); // usuwa wszytkie wyniki zeby dodac zaktualizowane

        List<PlayerScore> highScores = readScoresFromFile(); // zwraca wyniki jako lise obiektów PlayerScore

        for (PlayerScore playerScore : highScores) {
            String scoreString = String.format("%-30s %30d", playerScore.getPlayerName(), playerScore.getScore());
            highScoresListModel.addElement(scoreString);
        }
    }
    private List<PlayerScore> readScoresFromFile() {
        List<PlayerScore> highScores = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("Highscores.ser"))) {
            highScores = (List<PlayerScore>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) { // wyjątki: io - wejsie/wyjscie   classNotFound - nie znalezienie klasy podczas deserializacji
            e.printStackTrace();
        }
        return highScores;
    }
}



