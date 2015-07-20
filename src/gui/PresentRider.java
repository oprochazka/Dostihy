package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import objects.Rider;

import table.TableSystem;

import database.EditDatabase;


public class PresentRider extends JDialog{
	private static final long serialVersionUID = 1L;
	private JPanel buttonPanel = new JPanel();
	private JButton btnOk;
	private JButton btnRemove;
	
	private static JTable table;
	private static TableSystem sortimentTable;
	
	public PresentRider(JFrame parentFrame, List<List> lists, String[] collNames) throws Exception{
		super(parentFrame);
		setTitle("Prezentace");
				
		sortimentTable = new TableSystem(lists,collNames);
		table = new JTable(sortimentTable);
		
		JScrollPane scrollTable = new JScrollPane(table);
		
		GridLayout btnLayout = new GridLayout(1, 2);
		btnLayout.setHgap(10);
		btnLayout.setVgap(10);
				
		
		
		btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				setVisible(false);
			}
		});
		

		buttonPanel.add(btnOk);
		this.setLayout(new BorderLayout());
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(scrollTable, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		this.setPreferredSize(new Dimension(600, 400));
		this.pack();

	}

}
