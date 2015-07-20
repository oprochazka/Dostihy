package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import objects.AbsolvateHorse;
import objects.Race;

import database.EditDatabase;
import database.EmployeeDBException;

import table.TableSystem;

public class PresentOne extends JDialog{
		private static final long serialVersionUID = 1L;
		private JPanel buttonPanel = new JPanel();
		private JButton btnOk;
		private JButton btnRemove;
		
		private static JTable table;
		private static TableSystem sortimentTable;
		Integer result;
		public PresentOne(final JFrame parentFrame,final EditDatabase dat) throws Exception{
			super(parentFrame);
			setTitle("Prezentace");
										
			
			GridLayout btnLayout = new GridLayout(1, 2);
			btnLayout.setHgap(10);
			btnLayout.setVgap(10);
					
			
			final JComboBox comb =new JComboBox();
			btnOk = new JButton("Ok");
			btnOk.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {										
					result= (Integer)comb.getSelectedItem();
					
					try {
						List<Race> races = dat.getRace();
					} catch (EmployeeDBException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					List<List> list = new ArrayList<>();		
					
					List<AbsolvateHorse> aHorse = null;
					try {
						aHorse = dat.getPrint1(result);
					} catch (SQLException | EmployeeDBException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					String[] namesColl = {"Kůň", "Vsazených peněz"};
					
			
					List<String> names = new ArrayList<>();
					List<Integer> count = new ArrayList<>();
					
					for(AbsolvateHorse aH : aHorse){
						names.add(aH.name);
						count.add(aH.count);
					
					}
					
					list.add(names);
					list.add(count);
					
					try {
						JDialog dlg = new PresentRider(parentFrame, list, namesColl);
						dlg.setVisible(true);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					setVisible(false);
				}
			});
			
	
			buttonPanel.add(btnOk);
			this.setLayout(new BorderLayout());
			
			
			List<Race> races = dat.getRace();
			for(int i = 0; i < races.size(); i++ ){
				comb.addItem(races.get(i).getIdTables());
			}
			
			getContentPane().setLayout(new BorderLayout());
			getContentPane().add(comb, BorderLayout.NORTH);
			getContentPane().add(buttonPanel, BorderLayout.SOUTH);
			
			this.setPreferredSize(new Dimension(600, 400));
			this.pack();
	
		}
		
		public int getResult(){
			return result;
		}
	
	}
	