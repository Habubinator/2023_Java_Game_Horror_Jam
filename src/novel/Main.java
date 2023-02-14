package novel;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Puzzle Cellar");
        window.setIconImage(null);

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setUndecorated(true);
        window.setVisible(true);
        window.pack();

        window.setLocationRelativeTo(null);
        gamePanel.startGameThread();
    }
}