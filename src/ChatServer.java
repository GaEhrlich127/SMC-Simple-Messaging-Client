import java.io.*;
import java.net.*;

/*
 * SMC: Simple Messaging Client
 * Written by Gabe Ehrlich
 */
public class ChatServer {

	public static void main(String[] args){
		int port=3509;
		ServerSocket SSock;
		try {
			SSock = new ServerSocket(port);
			SSock.setSoTimeout(10000);
			SSock.accept();
			
			SSock.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
