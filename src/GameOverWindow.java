import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GameOverWindow extends JFrame implements Serializable {
    private static final long serialVersionUID = 1;  // zarzadzenie wersjami klasy podczas serializacji i deserializacji
    private GameManager gameManager;
    private JTextField nameField;
    private int score;
    private JFrame gameWindow;

    public GameOverWindow(GameManager gameManager, int score, JFrame gameWindow) {  //Jframe gameWindow - referencja do okna gry
        this.gameManager = gameManager;
        this.score = score;
        this.gameWindow = gameWindow;
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        //ustawienia panelu
        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.setBackground(Color.BLACK);

        // ustawienia dla tekstu
        JLabel text = new JLabel("Enter your name:");
        text.setHorizontalAlignment(SwingConstants.CENTER);
        text.setForeground(Color.white);
        panel.add(text);

        //ustawienia pola tekstowego
        nameField = new JTextField();
        nameField.setBackground(Color.BLACK); // ramka na czarno
        nameField.setForeground(Color.WHITE); // tekst na biały
        nameField.setBorder(new LineBorder(Color.darkGray));
        panel.add(nameField);

        //ustawienia przycisku
        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(Color.black);
        submitButton.setForeground(Color.white);
        panel.add(submitButton);

        add(panel);

        //obsluga przecisku
        submitButton.addActionListener(e -> {
            String playerName = nameField.getText().trim(); // bierze tekst z nameField, usuwa spacje na koncu, sprawdza czy puste
            if (!playerName.isEmpty()) {
                PlayerScore scores = new PlayerScore(playerName, gameManager.playerScore);
                saveScoreToFile(scores);
                dispose();
                gameWindow.dispose();

                SwingUtilities.invokeLater(() -> {// tworzy nowe okno dla mainMenu
                    JFrame mainMenuFrame = new JFrame("Main Menu");
                    mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    mainMenuFrame.setSize(1080, 840);
                    mainMenuFrame.setLocationRelativeTo(null);
                    mainMenuFrame.add(new MainMenu()); // dodanie instancji MainMenu
                    mainMenuFrame.setVisible(true);
                });
            } else { // jak jest puste
                JOptionPane.showMessageDialog(GameOverWindow.this, "Name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void saveScoreToFile(PlayerScore playerScore) {
        List<PlayerScore> highScores = readScoresFromFile(); // calosc pliku daje do listy
        highScores.add(playerScore); // do listy dodaje aktualny wynik z nazwa
        highScores.sort((s1, s2) -> Integer.compare(s2.getScore(), s1.getScore())); // sortuje

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Highscores.ser"))) { // zapisanie do pliku
            oos.writeObject(highScores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // odczyt aktualnych, po to aby doodac nowy wynik do listy, a potem calosc do pliku
    private List<PlayerScore> readScoresFromFile() {
        List<PlayerScore> scores = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Highscores.ser"))) {
            scores = (List<PlayerScore>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return scores;
    }
}
