import javax. swing.*;
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->{
            JFrame window = new JFrame();
            MainMenu MainMenu = new MainMenu();

            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setResizable(true);
            window.setTitle("PacMan");

            window.add(MainMenu);
            window.pack();

            window.setLocationRelativeTo(null);
            window.setVisible(true);
        });
    }
}