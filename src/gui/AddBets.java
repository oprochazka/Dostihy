package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import objects.Bets;
import objects.Horse;
import objects.Race;
import objects.RaceData;

import database.EditDatabase;
import database.EmployeeDBException;

import table.TableSystem;

public class AddBets extends JDialog{
	private static final long serialVersionUID = 1L;
	private JPanel buttonPanel = new JPanel();
	private JButton btnOk;
	private JButton btnRemove;
	private JPanel inputPanel = new JPanel();
	private JComboBox combList;
	private JComboBox combAmount;
	
	private static JTable table;
	private static TableSystem sortimentTable;
	private Bets bets;
	private List<List> lists;
	String[] collNames = {"Jméno", "Místo","Datum"};;
	private Object result;
	
	public AddBets(final JFrame parentFrame, final EditDatabase databaze ) throws Exception{
		super(parentFrame);
		setTitle("Prezentace");
				
		JLabel lbHorse = new JLabel("Stáje - Kůň");
		JLabel lbAmount = new JLabel("Částka");
		
		final List<Race> races = databaze.getRace();
		lists = new ArrayList<>();		
			
		String[] combTxt = {"50","100", "500", "1000", "2000", "5000"};
		String[] horseTxt = { "NONE" };
		combList = new JComboBox<>(horseTxt);
		combAmount = new JComboBox<>(combTxt);
		final JTextField txtFirst = new JTextField();
		txtFirst.setColumns(10);
		
		
		List<String> names = new ArrayList<>();
		List<String> lastNames = new ArrayList<>();
		List<String> myDate = new ArrayList<>();
		
		for(Race r : races){
			names.add(r.getName());
			lastNames.add(r.getPlace());
			myDate.add(r.getDate().getStrDate());					
		}
		
		lists.add(names);
		lists.add(lastNames);
		lists.add(myDate);		
		
		
		sortimentTable = new TableSystem(lists,collNames);
		table = new JTable(sortimentTable);
		
		JScrollPane scrollTable = new JScrollPane(table);
		
		scrollTable.setPreferredSize(new Dimension(400,200));
		
		GridLayout btnLayout = new GridLayout(1, 2);
		btnLayout.setHgap(10);
		btnLayout.setVgap(10);
				
		
		table.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				System.out.println(table.getSelectedRow());
				try {
					List<RaceData> rD = databaze.getRaceData(races.get(table.getSelectedRow()).getIdTables());
					
					System.out.println(rD.get(1).horse.getName());
					combList.removeAllItems();		
					for(int i = 0; i < rD.size(); i++){
						combList.addItem(rD.get(i).horse);
					}
					
					
				} catch (SQLException | EmployeeDBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			
			
				
			
			}			
			@Override
			public void mousePressed(MouseEvent arg0) {
			
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
		GroupLayout layout = new GroupLayout(inputPanel);
		inputPanel.setLayout(layout);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		layout.setHorizontalGroup(
				layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()	
									.addComponent(txtFirst))
						.addGroup(layout.createParallelGroup()
									.addComponent(lbHorse))									
						.addGroup(layout.createParallelGroup()									
									.addComponent(combList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, 100))
						.addGroup(layout.createParallelGroup()	
									.addComponent(lbAmount))
						.addGroup(layout.createParallelGroup()	
											.addComponent(combAmount)));
			/*			.addGroup(layout.createParallelGroup()
									.addComponent(scrollTable, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, 100)));*/
																									
		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
											.addComponent(txtFirst)
											.addComponent(lbHorse)											
											.addComponent(combList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, 20)
											.addComponent(lbAmount)
											.addComponent(combAmount)));
											//.addComponent(scrollTable, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, 200)));
				
										
				
		
		
		inputPanel.add(combList);
		inputPanel.add(scrollTable);
		
		btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				bets = new Bets(txtFirst.getText(), ((Horse)combList.getSelectedItem()).id, 
						Integer.parseInt((String) combAmount.getSelectedItem()), races.get(table.getSelectedRow()).getIdTables());
						
				System.out.println("here"+ bets);
				
				setVisible(false);
			}
		});
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				bets = null;
				
				setVisible(false);
			}
		});

		buttonPanel.add(btnOk);
		buttonPanel.add(btnCancel);
		this.setLayout(new BorderLayout());
		
		getContentPane().setLayout(new BorderLayout());
		
		getContentPane().add(scrollTable, BorderLayout.NORTH);
		getContentPane().add(inputPanel, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
	//	this.setPreferredSize(new Dimension(600, 650));
		this.pack();

	}
	
	public Bets getResult(){
		return bets;
	}
}
