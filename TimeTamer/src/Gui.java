
import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui extends JFrame implements ActionListener {

	String newline = "\n";

	public void createAndShowGUI() {
		Font fancyFont = new Font("Serif", Font.ITALIC, 16);

		JTextField txtInput = new JTextField("enter values here", 20);
		txtInput.setFont(fancyFont);
		String input = txtInput.getText();
		txtInput.addActionListener(this);
		txtInput.setColumns(20);
		
		JTextArea txtArea = new JTextArea(5, 5);
		txtArea.setSize(50, 50);
		txtArea.setFont(fancyFont);
		txtArea.setWrapStyleWord(true);
		txtArea.setText("Values go here");
		
		JFrame f = new JFrame("Time Tamer");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(300,300);
		f.setVisible(true);
		// f.add(txtInput);
		f.add(txtArea);
		
		
		JButton pauseButton = new javax.swing.JButton();
		f.add(pauseButton);
		// dialog.add(textField);
		// panel.add(textField);
		// applet.getContentPane().add(textField);

		// jtAreaOutput = new JTextArea(5, 20);
		// jtAreaOutput.setEditable(false);
		// JScrollPane scrollPane = new JScrollPane(jtAreaOutput,
		// JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
		// JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		// GridBagLayout gridBag = new GridBagLayout();
		// Container contentPane = getContentPane();
		// contentPane.setLayout(gridBag);
		// GridBagConstraints gridCons1 = new GridBagConstraints();
		// gridCons1.gridwidth = GridBagConstraints.REMAINDER;
		// gridCons1.fill = GridBagConstraints.HORIZONTAL;
		// contentPane.add(jtfInput, gridCons1);
		// GridBagConstraints gridCons2 = new GridBagConstraints();
		// gridCons2.weightx = 1.0;
		// gridCons2.weighty = 1.0;
		// contentPane.add(scrollPane, gridCons2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
