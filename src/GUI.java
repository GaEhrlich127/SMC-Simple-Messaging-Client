import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.swing.JScrollPane;
import java.awt.Button;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GUI extends JPanel {
	private JTextField messageField;

	/*
	 * Create the panel.
	 */
	public GUI(String name) {
		
		
		setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 13, 766, 499);
		add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 18));
		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);
		
		JButton submitButton = new JButton("Submit");
		submitButton.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				//Submit pressed
				String msg="\n["+LocalTime.now()+"] "+name+": "+messageField.getText();
				textArea.setText(textArea.getText()+msg);
				messageField.setText("");
			}
		});
		submitButton.setBounds(649, 529, 139, 55);
		add(submitButton);
		
		messageField = new JTextField();
		messageField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String msg="\n["+LocalTime.now()+"] "+name+": "+messageField.getText();
				textArea.setText(textArea.getText()+msg);
				messageField.setText("");
				//This is identical to pressing the submit button.
			}
		});
		
		messageField.setBounds(22, 529, 615, 55);
		add(messageField);
		messageField.setColumns(10);
		
	}
	
}
