
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;
import java.awt.Color;
import javax.swing.border.*;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import javax.sound.sampled.*;
import java.io.*;

public class GamePanel extends JPanel implements ActionListener, MouseMotionListener
{
    // instance variables - replace the example below with your own
    private Timer _timer;
    protected Paddle _paddle;
    private Ball[] _balls = new Ball[5];
    private int _numBalls;
    private int score;
    private int lives;
    protected SmartRectangle[][] _board = new SmartRectangle[7][10];
    private KeyInteractor _spacekey;
    private KeyInteractor _pausekey;
    private KeyInteractor _rightListener;
    private BoardBuilder _builder;
    private int _level;
    private int _blocksLeft;
    private int[] _totalBlocks = {52, 44}; // stores # bricks per level
    private MouseListener mousetracker;
    private boolean gameStarted;
    private JLabel scoreLabel;
    private JLabel livesLabel;
    private JLabel startText;
    private JLabel pauseText;
    private int activeBalls;
    private boolean reset = false;
    private boolean isGameover = false;
    private int numBricks;
    private Audio BGM;
    
    
    
    public GamePanel()
    {
        this.setBackground(Color.WHITE);
        this.setBorder(new LineBorder(Color.BLACK, BreakoutConstants.WINDOW_BORDER)); 
        _spacekey = new KeySpaceListener(this);
        _pausekey = new KeyPListener(this);
        addMouseMotionListener(this);
        BGM = new Audio("music.wav");
        // score related stuff
        score = 0;
        lives = 3;
        startText = new JLabel("Press SPACE to start");
        startText.setFont(new Font("Serif", Font.BOLD, 2*BreakoutConstants.BRICK_HEIGHT));
        scoreLabel = new JLabel("Score = 0");
        livesLabel = new JLabel("Lives = 3");
        pauseText = new JLabel("Paused");
        pauseText.setFont(new Font("Serif", Font.BOLD, 2*BreakoutConstants.BRICK_HEIGHT));
        setLayout(null); 
        this.add(startText);
        this.add(scoreLabel);
        this.add(livesLabel);
        this.add(pauseText);
        pauseText.setVisible(false);
        startText.setBounds((BreakoutConstants.BOARD_WIDTH/3), (BreakoutConstants.BOARD_HEIGHT - 3*BreakoutConstants.BRICK_WIDTH), 500, 2*BreakoutConstants.BRICK_HEIGHT);
        pauseText.setBounds((BreakoutConstants.BOARD_WIDTH/3)+ BreakoutConstants.BRICK_WIDTH, (BreakoutConstants.BOARD_HEIGHT - 3*BreakoutConstants.BRICK_WIDTH), 500, 2*BreakoutConstants.BRICK_HEIGHT);
        scoreLabel.setBounds(20, 20, 500, 10);
        livesLabel.setBounds(20, 40, 500, 10);
        activeBalls = 1;

        // use Builder to create the board and balls
        _builder = new BoardBuilder(this);
        _level = 1;
        _blocksLeft = _totalBlocks[_level%2 - 1]; // sets # blocks for this level
        _board = _builder.build(_level);
        _balls = _builder.spawnBalls(_level);
        _numBalls = _builder.numBalls(_level);
        numBricks = brickCount();
        
        // create paddle
        _paddle = new Paddle(java.awt.Color.BLUE, this);
        _paddle.setLocation(BreakoutConstants.BOARD_WIDTH/2-BreakoutConstants.BRICK_WIDTH, BreakoutConstants.BOARD_HEIGHT - (3*BreakoutConstants.BRICK_HEIGHT));
        _paddle.setBorderColor(java.awt.Color.BLACK);

        gameStarted = false;
    }
    
