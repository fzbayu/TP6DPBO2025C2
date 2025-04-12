import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {

    int frameWidth = 360;
    int frameHeight = 640;

    // image attributes
    Image backgroundImage;
    Image birdImage;
    Image lowerPipeImage;
    Image upperPipeImage;
    Image buttonScore;

    // player
    int playerStartPosX = frameWidth / 8;
    int playerStartPosY = frameHeight / 2;
    int playerWidth = 34;
    int playerHeight = 24;
    Player player;

    // Pipe attributes
    int pipeStartPosX = frameWidth;
    int pipeStartPosY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;
    ArrayList<Pipe> pipes;

    // game over
    boolean gameOver = false;

    // fitur score
    int score = 0;
    JLabel scoreLabel;

    // game logic
    Timer gameloop;
    Timer pipesCooldown;
    int gravity = 1;

    // constructor
    public FlappyBird(JLabel scoreLabel) {
        this.scoreLabel = scoreLabel; // untuk score

        setPreferredSize(new Dimension(frameWidth, frameHeight));
        setFocusable(true);
        addKeyListener(this);

        // setBackground(Color.blue);

        // load image
        backgroundImage = new ImageIcon(getClass().getResource("assets/background.png")).getImage();
        birdImage = new ImageIcon(getClass().getResource("assets/bird.png")).getImage();
        lowerPipeImage = new ImageIcon(getClass().getResource("assets/lowerPipe.png")).getImage();
        upperPipeImage = new ImageIcon(getClass().getResource("assets/upperPipe.png")).getImage();

        player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, birdImage);
        pipes = new ArrayList<Pipe>();

        pipesCooldown = new Timer(4000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("pipe");
                placePipes();
            }
        });

        gameloop = new Timer(1000/60, this);
        gameloop.start();
        pipesCooldown.start();

    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g); // sangat penting untuk clear layar
        g.drawImage(backgroundImage, 0 , 0, frameWidth, frameHeight, null);
        g.drawImage(player.getImage(), player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight(), null);

        for (int i = 0; i < pipes.size(); i++){
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.getImage(), pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeight(), null);
        }

        // jika game over maka tampilkan tulisan GAME OVER
        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("GAME OVER", frameWidth / 4 - 20, frameHeight / 2);
        }
    }


    public void draw(Graphics g){
        g.drawImage(backgroundImage, 0 , 0, frameWidth, frameHeight, null);
        g.drawImage(player.getImage(), player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight(), null);

        for (int i = 0; i < pipes.size(); i++){
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.getImage(), pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeight(), null);
        }
    }

    public void move(){
        player.setVelocityY(player.getVelocityY() + gravity);
        player.setPosY(player.getPosY() + player.getVelocityY());


        for (int i = 0; i < pipes.size(); i++){
            Pipe pipe = pipes.get(i);
            pipe.setPosX(pipe.getPosX() + pipe.getVelocityX());
        }

        for (int i = 0; i < pipes.size(); i += 2) {
            Pipe lowerPipe = pipes.get(i + 1);

            // Jika pipa dilewati oleh burung (kanan pipa < kiri burung), dan belum dihitung
            if (!lowerPipe.isPassed && lowerPipe.getPosX() + lowerPipe.getWidth() < player.getPosX()) {
                lowerPipe.isPassed = true;
                score++;
                scoreLabel.setText("Score: " + score);
            }
        }
    }

    public void placePipes(){
        int randomPosY = (int) (pipeStartPosY - pipeHeight/4 - Math.random() * (pipeHeight/2));
        int openingSpace = frameHeight/4;

        Pipe upperPipe = new Pipe(pipeStartPosX, randomPosY, pipeWidth, pipeHeight, upperPipeImage);
        pipes.add(upperPipe);

        Pipe lowerPipe = new Pipe(pipeStartPosX, (randomPosY + openingSpace + pipeHeight), pipeWidth, pipeHeight, lowerPipeImage);
        pipes.add(lowerPipe);
    }

    public void cekTabrak() {
        Rectangle playerRect = new Rectangle(player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight());

        // cek apakah menabrak pipa
        for (Pipe pipe : pipes) {
            Rectangle pipeRect = new Rectangle(pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeight());

            if (playerRect.intersects(pipeRect)) {
                gameOver = true;
            }
        }

        // cek jatuh ke bawah atau terbang ke atas
        if (player.getPosY() + player.getHeight() > frameHeight || player.getPosY() < 0) {
            gameOver = true;
        }

        if (gameOver) {
            System.out.println("Game Over!");
            gameloop.stop();
            pipesCooldown.stop();
        }
    }

    public void resetGame() {
        player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, birdImage);
        pipes.clear();
        score = 0;
        gameOver = false;
        scoreLabel.setText("Score: 0");

        if (gameloop != null) gameloop.start();
        if (pipesCooldown != null) pipesCooldown.start();
    }


    @Override
    public void actionPerformed(ActionEvent e){
        if (!gameOver) {
            move();
            cekTabrak();
            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e){

    }

    @Override
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            player.setVelocityY(-10);
        }

        if (e.getKeyCode() == KeyEvent.VK_R && gameOver){
            resetGame();
        }

    }

    @Override
    public void keyReleased(KeyEvent e){

    }




}
