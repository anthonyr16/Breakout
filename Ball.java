 

public class Ball extends SmartEllipse implements Mover {
    private double _changeX, _changeY; // attributes
    private double _moveLen = 1;
    protected javax.swing.Timer _timer;
    private int velocityX;
    private int velocityY;
    private GamePanel _panel;
    protected boolean _active;
    protected boolean toRemove;

    public Ball (java.awt.Color aColor, GamePanel aPanel){
        // Instantiate the JPanel
        super(aColor);
        // ball can break blocks
        _active = true;
        // Initialize the change in each direction per timer tick
        _changeX = _moveLen;
        _changeY = _moveLen;
        _panel = aPanel;
        toRemove = false;
    }
    public void setActive(boolean active) {
        _active = active;
    }
    public boolean isActive() {
        return _active;
    }
    
    // timer functions
    public void setTimer(MoveTimer timer) {
        _timer = timer;
    }
    public boolean timerRunning() {
        return _timer.isRunning();
    }
    public void stopTimer() {
        _timer.stop();
    }
    public void startTimer() {
        _timer.start();
    }
    
    public SmartRectangle checkCollision(){
        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 10; j++){
                if(_panel._board[i][j] instanceof SmartRectangle){
                    if(_panel._board[i][j].intersects(this.getBounds2D())){
                        SmartRectangle hold = _panel._board[i][j];
                        if(_active){
                           _panel.removeBrick(i,j);
                           _panel.addScore();
                        }
                        return hold;
                    }
                }
            } 
        }
        
        if(_panel._paddle.intersects(this.getBounds2D())){
            return _panel._paddle;
        }
        return null;
    }
    

    public void move() {
        double nextX = this.getX() + _changeX;
        double nextY = this.getY() +  _changeY;
        SmartRectangle check = null;
        check = checkCollision();
        
        if(check instanceof Paddle){
            if(_active == false){
                _active = true;
                _panel.newActive();
            }
            
            if(this.getCenterX() >= check.getCenterX()){
                _changeY = -1*((check.getCenterX() + check.getX() + check.getWidth())/this.getCenterX());
                if(_changeX < 0){
                    _changeX *= -1;
                }
                nextY = this.getY() + _changeY;
            }
            
            if(this.getCenterX() < check.getCenterX()){
                _changeY = -1*((check.getX() + check.getCenterX())/this.getCenterX());
                if(_changeX > 0){
                    _changeX *= -1;
                }
                nextY = this.getY() + _changeY;
            }
        }
        
        if(check != null && !(check instanceof Paddle)){
             double wy = (this.getWidth() + check.getWidth()) * (this.getCenterY() - check.getCenterY());
             double hx = (this.getHeight() + check.getHeight()) * (this.getCenterX() - check.getCenterX());
            if(wy > hx){
                if(wy > -hx){  // top
                    _changeY *= -1;
                    nextY = this.getY() + 2;
                }else{         // left 
                    _changeX *= -1;
                    nextX = this.getX() - 2;
                }
            }else{
                if(wy > -hx){  // right
                    _changeX *= -1;
                    nextX = check.getX() + BreakoutConstants.BRICK_WIDTH + 2;
                }else{         // bottom
                    _changeY *= -1;
                    nextY = this.getY() - 2  ;
                }
            }
        }
        
        if (nextX <= this.getMinBoundX()) {
            _changeX *= -1;
            nextX = this.getMinBoundX();
        }
        else if (nextX >= this.getMaxBoundX()) {
            _changeX *= -1;
            nextX = this.getMaxBoundX();
        }
        if (nextY <= this.getMinBoundY()) {
            _changeY *= -1;
            nextY  = this.getMinBoundY();
        }
        else if (nextY > this.getMaxBoundY()){
            this.remove();
            nextX = 0;
            nextY = 0;
            _panel.removeBall(this);
        }
        this.setLocation(nextX, nextY);
        _panel.repaint();
    } 
    
    public void remove(){
        this.setMoveLength(0);
        this.setLocation(0, 0);
        stopTimer();
    }
    
    public double getMinBoundX() {
        return _panel.getX() + BreakoutConstants.WINDOW_BORDER;
    }
    public double getMinBoundY() {
        return _panel.getY() + BreakoutConstants.WINDOW_BORDER;
    }
    public double getMaxBoundX() {
        return (_panel.getX() + _panel.getWidth() - this.getWidth() - BreakoutConstants.WINDOW_BORDER);
    }
    public double getMaxBoundY() {
        return (_panel.getY() + _panel.getHeight() - this.getHeight() - BreakoutConstants.WINDOW_BORDER);
    }
    public void setMoveLength(double value)
    {
        _moveLen = value;
        if(_changeX >=0) 
            _changeX = value;
        else
            _changeX = value * -1;
        if(_changeY >=0)
            _changeY = value;
        else
            _changeY = value * -1;
   }
   
    // getter and setter methods for the velocities in X and Y directions
    public void setVelocityX(int newX) {
        velocityX = newX;
    }
    
    public void setVelocityY(int newY) {
        velocityY = newY;
    }
    
    public int getVelocityX() {
        return velocityX;
    }
    
    public int getVelocityY() {
        return velocityY;
    }
    
    public void collision()
    {
    }
}