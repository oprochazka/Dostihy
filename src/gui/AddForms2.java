package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JTextField;

import objects.AbsolvateHorse;
import objects.Horse;
import objects.MyDate;
import objects.Race;
import objects.Rider;
import database.EditDatabase;
import database.EmployeeDBException;

public class AddForms2 extends JDialog{
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
	private JComboBox combListM;
	private JComboBox combListY;
	private JComboBox combListH;
	private JComboBox combListMin;
	private String result1 = null;
	private String result2 = null;
	private MyDate myDate2 = null;
	private List<String> resultPos = new ArrayList<>(); 	
	private List<Horse> resultHorse = new ArrayList<>();
	private List<Rider> resultRider = new ArrayList<>();
	private String[] strCombDay = new String[31];
	private String[] strCombMon = new String[50];
	private String[] strCombYear = new String[50];
	
	private String[] strCombHour = new String[24];
	private String[] strCombMinute = new String[60];
	
	private MyDate myDate;
	
	public AddForms2(final JFrame parentFrame, final EditDatabase database) throws EmployeeDBException, SQLException {
		super(parentFrame);
		setTitle("Add Horse");
		List<Horse> horseName = database.getHorse();
		List<Rider> riderName = database.getRider();
		final JComboBox horseComb = new JComboBox();
		
		for(int i = 0; i < horseName.size(); i++){
			horseComb.addItem(horseName.get(i).getName());
		}
		for(int i = 0; i < 60; i++){
			strCombMinute[i] = new Integer(i + 1).toString();
		}
		
		for(int i = 0; i < 31; i++){
			strCombDay[i] = new Integer(i + 1).toString();
		}
		for(int i = 0; i < 12; i++){
			strCombMon[i] = new Integer(i + 1).toString();
		}
		for(int i = 0; i < 50; i++){
			strCombYear[i] = new Integer(i + 2000).toString();
		}
		
		final List<JComboBox> riderComb = new ArrayList<>();
		final List<JComboBox> posComb = new ArrayList<>();
		
		for(int i = 0; i < 7; i++){		
			String[] tmp = {"-","1","2","3","4","5","6","7"};
			posComb.add(new JComboBox(tmp));
		}
		
		String[] horse = new String[horseName.size()];
		List<JLabel> labels = new ArrayList<>();
		for(int i = 0; i < 7; i++){
			labels.add(new JLabel("Kůň:"));
		}
		
		for(int i= 0; i < 7; i++){
			JComboBox tmp = new JComboBox<>();
			JComboBox rider= new JComboBox<>();
			for(Horse h : horseName){
				tmp.addItem(h);		
				
			}						
			for(Rider r : riderName){
				rider.addItem(r);
			}
			
			
					
			riderComb.add(rider);
		}
		
	
		JLabel lbYear = new JLabel("od Roku");
		combListY = new JComboBox<String>(strCombYear);
		combListM = new JComboBox<String>(strCombYear);
		final JComboBox fromCombListM = new JComboBox(strCombMon);
		JComboBox toCombListM = new JComboBox(strCombMon);
		final JComboBox fromCombListD = new JComboBox(strCombDay);
		JComboBox toCombListD = new JComboBox(strCombDay);
		
		JLabel lbName = new JLabel("kůn ");
		JLabel lbPlace = new JLabel("po rok ");
		
		lbSecond = new JLabel("Jméno Koně");
		txtFirst = new JTextField();
		txtFirst.setColumns(10);
		txtSecond = new JTextField();
		txtSecond.setColumns(10);
		
		GroupLayout layout = new GroupLayout(inputPanel);
		inputPanel.setLayout(layout);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		layout.setHorizontalGroup(
				layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()									
									.addComponent(lbYear)
									.addComponent(lbName)
									.addComponent(lbPlace))
						.addGroup(layout.createParallelGroup()														
									.addComponent(combListY)
									.addComponent(fromCombListM)
									.addComponent(fromCombListD)
									.addComponent(horseComb)
									.addComponent(combListM)
									.addComponent(toCombListM)
									.addComponent(toCombListD)));
					
		
		
		layout.setVerticalGroup(
				layout.createSequentialGroup()																
						.addGroup(layout.createParallelGroup()																
											.addComponent(lbYear)							
											.addComponent(combListY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(layout.createParallelGroup()																
											//.addComponent(lbYear)							
											.addComponent(fromCombListM, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(layout.createParallelGroup()																
											//.addComponent(lbYear)							
											.addComponent(fromCombListD, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(layout.createParallelGroup()
											.addComponent(lbName)
											.addComponent(horseComb))
						.addGroup(layout.createParallelGroup()
											.addComponent(lbPlace)
											.addComponent(combListM))
						.addGroup(layout.createParallelGroup()																
											//.addComponent(lbYear)							
											.addComponent(toCombListM, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(layout.createParallelGroup()																
											//.addComponent(lbYear)							
											.addComponent(toCombListD, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)));
											
		

		
		
		
		GridLayout btnLayout = new GridLayout(1, 2);
		btnLayout.setHgap(5);
		btnLayout.setVgap(5);
		
		btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {				
				
				myDate = new MyDate(Integer.parseInt((String) combListY.getSelectedItem()),Integer.parseInt((String)fromCombListM.getSelectedItem()),
						Integer.parseInt((String)fromCombListD.getSelectedItem()),0,0);
				
				myDate2 = new MyDate(Integer.parseInt((String) combListM.getSelectedItem()),1,1,0,0);
				
				
				List<List> list = new ArrayList<>();		
				
				List<AbsolvateHorse> aHorse = null;
				try {
					aHorse = database.getPrint2((String)horseComb.getSelectedItem(),myDate, myDate2);
				} catch (SQLException | EmployeeDBException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				String[] namesColl = {"Kůň", "Jezdec","Pozice","dostih"};
				
		
				List<String> names = new ArrayList<>();
				List<Integer> count = new ArrayList<>();
				List<String> pos = new ArrayList<>();
				List<Integer> race = new ArrayList<>();
				for(AbsolvateHorse aH : aHorse){
					names.add(aH.name);
					count.add(aH.count);
					pos.add(aH.str);
					race.add(aH.count1);
				}
				
				list.add(names);
				list.add(count);
				list.add(pos);
				list.add(race);
				
				JDialog dlg = null;
				try {
					dlg = new PresentRider(parentFrame,list,namesColl);
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				dlg.setVisible(true);
				try{				
					result1 = null;
					result2 = null;
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

	public String getName() {		
		return result1;
	}
	public MyDate getDate(){
		return myDate;
	}
	public String getPlace() {
		return result2;
	}
	public List<Horse> getResultHorse(){
		return resultHorse;
	}
	public List<String> getResultPos(){
		return resultPos;
	}
	public List<Rider> getResultRider(){
		return resultRider;
	}
}
