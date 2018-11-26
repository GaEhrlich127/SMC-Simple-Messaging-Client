import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Testing {

	static GUI gui;
	static JFrame frame;
	
	public static void main(String[] args) {
		guiSetup();

	}
	
	private static void guiSetup() {
		String username=JOptionPane.showInputDialog("Please enter your username");
		gui=new GUI();
		frame=new JFrame();

		addGUIButtons(username);

		frame.getContentPane().add(gui);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setTitle("SMC Server: "+username);
		frame.setSize(880,660);
		frame.setVisible(true);
	}

	private static void addGUIButtons(String username) {//This will add the functionality to hitting enter and/or the button
		gui.getMessageField().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				gui.setMSG("\n["+LocalTime.now()+"] "+username+": "+gui.getMessageField().getText());
				gui.addText(gui.getMSG());
				gui.getMessageField().setText("");
			}
		});
		gui.getTextSubmit().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//Submit pressed
				gui.setMSG("\n["+LocalTime.now()+"] "+username+": "+gui.getMessageField().getText());
				gui.addText(gui.getMSG());
				gui.getMessageField().setText("");
			}
		});
		gui.getImageSubmit().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser fc=new JFileChooser();
				fc.showOpenDialog(gui);
				File f=fc.getSelectedFile();
				BufferedImage bi;
				try {
					bi=ImageIO.read(f);
					JFrame frame=new JFrame();
					JLabel picLabel = new JLabel(new ImageIcon(bi));
					frame.add(picLabel);
					LocalTime time=LocalTime.now();
					gui.setMSG("\n["+time+"] IMAGE FROM "+username);
					gui.addText(gui.getMSG());
					String extension=f.getAbsolutePath();
					for(int i=extension.length()-1;i>0;i--) {
						if(extension.charAt(i)=='\\') {
								extension=extension.substring(i+1,extension.length());
								break;
						}
					}
					extension=extension.toUpperCase();
//					ImageIO.write(bi, extension, socket.getOutputStream());
					frame.setTitle("["+time+"]");
					frame.setSize(bi.getWidth()+bi.getWidth()/10,bi.getHeight()+bi.getHeight()/10);
					frame.setVisible(true);
				}catch(IOException err) {
					err.getMessage();
				}
			}
		});
	}

}
