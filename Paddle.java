 



/**
 * Write a description of class Paddle here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Paddle extends SmartRectangle 
{
    // instance variables - replace the example below with your own
      private GamePanel _panelRef;
    public Paddle(java.awt.Color aColor, GamePanel aPanel){
        super(aColor);
        _panelRef = aPanel;
        this.setSize(2*BreakoutConstants.BRICK_WIDTH,BreakoutConstants.BRICK_HEIGHT);       
    }
    /*
    public boolean moveRight()
    {
        double x = this.getX() + 20;
        double y = this.getY();
        if(_panelRef.canMove(x,y)){
            this.setLocation(x, y);
        }
        return true;
    }
    
    public boolean moveLeft()
    {
        double x = this.getX() - 20;
       double y = this.getY();
       if(_panelRef.canMove(x,y)){
           this.setLocation(x, y);
       }
       
        return true;
    }
    */
    public boolean move(double x){
        double y = this.getY();
        if(_panelRef.canMove(x,y)){
           this.setLocation(x, y);
           return true;
        }
        return false;
    }
}
