import javax.swing.*;
import javax.swing.event.AncestorListener;
import java.awt.*;

public class GameMenuManager {

    JPanel menuPanel;

    private final SnakeGame snakeGame;
    private GameActionListener gameActionListener;
    public GameMenuManager(SnakeGame snakeGame, GameActionListener gameActionListener) {
        this.snakeGame = snakeGame;
        this.gameActionListener = gameActionListener;

    }

    public void draw(Graphics g) {
//
        menuPanel = new JPanel();

        menuPanel.setBounds(100,100,300, 100);
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.setAlignmentY(Component.CENTER_ALIGNMENT);

        menuPanel.add(new JLabel("Game Menu"));

            JButton startbutton = new JButton("Start Game");
            menuPanel.add(startbutton);
            startbutton.addActionListener(this.gameActionListener);


//        menuPanel.add(new JButton("View High Score"));


        this.snakeGame.add(menuPanel);
        this.snakeGame.repaint();
        this.snakeGame.revalidate();
    }


}
