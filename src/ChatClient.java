import java.io.*;
import java.net.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

/*
 * SMC: Simple Messaging Client
 * Written by Gabe Ehrlich
 */
public class ChatClient {

	private GUI gui;
	
	public static void main(String[] args){
		ChatClient cc=new ChatClient();
	}
	
	public ChatClient() {
		String IP=JOptionPane.showInputDialog("Enter the IP address of the server you'd like to connect to");
		int port=3509;
		try {
			Socket sock = new Socket(InetAddress.getByName(IP), port);
			guiSetup();
			System.out.println("Connected to "+sock.getRemoteSocketAddress());
			OutputStream outToServer = sock.getOutputStream();
	        DataOutputStream out = new DataOutputStream(outToServer);
	        /* InputStream inFromServer = sock.getInputStream();
	         DataInputStream in = new DataInputStream(inFromServer);
	         
	         System.out.println("Server says " + in.readUTF());*/
			sock.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void guiSetup() {
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
