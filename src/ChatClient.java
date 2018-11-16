import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.time.LocalTime;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

/*
 * SMC: Simple Messaging Client
 * Written by Gabe Ehrlich
 */
public class ChatClient extends Thread {

	static GUI gui;
	static DataOutputStream out;
	static DataInputStream in;
	static Socket sock;

	public static void main(String[] args) throws UnknownHostException, IOException{
		ChatClient cc=new ChatClient();
		cc.run();
	}

	public ChatClient() throws UnknownHostException, IOException {
		String IP=JOptionPane.showInputDialog("Enter the IP address of the server you'd like to connect to");
		int port=3509;
		sock = new Socket(InetAddress.getByName(IP), port);
		guiSetup();
		gui.addText("Connected to "+sock.getRemoteSocketAddress());
	}
	
	public void run() {
		try {
			OutputStream outToServer = sock.getOutputStream();
			out = new DataOutputStream(outToServer);
			InputStream inFromServer = sock.getInputStream();
			in = new DataInputStream(inFromServer);
			while(true)
				gui.addText(in.readUTF());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void guiSetup() {
		String username=JOptionPane.showInputDialog("Please enter your username");
		gui=new GUI();
		JFrame frame=new JFrame();

		addGUIButtons(username);

		frame.getContentPane().add(gui);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setTitle("SMC Client: "+username);
		frame.setSize(835,660);
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
					out.writeUTF(gui.getMSG());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		gui.getSubmitButton().addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				//Submit pressed
				gui.setMSG("\n["+LocalTime.now()+"] "+username+": "+gui.getMessageField().getText());
				gui.addText(gui.getMSG());
				gui.getMessageField().setText("");
				try {
					out.writeUTF(gui.getMSG());
				} catch (IOException err) {
					// TODO Auto-generated catch block
					err.printStackTrace();
				}
			}
		});
	}


}