    public void paintComponent(java.awt.Graphics aBrush){
        super.paintComponent(aBrush);
        java.awt.Graphics2D betterBrush = (java.awt.Graphics2D)aBrush;
        // paint the bricks
        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 10; j++){
                if(_board[i][j] instanceof SmartRectangle){
                    _board[i][j].fill(betterBrush);
                    _board[i][j].draw(betterBrush);
                }
            }
        }
        // paint the balls
        for(int i = 0; i < 5; i++) {
            if(_balls[i] instanceof SmartEllipse) {
                _balls[i].fill(betterBrush);
                _balls[i].draw(betterBrush);
            }
        }
        // paint the paddle
        _paddle.fill(betterBrush);
        _paddle.draw(betterBrush);    
    }
    
    // go to next level (reset board)
    public void resetLevel() {
        gameStarted = false;
        activeBalls = 1;
        _level = _level%2 + 1; // next level in cycle
        for(int i = 0; i < 5; i++){
            if(_balls[i] instanceof Ball){
                _balls[i].stopTimer();
                _balls[i].remove();
                _balls[i] = null; 
            }                     
        }
        // rebuild the board
        //_blocksLeft = _totalBlocks[_level]; 
        _paddle.setLocation(BreakoutConstants.BOARD_WIDTH/2-BreakoutConstants.BRICK_WIDTH, BreakoutConstants.BOARD_HEIGHT - (3*BreakoutConstants.BRICK_HEIGHT));
        _board = _builder.build(_level);
        _balls = _builder.spawnBalls(_level);
        _numBalls = _builder.numBalls(_level);
        numBricks = brickCount();
        // repaint the board
        this.repaint();
    }
    
    public int brickCount(){
        int bricks = _builder.numBricks();
        return bricks;
    }
    
    //This is used for just paddle right now
    public boolean canMove(double x, double y){
        if(x < 5 || x > (BreakoutConstants.BOARD_WIDTH+(BreakoutConstants.BRICK_BORDER*9) - _paddle.getWidth() - 5)){
            return false;
        }else{
            return true;
        }        
    }
    
    public void startGame(){
        BGM.loop();
        if(gameStarted == false && !isGameover){
            for(int i = 0; i < 5; i++){
                if(_balls[i] instanceof Ball){
                    _balls[i].setTimer(new MoveTimer(6, _balls[i]));
                    _balls[i].startTimer();
                }
            }
            this.remove(startText);
            repaint();
            gameStarted = true;
        }
    }
    
    public void removeBall(Ball toRemove){
        for(int i = 0; i < 5; i++){
            if(_balls[i] == toRemove){
                _balls[i] = null;
            }           
        }
        if(toRemove._active){  
            activeBalls -= 1;
        }
        if(activeBalls == 0){
            for(int i = 0; i < 5; i++){
                if(_balls[i] instanceof Ball){
                    _balls[i].stopTimer();
                }
            }
        reset();
        }
    }
    
    public void removeBrick(int x, int y){
        _board[x][y] = null;
        numBricks -= 1;
        if(numBricks == 0){
            resetLevel();
        }
    }

    public void reset(){
        if(lives == 0){
            gameOver();
        }else{
            lives -= 1;
            String display = "Lives = " + lives;
            livesLabel.setText(display);
            _balls[0] = _builder.makeBall((double)(6.5 * BreakoutConstants.BRICK_WIDTH), (double)(13 * BreakoutConstants.BRICK_HEIGHT), true);
            activeBalls = 1;
            _balls[0]._timer = new MoveTimer(5, _balls[0]);
            reset = true;
        }
                
    }
    
    public void gameOver(){
        BGM.stop();
        isGameover = true;
        gameStarted = false;
        JLabel gameover = new JLabel("Game Over");
        gameover.setFont(new Font("Serif", Font.BOLD, 2*BreakoutConstants.BRICK_HEIGHT));
        this.add(gameover);
        gameover.setBounds((BreakoutConstants.BOARD_WIDTH/3), (BreakoutConstants.BOARD_HEIGHT - 3*BreakoutConstants.BRICK_WIDTH), 500, 2*BreakoutConstants.BRICK_HEIGHT);

    }

    public void addScore()
    {
        score = score += _level * 100;
        String display = "Score = " + score;
        scoreLabel.setText(display);
    }
    
    public void newActive(){
        activeBalls += 1;
    }

    
    public void actionPerformed(ActionEvent e)
    {
    }
    
    private class KeySpaceListener extends KeyInteractor
    {
        public KeySpaceListener(JPanel p)
        {
            super(p,KeyEvent.VK_SPACE);
        }
        public void actionPerformed(ActionEvent e){
            startGame();
            if(reset && !isGameover){
                reset = false;
                _balls[0]._timer.start();
                for(int i = 0; i < 5; i++){
                    if(_balls[i] instanceof Ball){
                        _balls[i].startTimer();
                    }
                }
            }
        }
    }
    
    private class KeyPListener extends KeyInteractor 
    {
        public KeyPListener(JPanel p)
        {
            super(p,KeyEvent.VK_P);
        }
        
        public  void actionPerformed (ActionEvent e) {
            if(gameStarted && !isGameover && !reset){
                for(int i = 0; i < 5; i++){
                    if(_balls[i] instanceof Ball && _balls[i].timerRunning()){
                        _balls[i].stopTimer();
                        pauseText.setVisible(true);
                    }else if(_balls[i] instanceof Ball){
                        pauseText.setVisible(false);
                        _balls[i].startTimer();
                    }
                }
            }
        }
    }
    
    public void mouseMoved(MouseEvent e){
        if(gameStarted && !pauseText.isVisible()){
            double x = e.getX();
            _paddle.move(x);
            repaint();
        }
    }
    
    public void mouseDragged(MouseEvent e){}
}


