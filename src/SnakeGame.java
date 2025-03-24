
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements Runnable {

      Thread gameThread;

    int boardWidth;
    int boardHeight;
    int tileSize = 25;


    int topPadding = 50;
    
    //snake
    Tile snakeHead;
    ArrayList<Tile> snakeBody;

    //food
    Tile food;
    Random random;

    //game logic
    int velocityX;
    int velocityY;

//    boolean gameOver = false;
    GameState gameState = GameState.NOT_STARTED;

    GameKeyListener gameKeyListener;
    GameActionListener gameActionListener;

    GameMenuManager gameMenuManager;

    int highScore = 10;

    JButton pauseButton;

    SnakeGame(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);

        setFocusable(true);

        snakeHead = new Tile(5, 5);
        snakeBody = new ArrayList<Tile>();

        food = new Tile(10, 10);
        random = new Random();
        placeFood();

        velocityX = 1;
        velocityY = 0;
        

        gameKeyListener = new GameKeyListener(this);
        addKeyListener(gameKeyListener);

        gameActionListener = new GameActionListener(this);

        this.setLayout(null);
        gameMenuManager = new GameMenuManager(this, gameActionListener);

        //Add A new Button To pause Game
        pauseButton = new JButton("Pause");
        pauseButton.addActionListener(gameActionListener);
        pauseButton.setBounds(boardWidth - 140,10,100, 20);
        this.pauseButton.addActionListener(gameActionListener);
	}

    public void initializeBoard(){
        add(pauseButton);

    }
    
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	public void draw(Graphics g) {

        if (gameState == GameState.NOT_STARTED) {
            this.gameMenuManager.draw(g);
        } else if (gameState == GameState.PLAYING) {
            drawSnakeBoard(g);
        }
        else if (gameState == GameState.PAUSED) {
            drawSnakeBoard(g);
        }

    }

    public void drawSnakeBoard(Graphics g) {

        g.setColor(Color.blue);
        //Grid Lines
        for(int i = 0; i < boardWidth/tileSize; i++) {
            //(x1, y1, x2, y2)
            g.drawLine(i*tileSize, topPadding, i*tileSize, boardHeight + topPadding);
            g.drawLine(0, (i*tileSize) + topPadding, boardWidth, (i*tileSize) + topPadding);
        }

        //Food
        g.setColor(Color.red);
        // g.fillRect(food.x*tileSize, food.y*tileSize, tileSize, tileSize);
        g.fill3DRect(food.x*tileSize, (food.y*tileSize) + topPadding, tileSize, tileSize, true);

        //Snake Head
        g.setColor(Color.orange);
        // g.fillRect(snakeHead.x, snakeHead.y, tileSize, tileSize);
        // g.fillRect(snakeHead.x*tileSize, snakeHead.y*tileSize, tileSize, tileSize);
        g.fill3DRect(snakeHead.x*tileSize, (snakeHead.y*tileSize) + topPadding, tileSize, tileSize, true);

        //Snake Body
        g.setColor(Color.green);
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            // g.fillRect(snakePart.x*tileSize, snakePart.y*tileSize, tileSize, tileSize);
            g.fill3DRect(snakePart.x*tileSize, (snakePart.y*tileSize) + topPadding, tileSize, tileSize, true);
        }

        //Score
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if (gameState == GameState.GAME_OVER) {
            g.setColor(Color.red);
            g.drawString("Game Over: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
        else {
            g.drawString("Score: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
    }

    public void placeFood(){
        food.x = random.nextInt(boardWidth/tileSize);
		food.y = random.nextInt((boardHeight- topPadding)/tileSize );
	}

    public void move() {
        //eat food
        if (collision(snakeHead, food)) {
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }

        //move snake body
        for (int i = snakeBody.size()-1; i >= 0; i--) {
            Tile snakePart = snakeBody.get(i);
            if (i == 0) { //right before the head
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            }
            else {
                Tile prevSnakePart = snakeBody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }

        if(snakeHead.x <=0){
            snakeHead.x = boardWidth/tileSize;
        }
        snakeHead.x =  (snakeHead.x + velocityX) % (boardWidth/tileSize);

        if(snakeHead.y <=0){
            snakeHead.y = ((boardHeight - topPadding)/tileSize);
        }
        snakeHead.y = (snakeHead.y + velocityY) % ((boardHeight - topPadding )/ tileSize);


        //game over conditions
        for(int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);

            //collide with snake head
            if (collision(snakeHead, snakePart)) {
                gameState = GameState.GAME_OVER;
            }
        }

    }

    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }





    public void update() {

        if (gameState == GameState.PLAYING) {
            move();
        }else if (gameState == GameState.NOT_STARTED) {

        }

    }



    public void startGameThread() {
        this.gameThread = new Thread(this);
        this.gameThread.start();
    }


    @Override
    public void run() {

        while (this.gameThread != null) {
//            System.out.println("The game loop is running");


            // Update information about the character
            this.update();
//            this.move();

            // Draw the screen with the updated information
            repaint();

            try {
                Thread.sleep((long) 100);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


}
