import javax.swing.*;
import java.awt.Font;
/*
 * SMC: Simple Messaging Client
 * Written by Gabe Ehrlich
 */
public class GUI extends JPanel {
	//This is probably meant to actually extend JFrame? I'm not really sure I guess I'm just trusting WindowBuilder here.
	//Regardless, it's been designed to stay as is and I'm sure changing it this late into development can only go south.

	/*
	 * Create the panel.
	 * 
	 * Creates this such that the Component array from gui.getComponents() is as follows
	 * 0: scrollPane
	 * 1: submitButton (feat. mouseClicked)
	 * 2: messageField (feat. actionPerformed)
	 */
	private JEditorPane mainDisplay;
	private String msg;
	private JTextField messageField;
	private JButton submitButton;
	
	public GUI() {
		msg="";
		setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 13, 766, 499);
		add(scrollPane);
		
		mainDisplay = new JEditorPane();
		mainDisplay.setFont(new Font("Monospaced", Font.PLAIN, 18));
		scrollPane.setViewportView(mainDisplay);
		mainDisplay.setEditable(false);
		
		submitButton = new JButton("Submit");
//This button gains it's functionality within ChatClient and ChatServer, in order to make it work with networking
		submitButton.setBounds(649, 529, 139, 55);
		add(submitButton);
		
		messageField = new JTextField();
//The ability to send messages by pressing enter is given in ChatClient and ChatServer, in order to make it work with networking
		
		messageField.setBounds(22, 529, 615, 55);
		add(messageField);
		messageField.setColumns(10);
		
	}
	public JButton getSubmitButton() {
		return submitButton;
	}
	public JTextField getMessageField() {
		return messageField;
	}
	public void addText(String msg) {
		mainDisplay.setText(mainDisplay.getText()+msg);
	}
	public String getMSG() {
		return msg;
	}
	public void setMSG(String str) {
		msg=str;
	}
	
	
}
