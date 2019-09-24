 

import javax.swing.JFrame;
import javax.swing.JPanel;


public class BreakoutApp extends JFrame
{
   private GamePanel _gamePanel;

    
    public BreakoutApp()
    {
         super("Breakout");
         this.setResizable(false);
         _gamePanel = new GamePanel();
         _gamePanel.setPreferredSize(new java.awt.Dimension(BreakoutConstants.BOARD_WIDTH, BreakoutConstants.BOARD_HEIGHT));
         this.setSize(BreakoutConstants.BOARD_WIDTH, BreakoutConstants.BOARD_HEIGHT);
         this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
         this.add(_gamePanel);  
         this.setVisible(true);
    }

   
    public static void main(String[] args)
    {
        BreakoutApp app = new BreakoutApp();
    }
}
