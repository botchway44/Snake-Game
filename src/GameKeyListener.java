import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameKeyListener implements KeyListener {

    private final SnakeGame game;

    public GameKeyListener(SnakeGame game) {
        this.game = game;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // System.out.println("KeyEvent: " + e.getKeyCode());
        if (e.getKeyCode() == KeyEvent.VK_UP && game.velocityY != 1) {
            game.velocityX = 0;
            game.velocityY = -1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN && game.velocityY != -1) {
            game.velocityX = 0;
            game.velocityY = 1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT && game.velocityX != 1) {
            game.velocityX = -1;
            game.velocityY = 0;
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT && game.velocityX != -1) {
            game.velocityX = 1;
            game.velocityY = 0;
        }


        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            this.game.gameState = GameState.PAUSED;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
