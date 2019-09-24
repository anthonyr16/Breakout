 

public class BoardBuilder
{
    private GamePanel _panel;
    private SmartRectangle[][] _board;
    private Ball[] _balls;
    private int numBricks;
    
    public BoardBuilder(GamePanel panel)
    {
       _panel = panel;
       _board = new SmartRectangle[7][10];
       _balls = new Ball[5]; // max 5 balls
       numBricks = 0;
    }
    
    public Ball makeBall(double x, double y, boolean active) {
        Ball ball = new Ball(java.awt.Color.GREEN, _panel);
        ball.setLocation(x, y);
        ball.setSize(BreakoutConstants.BALL_SIZE, BreakoutConstants.BALL_SIZE);
        ball.setActive(active); // ball cannot break confinement
        ball.setBorderColor(java.awt.Color.BLACK);
        return ball;
    }
    
    public SmartRectangle[][] build(int level){
        numBricks = 0;
        if(level % 2 == 0){ // level 2* level % 2 == 0
            for(int i = 0; i < 7; i++){
                for(int j = 0; j < 10; j++){
                    if( !((i == 0 || i == 6) && (j > 1 && j < 8)) && 
                    !((i == 1 || i == 5) && (j > 2 && j < 7)) && 
                    !((i > 1 && i < 5) && (j == 1 || j == 8))) {
                        SmartRectangle block = new SmartRectangle(java.awt.Color.RED);
                        block.setLocation(BreakoutConstants.BRICK_WIDTH+(j*(BreakoutConstants.BRICK_WIDTH+BreakoutConstants.BRICK_BORDER)), 
                        BreakoutConstants.BRICK_WIDTH+(i*(BreakoutConstants.BRICK_HEIGHT+BreakoutConstants.BRICK_BORDER)));
                        block.setSize(BreakoutConstants.BRICK_WIDTH, BreakoutConstants.BRICK_HEIGHT);
                        block.setBorderColor(java.awt.Color.BLACK);
                        numBricks += 1;
                        _board[i][j] = block;
                    }
                }
            }
            
        } else { // level 1*
            for(int i = 0; i < 7; i++){
                for(int j = 0; j < 10; j++){
                    if(!(i > 1 && i < 5) || !(j > 1 && j < 8)) { // creates a 6 * 3 hole)
                        SmartRectangle block = new SmartRectangle(java.awt.Color.RED);
                        block.setLocation(BreakoutConstants.BRICK_WIDTH+(j*(BreakoutConstants.BRICK_WIDTH+BreakoutConstants.BRICK_BORDER)), 
                        BreakoutConstants.BRICK_WIDTH+(i*(BreakoutConstants.BRICK_HEIGHT+BreakoutConstants.BRICK_BORDER)));
                        block.setSize(BreakoutConstants.BRICK_WIDTH, BreakoutConstants.BRICK_HEIGHT);
                        block.setBorderColor(java.awt.Color.BLACK);
                        numBricks += 1;
                        _board[i][j] = block;
                    }
                }
            }
        }
        return _board;
    }

    // add inactive balls
    // the first index is reserved for Starter Ball and will start active
    public Ball[] spawnBalls(int level) {
        if(level % 2 == 0) { // level 2* level % 2 == 0
            _balls[0] = makeBall((double)(6.5 * BreakoutConstants.BRICK_WIDTH), (double)(14 * BreakoutConstants.BRICK_HEIGHT), true);
            _balls[1] = makeBall((double)(2.5 * BreakoutConstants.BRICK_WIDTH), (double)(6.5 * BreakoutConstants.BRICK_HEIGHT), false);
            _balls[2] = makeBall((double)(10 * BreakoutConstants.BRICK_WIDTH), (double)(6.5 * BreakoutConstants.BRICK_HEIGHT), false);
        } else { // level 1*
            _balls[0] = makeBall((double)(6.5 * BreakoutConstants.BRICK_WIDTH), (double)(13 * BreakoutConstants.BRICK_HEIGHT), true);
            _balls[1] = makeBall((double)(6.5 * BreakoutConstants.BRICK_WIDTH), (double)(8 * BreakoutConstants.BRICK_HEIGHT), false);
        }
        
        return _balls;
    }
    
    // return # of balls in game at the START of the current level
    public int numBalls(int level) {
        if(level % 2 == 0) { // level 2*
            return 3;
        } else { // level 1*
            return 2;
        }
    }
    
    public int numBricks(){
        return numBricks;
    }
}
