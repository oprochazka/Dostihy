package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class RemoveDialog extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnOk;
	private JButton btnNO;
	private JTextField txtFirst;
	JTextArea mainTextArea;
	private JPanel buttonPanel = new JPanel();
	private JPanel textPanel = new JPanel();
	private boolean result = false;
	
	public RemoveDialog(JFrame parentFrame,String s){
			super(parentFrame);
			setTitle("Remove sortiment " );

			btnOk = new JButton("Yes");
			btnOk.addActionListener(new OkeyButton());
			btnOk.addActionListener(new OkeyButton());
			
			btnNO = new JButton("No");
			btnNO.addActionListener(new CancleButton());
			
			buttonPanel.add(btnOk);
			buttonPanel.add(btnNO);
			mainTextArea = new JTextArea(s);
			textPanel.add(mainTextArea);
			getContentPane().setLayout(new BorderLayout());
			getContentPane().add(textPanel, BorderLayout.CENTER);
			getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		pack();
	}
	public boolean getResult() {
		return result;
	}
	
	private class OkeyButton implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			result = true;
			setVisible(false);
		}
		
	}
	
	private class CancleButton implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			result = false;
			setVisible(false);
		}
		
	}
	
}
