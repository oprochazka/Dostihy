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

import objects.Horse;
import objects.MyDate;
import objects.Rider;

import database.EditDatabase;
import database.EmployeeDBException;

public class AddRaces extends JDialog{
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
	
	private List<String> resultPos = new ArrayList<>(); 	
	private List<Horse> resultHorse = new ArrayList<>();
	private List<Rider> resultRider = new ArrayList<>();
	private String[] strCombDay = new String[31];
	private String[] strCombMon = new String[12];
	private String[] strCombYear = new String[50];
	
	private String[] strCombHour = new String[24];
	private String[] strCombMinute = new String[60];
	
	private MyDate myDate;
	
	public AddRaces(JFrame parentFrame, EditDatabase database) throws EmployeeDBException, SQLException {
		super(parentFrame);
		setTitle("Add Horse");
		List<Horse> horseName = database.getHorse();
		List<Rider> riderName = database.getRider();
		
		for(int i = 0; i < 24; i++){
			strCombHour[i] = new Integer(i + 1).toString();
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
		
		final List<JComboBox> listComb = new ArrayList<>();
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
			
			
			listComb.add(tmp);			
			riderComb.add(rider);
		}
		
		lbFirst = new JLabel("Den");
		combList = new JComboBox<String>(strCombDay);
		
		JLabel lbMonth = new JLabel("Měsíc");
		combListM = new JComboBox<String>(strCombMon);
		
		JLabel lbYear = new JLabel("Rok");
		combListY = new JComboBox<String>(strCombYear);
		
		JLabel lbHour = new JLabel("Hodina");
		combListH = new JComboBox<String>(strCombHour);
		
		JLabel lbMinute = new JLabel("Minuta");
		combListMin = new JComboBox<String>(strCombMinute);
		
		JLabel lbName = new JLabel("Jméno dostihu: ");
		JLabel lbPlace = new JLabel("Místo dostihu: ");
		
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
									.addComponent(lbMinute)
									.addComponent(lbHour)
									.addComponent(lbFirst)
									.addComponent(lbMonth)
									.addComponent(lbYear)
									.addComponent(lbName)
									.addComponent(lbPlace))
						.addGroup(layout.createParallelGroup()
									.addComponent(combListMin)
									.addComponent(combListH)
									.addComponent(combList)
									.addComponent(combListM)
									.addComponent(combListY)
									.addComponent(txtFirst)
									.addComponent(txtSecond))
						.addGroup(layout.createParallelGroup()
									.addComponent(labels.get(0))
									.addComponent(labels.get(1))
									.addComponent(labels.get(2))
									.addComponent(labels.get(3))
									.addComponent(labels.get(4))
									.addComponent(labels.get(5))
									.addComponent(labels.get(6)))
						.addGroup(layout.createParallelGroup()
									.addComponent(listComb.get(0))
									.addComponent(listComb.get(1))
									.addComponent(listComb.get(2))
									.addComponent(listComb.get(3))
									.addComponent(listComb.get(4))
									.addComponent(listComb.get(5))
									.addComponent(listComb.get(6)))
						.addGroup(layout.createParallelGroup()
									.addComponent(riderComb.get(0))
									.addComponent(riderComb.get(1))
									.addComponent(riderComb.get(2))
									.addComponent(riderComb.get(3))
									.addComponent(riderComb.get(4))
									.addComponent(riderComb.get(5))
									.addComponent(riderComb.get(6)))
						.addGroup(layout.createParallelGroup()
									.addComponent(posComb.get(0))
									.addComponent(posComb.get(1))
									.addComponent(posComb.get(2))
									.addComponent(posComb.get(3))
									.addComponent(posComb.get(4))
									.addComponent(posComb.get(5))
									.addComponent(posComb.get(6))								
									));
		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
											.addComponent(labels.get(0))
											.addComponent(listComb.get(0))
											.addComponent(riderComb.get(0))
											.addComponent(posComb.get(0))
											.addComponent(lbMinute)
											.addComponent(combListMin)
											
											)																					
						.addGroup(layout.createParallelGroup()
											.addComponent(labels.get(1))											
											.addComponent(listComb.get(1))
											.addComponent(riderComb.get(1))
											.addComponent(posComb.get(1))
											.addComponent(lbHour)
											.addComponent(combListH, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
											
						.addGroup(layout.createParallelGroup()
								.addComponent(labels.get(2))								
								.addComponent(listComb.get(2))
								.addComponent(riderComb.get(2))
								.addComponent(posComb.get(2))
											.addComponent(lbFirst)
											.addComponent(combList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
											
						.addGroup(layout.createParallelGroup()
								.addComponent(labels.get(3))
								.addComponent(riderComb.get(3))
								.addComponent(listComb.get(3))
								.addComponent(posComb.get(3))
											.addComponent(lbMonth)
											.addComponent(combListM, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
											
						.addGroup(layout.createParallelGroup()								
								.addComponent(labels.get(4))
								.addComponent(riderComb.get(4))
								.addComponent(listComb.get(4))
								.addComponent(posComb.get(4))
											.addComponent(lbYear)
							
											.addComponent(combListY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
												
						.addGroup(layout.createParallelGroup()
								.addComponent(labels.get(5))
								.addComponent(listComb.get(5))
								.addComponent(riderComb.get(5))
								.addComponent(posComb.get(5))
											.addComponent(lbName)
											.addComponent(txtFirst, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
											
						.addGroup(layout.createParallelGroup()
								.addComponent(labels.get(6))
								.addComponent(riderComb.get(6))
								.addComponent(listComb.get(6))
								.addComponent(posComb.get(6))
											.addComponent(lbPlace)
											.addComponent(txtSecond, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
											
											
				);
		
		inputPanel.add(combList);
		inputPanel.add(lbFirst);
		
		inputPanel.add(combListM);
		inputPanel.add(lbMonth);
		
		inputPanel.add(combListY);
		inputPanel.add(lbYear);
		
		inputPanel.add(combListH);
		inputPanel.add(lbHour);
		
		inputPanel.add(combListMin);
		inputPanel.add(lbMinute);
		
		inputPanel.add(lbName);
		inputPanel.add(txtFirst);
		inputPanel.add(lbPlace);
		inputPanel.add(txtSecond);
		
		
		
		GridLayout btnLayout = new GridLayout(1, 2);
		btnLayout.setHgap(5);
		btnLayout.setVgap(5);
		
		btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {				
				
				myDate = new MyDate(Integer.parseInt((String) combListY.getSelectedItem()),Integer.parseInt((String)combListM.getSelectedItem()), 
						Integer.parseInt((String)combList.getSelectedItem()), 
								Integer.parseInt((String)combListH.getSelectedItem()), Integer.parseInt((String)combListMin.getSelectedItem()));
				
				
				for(int i = 0; i < 7; i++){
					resultHorse.add( (Horse) listComb.get(i).getSelectedItem());
					resultRider.add((Rider) riderComb.get(i).getSelectedItem());
					resultPos.add((String) posComb.get(i).getSelectedItem());
				}
								
				try{				
					result1 = txtFirst.getText();
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
