import java.io.*;
import java.net.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

/*
 * SMC: Simple Messaging Client
 * Written by Gabe Ehrlich
 */
public class ChatServer extends Thread{

	ServerSocket SSock;
	static GUI gui;
	
	public ChatServer() throws IOException {
		guiSetup();
		SSock=new ServerSocket(3509);
		SSock.setSoTimeout(10000);
	}
	
	public void run(){
		try {
			Socket client=SSock.accept();
            DataInputStream in = new DataInputStream(client.getInputStream());
            gui.addText(in.readUTF());
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            out.writeUTF("ping");
			SSock.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException {
		Thread cs=new ChatServer();
		cs.run();
	}
	
	public static void guiSetup() {
		String username=JOptionPane.showInputDialog("Please enter your username");
		gui=new GUI(username);
		JFrame frame=new JFrame();
		frame.getContentPane().add(gui);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setTitle("SMC Server: "+username);
		frame.setSize(835,660);
		frame.setVisible(true);
	}
}
