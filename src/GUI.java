import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalTime;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/*
 * SMC: Simple Messaging Client
 * Written by Gabe Ehrlich
 */
public class GUI extends JPanel {
	

	/*
	 * Create the panel.
	 * 
	 * Creates this such that the Component array from gui.getComponents() is as follows
	 * 0: scrollPane
	 * 1: submitButton (feat. mouseClicked)
	 * 2: messageField (feat. actionPerformed)
	 */
	private JTextArea textArea;
	private String msg;
	private String name;
	private JTextField messageField;
	private JButton submitButton;
	
	public GUI(String n) {
		name=n;
		msg="";
		setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 13, 766, 499);
		add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 18));
		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);
		
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
		textArea.append(msg);
	}
	public String getMSG() {
		return msg;
	}
	public void setMSG(String str) {
		msg=str;
	}
	
	
}
