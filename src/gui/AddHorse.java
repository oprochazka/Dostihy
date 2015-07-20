package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddHorse  extends JDialog{
	private static final long serialVersionUID = 1L;
	private JPanel buttonPanel = new JPanel();
	private JPanel inputPanel = new JPanel();
	private JButton btnOk;
	private JButton btnCancel;
	
	private JTextField txtFirst;
	private JTextField txtSecond;
	private JLabel lbFirst;
	private JLabel lbSecond;
	
	private JComboBox combList;
	private String result1 = null;
	private String result2 = null;
	
	public AddHorse(JFrame parentFrame, String[] strComb) {
		super(parentFrame);
		setTitle("Add Horse");
		combList = new JComboBox(strComb);
		lbFirst = new JLabel("Stáje");
		lbSecond = new JLabel("Jméno Koně");
		txtFirst = new JTextField();
		txtFirst.setColumns(20);
		txtSecond = new JTextField();
		txtSecond.setColumns(20);
		
		GroupLayout layout = new GroupLayout(inputPanel);
		inputPanel.setLayout(layout);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		layout.setHorizontalGroup(
				layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
									.addComponent(lbFirst)
									.addComponent(lbSecond))
						.addGroup(layout.createParallelGroup()
									.addComponent(combList)
									.addComponent(txtSecond, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, 200)));
		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
											.addComponent(lbFirst)
											.addComponent(combList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(layout.createParallelGroup()
											.addComponent(lbSecond)
											.addComponent(txtSecond, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)));
		
		inputPanel.add(combList);
		inputPanel.add(lbFirst);
		
		
		GridLayout btnLayout = new GridLayout(1, 2);
		btnLayout.setHgap(5);
		btnLayout.setVgap(5);
		
		btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {				
				result1 = (String) combList.getSelectedItem();
			
				try{
					result2 = txtSecond.getText();
				}
				catch(Exception e1){
					result2 = null;
					System.out.println(e1);
				}
				setVisible(false);
			}
		});
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				result1 = null;
				result2 = null;
				setVisible(false);
			}
		});
		
		buttonPanel.setLayout(btnLayout);
		buttonPanel.add(btnOk);
		buttonPanel.add(btnCancel);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(inputPanel, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
	  
		pack();
	}

	public String getResult() {
		return result1;
	}
	public String getResult2() {
		return result2;
	}
	
}
