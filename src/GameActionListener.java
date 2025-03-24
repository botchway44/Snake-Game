import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameActionListener implements ActionListener {

    SnakeGame game;
    public GameActionListener(SnakeGame game) {
        this.game = game;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());


        if(e.getActionCommand().equals("Start Game")){
            this.game.removeAll();
            this.game.repaint();
            this.game.initializeBoard();
            this.game.gameState = GameState.PLAYING;


        }
        if(e.getActionCommand().equals("Pause")){
            this.game.gameState = GameState.PAUSED;

            this.game.pauseButton.setText("Play");
            this.game.repaint();
        }
        if(e.getActionCommand().equals("Play")){
            this.game.gameState = GameState.PLAYING;
            this.game.pauseButton.setText("Pause");
            this.game.repaint();
        }

    }
}
