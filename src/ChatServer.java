import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.time.LocalTime;

import javax.imageio.ImageIO;
import javax.swing.*;

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
			//Set everything up
			sockText=SSockText.accept();
			sockImage=SSockImage.accept();

			//Text in/out
			OutputStream outToServer = sockText.getOutputStream();
			InputStream inFromServer = sockText.getInputStream();
			outText = new DataOutputStream(outToServer);
			inText = new DataInputStream(inFromServer);
			
			//Image in/out
			OutputStream outToServer2 = sockImage.getOutputStream();
			InputStream inFromServer2 = sockImage.getInputStream();
			outImage = new DataOutputStream(outToServer2);
			inImage = new DataInputStream(inFromServer2);

			while(true) {
				while(inText.available()>0)								//When there's text data waiting
					gui.addText(inText.readUTF());						//Display it
				
				while(inImage.available()>0) {							//When there's image data waiting
						BufferedImage bi=ImageIO.read(inImage);			//Read the image
						JFrame frame=new JFrame();						//Make a new JFrame
						JLabel picLabel = new JLabel(new ImageIcon(bi));//Make the image compatible
						frame.add(picLabel);							//Add it to the frame
						LocalTime time=LocalTime.now();
						gui.addText("\n["+time+"] Image Received");
						frame.setTitle("["+time+"] Image Received");	//Display that you've received the image
						frame.setSize(bi.getWidth()+bi.getWidth()/10,bi.getHeight()+bi.getHeight()/10);
						frame.setVisible(true);							//Size the Frame, make it visible
				}
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

	private static void addGUIButtons(String username) {//This will add the functionality for hitting buttons or enter
		gui.getMessageField().addActionListener(new ActionListener() {//Enter Key
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//Compose the message
				gui.setMSG("\n["+LocalTime.now()+"] "+username+": "+gui.getMessageField().getText());
				gui.addText(gui.getMSG());
				gui.getMessageField().setText("");
				try {
					outText.writeUTF(gui.getMSG());//Send it
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		gui.getTextSubmit().addMouseListener(new MouseAdapter() {//Submit button pressed
			@Override
			public void mouseClicked(MouseEvent e) {
				//Compose the message
				gui.setMSG("\n["+LocalTime.now()+"] "+username+": "+gui.getMessageField().getText());
				gui.addText(gui.getMSG());
				gui.getMessageField().setText("");
				try {
					outText.writeUTF(gui.getMSG());//Send it
				} catch (IOException err) {
					err.printStackTrace();
				}
			}
		});
		gui.getImageSubmit().addMouseListener(new MouseAdapter() {
			@Override
			//Image Submit
			public void mouseClicked(MouseEvent e) {
				//Choose the file
				JFileChooser fc=new JFileChooser();
				fc.showOpenDialog(gui);
				File f=fc.getSelectedFile();
				BufferedImage bi;
				try {
					//Make the file swing compatible
					bi=ImageIO.read(f);
					JFrame frame=new JFrame();
					JLabel img = new JLabel(new ImageIcon(bi));
					frame.add(img);
					
					//Send that you're sending an image
					LocalTime time=LocalTime.now();
					gui.setMSG("\n["+time+"] "+username+": Sent an Image");
					outText.writeUTF(gui.getMSG());
					gui.addText(gui.getMSG());
					
					//Determine the File Extension
					String extension=f.getAbsolutePath();
					for(int i=extension.length()-1;i>0;i--) {
						if(extension.charAt(i)=='.') {
							extension=extension.substring(i+1,extension.length());
							break;
						}
					}
					
					//Send the image, and open it
					ImageIO.write(bi, extension, outImage);
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
