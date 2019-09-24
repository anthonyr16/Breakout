
import javax.swing.JFrame;

public class BreakoutConstants extends JFrame
{
    // instance variables - replace the example below with your own
    public static final int BRICK_WIDTH = 70; // 100
    public static final int BRICK_HEIGHT = 20; // 40
    public static final int BRICK_BORDER = (int)(BRICK_WIDTH/10);
    public static final int BOARD_WIDTH = 12*BRICK_WIDTH+BreakoutConstants.BRICK_BORDER*9;
    public static final int BOARD_HEIGHT = 25*BRICK_HEIGHT;
    public static final int PADDLE_WIDTH = 2*BRICK_WIDTH;
    public static final int PADDLE_HEIGHT = BRICK_HEIGHT;
    public static final int BALL_SIZE = BRICK_HEIGHT;
    public static final int WINDOW_BORDER = 5;

}
