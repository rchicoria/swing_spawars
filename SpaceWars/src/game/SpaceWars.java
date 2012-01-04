package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
 
import javax.swing.JFrame;
import javax.swing.Timer;
import levels.*;


/**
 * @author João Claro
 * @author Ricardo Lopes
 * @author Rui Chicória
 */

@SuppressWarnings("serial")
public class SpaceWars extends JFrame implements KeyListener, LevelOneCommons {
        
        private ContainerBox box;
        private Level level;
        private ArrayList<Integer> keys = new ArrayList<Integer>();
        private BufferStrategy buffer;
 
 
	public static void main(String[] args) {
		new SpaceWars();
	}
 
	public SpaceWars()
        {
            // Init the Container Box to fill the screen
            box = ContainerBox.getInstance();
            box.set(0, 0, BOARD_WIDTH, 480);
            
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    // Exit on Close
            this.setSize(BOARD_WIDTH, 600);                // Window Size
            this.setResizable(false);                               // Non Resizable
            this.setLocationRelativeTo(null);                       // Centers
            this.setVisible(true);                                  // Is visible
            this.createBufferStrategy(2);
            this.addKeyListener(this);                              // Accept key control

            buffer = this.getBufferStrategy();
            level = new LevelOne(buffer);

            ActionListener actionListener = new ActionListener()
            {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent)
                    {
                        if(level.loop(keys).equalsIgnoreCase("ok"))
                            level.draw();
                        else if(level.loop(keys).equalsIgnoreCase("lost"))
                            level = new LevelOne(buffer);
                        else
                            level = new LevelTwo(buffer);

                    }
            };
            
            Timer timer = new Timer(1000/UPDATE_RATE, actionListener);
            timer.start();
        }
	

	@Override
	public void keyPressed(KeyEvent e) {
            //System.out.println(e.getKeyChar());
		if(!keys.contains(new Integer(e.getKeyCode())))
			keys.add(new Integer(e.getKeyCode()));
	}
	 
	@Override
	public void keyReleased(KeyEvent e) { 
		keys.remove(new Integer(e.getKeyCode()));
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}