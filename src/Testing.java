import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

public class Testing {
/*
 * SMC: Simple Messaging Client
 * Written by Gabe Ehrlich
 */
	public static void main(String[] args) {
		String username=JOptionPane.showInputDialog("Please enter your username");
		GUI gui=new GUI(username);
		JFrame frame=new JFrame();
		frame.getContentPane().add(gui);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setTitle("SMC");
		frame.setSize(835,660);
		frame.setVisible(true);
	}

}
