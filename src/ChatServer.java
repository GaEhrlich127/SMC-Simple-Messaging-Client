import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.time.LocalTime;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

/*
 * SMC: Simple Messaging Client
 * Written by Gabe Ehrlich
 */
public class ChatServer extends Thread{

	static ServerSocket SSockText;
	static ServerSocket SSockImage;
	static Socket sockText;
	static Socket sockImage;
	static GUI gui;
	static DataOutputStream outText;
	static DataInputStream inText;
	static DataOutputStream outImage;
	static DataInputStream inImage;

	public ChatServer() throws IOException {
		guiSetup();
		SSockText=new ServerSocket(3509);
		SSockImage=new ServerSocket(3510);
		SSockText.setSoTimeout(10000);
		SSockImage.setSoTimeout(10000);
	}

	public void run(){
		try {
			sockText=SSockText.accept();
			sockImage=SSockImage.accept();
			while(true) {
				
//MOVED FROM OUTSIDE OF LOOP
				OutputStream outToServer = sockText.getOutputStream();
				InputStream inFromServer = sockText.getInputStream();
				outText = new DataOutputStream(outToServer);
				inText = new DataInputStream(inFromServer);

				OutputStream outToServer2 = sockImage.getOutputStream();
				InputStream inFromServer2 = sockImage.getInputStream();
				outImage = new DataOutputStream(outToServer2);
				inImage = new DataInputStream(inFromServer2);
//~~~~~~~~~~~~~~~~~~~~~~~~~~
				
				gui.addText(inText.readUTF());
				try {
					BufferedImage bi=ImageIO.read(inImage);
					JFrame frame=new JFrame();
					JLabel picLabel = new JLabel(new ImageIcon(bi));
					frame.add(picLabel);
					LocalTime time=LocalTime.now();
					gui.addText("\n["+time+"] Image Recieved");
					frame.setTitle("["+time+"] Image Recieved");
					frame.setSize(bi.getWidth()+bi.getWidth()/10,bi.getHeight()+bi.getHeight()/10);
					frame.setVisible(true);
				}catch(Exception e) {}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		Thread cs=new ChatServer();
		cs.run();
	}

	private static void guiSetup() {
		String username=JOptionPane.showInputDialog("Please enter your username");
		gui=new GUI();
		JFrame frame=new JFrame();

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
				try {
					outText.writeUTF(gui.getMSG());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		gui.getTextSubmit().addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				//Submit pressed
				gui.setMSG("\n["+LocalTime.now()+"] "+username+": "+gui.getMessageField().getText());
				gui.addText(gui.getMSG());
				gui.getMessageField().setText("");
				try {
					outText.writeUTF(gui.getMSG());
				} catch (IOException err) {
					err.printStackTrace();
				}
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
					gui.setMSG("\n["+time+"] "+username+": Sent an Image");
					outText.writeUTF(gui.getMSG());
					gui.addText(gui.getMSG());
					String extension=f.getAbsolutePath();
					for(int i=extension.length()-1;i>0;i--) {
						if(extension.charAt(i)=='\\') {
							extension=extension.substring(i+1,extension.length());
							break;
						}
					}
					extension=extension.toUpperCase();
					ImageIO.write(bi, extension, sockImage.getOutputStream());
					frame.setTitle("["+time+"] Image Sent");	
					frame.setSize(bi.getWidth()+bi.getWidth()/10,bi.getHeight()+bi.getHeight()/10);
					frame.setVisible(true);
					bi.flush();
				}catch(IOException err) {
					err.getMessage();
				}
			}
		});
	}
}
