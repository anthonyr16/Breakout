 

public class MoveTimer extends javax.swing.Timer {
    private Mover _mover; // peer object
    /**
     * Constructor for the MoveTimer
     */
    public MoveTimer (int anInterval, Mover aMover) {
       // Instantiate the timer with a particular interval
       super(anInterval, null);
       // Save the peer object
       _mover = aMover;
       // Register a new MoveListener with the MoveTimer
       this.addActionListener(new MoveListener());
   }

   /**
    * Internal class - MoveListener
    */
   private class MoveListener implements java.awt.event.ActionListener {
       /**
        * The actionPerformed method specifies what is to me accomplished when 
        * the timer ticks
        */
        public void actionPerformed(java.awt.event.ActionEvent e){
            _mover.move();
        }
   }
}

