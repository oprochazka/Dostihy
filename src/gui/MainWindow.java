package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.text.Position;

import objects.AbsolvateHorse;
import objects.Horse;
import objects.MyDate;
import objects.Race;
import objects.RaceData;
import objects.Rider;
import objects.Stable;

import database.EditDatabase;
import database.EmployeeDBException;




public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenuBar mainMenu;
	private JMenu menuFile;
	private JMenu menuStore;
	private JMenu menuPresent;
	private JMenu menuManageShoping;
	
	private JPanel inputPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	private JButton btnPay;
	private JButton btnRT;
	
	private EditDatabase databaze;
	
	public MainWindow(EditDatabase databaze) throws Exception 
	{
		super("Dostihy");
		this.databaze = databaze;
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				
		
		GroupLayout layout = new GroupLayout(inputPanel);
		inputPanel.setLayout(layout);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
	

		mainMenu = new JMenuBar();
		menuFile = new JMenu("File");

		JMenuItem Exit = new JMenuItem("Exit");
		Exit.addActionListener(new Remove(this));
		menuFile.add(Exit);

		menuStore = new JMenu("Přidej");
		JMenuItem menuItemAddGoods = new JMenuItem("Jezdce");
		menuItemAddGoods.addActionListener(new AddRiderWindow(this));
		menuStore.add(menuItemAddGoods);

		JMenuItem menuItemAddHorse = new JMenuItem("Koně");
		menuItemAddHorse.addActionListener(new AddHorseWindow(this));
		menuStore.add(menuItemAddHorse);
		
		JMenuItem addStable = new JMenuItem("Stáje");
		addStable.addActionListener(new AddStableWindow(this));
		menuStore.add(addStable);
		
		JMenuItem addBets = new JMenuItem("Sázku");
		addBets.addActionListener(new AddBetsWindow(this));
		menuStore.add(addBets);
		
		JMenuItem addRaces = new JMenuItem("Dostih");
		addRaces .addActionListener(new AddRaceWindow(this));
		menuStore.add(addRaces);
		
		menuPresent = new JMenu("Zobraz");
		JMenuItem menuItemPrezentRider = new JMenuItem("Jezdce");
		menuItemPrezentRider.addActionListener(new PresentRiderWindow(this));
		menuPresent.add(menuItemPrezentRider);
		
		JMenuItem menuItemPrezentHorse = new JMenuItem("Koni");
		menuItemPrezentHorse.addActionListener(new PresentHorseWindow(this));
		menuPresent.add(menuItemPrezentHorse);
		
		JMenuItem menuItemPrezentRace = new JMenuItem("Dostihy");
		menuItemPrezentRace.addActionListener(new PresentRacesWindow(this));
		menuPresent.add(menuItemPrezentRace);
		
		JMenuItem menuItemAbsolvateHorse = new JMenuItem("Absolvované dostihy");
		menuItemAbsolvateHorse.addActionListener(new PresentAbsolvateHorseWindow(this));
		menuPresent.add(menuItemAbsolvateHorse);
		
		JMenuItem menuItemSumHorse = new JMenuItem("Suma vsazenych penez");
		menuItemSumHorse.addActionListener(new PresentSumHorseWindow(this));
		menuPresent.add(menuItemSumHorse);
		
		JMenuItem menuItemPrint2 = new JMenuItem("Ukol2");
		menuItemPrint2.addActionListener(new PresentPrint2Window(this));
		menuPresent.add(menuItemPrint2);
		JMenuItem menuItemPrint3 = new JMenuItem("Ukol3");
		menuItemPrint3.addActionListener(new PresentPrint3Window(this));
		menuPresent.add(menuItemPrint3);
		
		menuManageShoping = new JMenu("Sázky");
		JMenuItem menuItemManage = new JMenuItem("sázej");
		menuItemManage.addActionListener(new Remove(this));
		menuManageShoping.add(menuItemManage);

		mainMenu.add(menuFile);
		mainMenu.add(menuStore);
		mainMenu.add(menuPresent);
		mainMenu.add(menuManageShoping);

		this.setJMenuBar(mainMenu);

		this.setPreferredSize(new Dimension(280, 400));
		this.pack();
	}

	private class Remove implements ActionListener {
		private JFrame parentFrame;

		public Remove(JFrame frame) {
			this.parentFrame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			RemoveDialog dlg = new RemoveDialog(parentFrame,"hehe");
			try {				
				dlg.setModal(true);
				dlg.setVisible(true);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}
	private class AddRiderWindow implements ActionListener {
		private JFrame parentFrame;

		public AddRiderWindow(JFrame frame) {
			this.parentFrame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e){
			AddRider dlg = new AddRider(parentFrame);
			dlg.setModal(true);
			dlg.setVisible(true);
			if (dlg.getResult() != null && dlg.getResult2() != null) {
				try {
					if(dlg.getResult().length() > 0 && dlg.getResult2().length() > 0)
					databaze.addRider(new Rider(dlg.getResult(), dlg.getResult2()));
					
				} catch (EmployeeDBException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		}
	}
	private class AddHorseWindow implements ActionListener {
		private JFrame parentFrame;

		public AddHorseWindow(JFrame frame) {
			this.parentFrame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e){
			List<Stable> stable = null;
			try {
				 stable = databaze.getStable();
			} catch (EmployeeDBException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			String[] strComb = new String[stable.size()];
						
			for(int i = 0; i < stable.size(); i++){
				strComb[i] = stable.get(i).getName();
			}
						
			AddHorse dlg = new AddHorse(parentFrame,strComb);
			dlg.setModal(true);
			dlg.setVisible(true);
			if (dlg.getResult() != null && dlg.getResult2() != null) {
				try {
					if(dlg.getResult2().length() > 0){
						databaze.addHorse(new Horse(dlg.getResult2(),dlg.getResult()));
					}
					
				} catch (EmployeeDBException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		}
	}
	
	private class AddRaceWindow implements ActionListener {
		private JFrame parentFrame;

		public AddRaceWindow(JFrame frame) {
			this.parentFrame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e){
						
			AddRaces dlg;
			try {
				dlg = new AddRaces(parentFrame, databaze);
		
			dlg.setModal(true);
			dlg.setVisible(true);
			if (dlg.getName() != null && dlg.getPlace() != null){
				if (dlg.getName().length() > 0 && dlg.getPlace().length() > 0) {
					try {
						Race race = new Race(dlg.getDate(), dlg.getPlace(), dlg.getName());
						
						List<Horse> horse = dlg.getResultHorse();
						List<Rider> rider = dlg.getResultRider();
						List<String> pos = dlg.getResultPos();
						
						if(pos.get(0) != "-"){
							race.state = "finished";
						}
						
						databaze.addRace(race);
						
						for(int i = 0; i < 7; i++){
							databaze.addRaceData(new RaceData(horse.get(i), rider.get(i), pos.get(i), race));
						}
						
					} catch (EmployeeDBException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			} catch (EmployeeDBException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

		}
	}
	
	private class AddStableWindow implements ActionListener {
		private JFrame parentFrame;

		public AddStableWindow(JFrame frame) {
			this.parentFrame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e){
						
			AddStable dlg = new AddStable(parentFrame);
			dlg.setModal(true);
			dlg.setVisible(true);
			if (dlg.getResult() != null ){
				if (dlg.getResult().length() > 0) {
					try {
						databaze.addStable(new Stable(dlg.getResult(),null));
					
					} catch (EmployeeDBException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}

		}
	}
	
	private class PresentRiderWindow implements ActionListener {
		private JFrame parentFrame;

		public PresentRiderWindow(JFrame frame) {
			this.parentFrame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e){
			PresentRider dlg = null;
			try {
				List<Rider> riders = databaze.getRider();
				List<List> list = new ArrayList<>();		
				
				String[] namesColl = {"Firstname", "Lastname", "id"};
				
				List<String> names = new ArrayList<>();
				List<String> lastNames = new ArrayList<>();
				List<Integer> idList = new ArrayList<>();
				
				for(Rider r : riders){
					names.add(r.getFirstName());
					lastNames.add(r.getLastName());
					idList.add(r.getId());
				}
				
				list.add(names);
				list.add(lastNames);
				list.add(idList);				

				dlg = new PresentRider(parentFrame,list,namesColl);
			} catch (EmployeeDBException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dlg.setModal(true);
			dlg.setVisible(true);			
		}
	}
	
	private class PresentHorseWindow implements ActionListener {
		private JFrame parentFrame;

		public PresentHorseWindow(JFrame frame) {
			this.parentFrame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e){
			PresentRider dlg = null;
			try {
				List<Horse> horse = databaze.getHorse();
				List<List> list = new ArrayList<>();		
				
				String[] namesColl = {"Name", "Stable", "id"};
				
				List<String> names = new ArrayList<>();
				List<String> lastNames = new ArrayList<>();
				List<Integer> idList = new ArrayList<>();
				
				for(Horse r : horse){
					System.out.println(r.getName());
					names.add(r.getName());
					lastNames.add(r.stable);
					idList.add(r.id);
				}
				
				list.add(names);
				list.add(lastNames);
				list.add(idList);				

				dlg = new PresentRider(parentFrame,list,namesColl);
			} catch (EmployeeDBException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dlg.setModal(true);
			dlg.setVisible(true);			
		}
	}
	
	private class PresentRacesWindow implements ActionListener {
		private JFrame parentFrame;

		public PresentRacesWindow(JFrame frame) {
			this.parentFrame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e){
			PresentRider dlg = null;
			try {
				List<Race> races = databaze.getRace();
				List<List> list = new ArrayList<>();		
				
				String[] namesColl = {"Jméno", "Místo","Datum","state","id"};
				
				List<String> names = new ArrayList<>();
				List<String> lastNames = new ArrayList<>();
				List<String> myDate = new ArrayList<>();
				List<Integer> idList = new ArrayList<>();
				List<String> state= new ArrayList<>();
				for(Race r : races){
					names.add(r.getName());
					lastNames.add(r.getPlace());
					myDate.add(r.getDate().getStrDate());
					state.add(r.state);
					idList.add(r.getIdTables());
				}
				
				list.add(names);
				list.add(lastNames);
				list.add(myDate);
				list.add(state);
				list.add(idList);				

				dlg = new PresentRider(parentFrame,list,namesColl);
			} catch (EmployeeDBException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dlg.setModal(true);
			dlg.setVisible(true);			
		}
	}
	
	private class PresentAbsolvateHorseWindow implements ActionListener {
		private JFrame parentFrame;

		public PresentAbsolvateHorseWindow(JFrame frame) {
			this.parentFrame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e){
			AddForms4 dlg = null;
			try {
			
				dlg = new AddForms4(parentFrame,databaze);
			} catch (EmployeeDBException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dlg.setModal(true);
			dlg.setVisible(true);			
		}
	}
	
	private class PresentSumHorseWindow implements ActionListener {
		private JFrame parentFrame;

		public PresentSumHorseWindow(JFrame frame) {
			this.parentFrame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e){
			PresentOne dlg = null;
			try {
		
				
				dlg = new PresentOne(parentFrame,databaze);
				
				
			} catch (EmployeeDBException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dlg.setModal(true);
			dlg.setVisible(true);			
		}
	}

	private class PresentPrint2Window implements ActionListener {
		private JFrame parentFrame;

		public PresentPrint2Window(JFrame frame) {
			this.parentFrame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e){
			AddForms2 dlg = null;
			try {
				dlg = new AddForms2(parentFrame,databaze);
			} catch (EmployeeDBException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dlg.setModal(true);
			dlg.setVisible(true);			
		}
	}
	
	private class PresentPrint3Window implements ActionListener {
		private JFrame parentFrame;

		public PresentPrint3Window(JFrame frame) {
			this.parentFrame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e){
			AddForms3 dlg = null;
			try {
			
				dlg = new AddForms3(parentFrame,databaze);
			} catch (EmployeeDBException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dlg.setModal(true);
			dlg.setVisible(true);			
		}
	}
	
	private class AddBetsWindow implements ActionListener {
		private JFrame parentFrame;

		public AddBetsWindow(JFrame frame) {
			this.parentFrame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e){
			AddBets dlg = null;	
		
				

			try {
				dlg = new AddBets(parentFrame, databaze);
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
				
			dlg.setModal(true);
			dlg.setVisible(true);
			
			if (dlg.getResult() != null) {
				try {
					
					databaze.addBets(dlg.getResult());
				} catch (EmployeeDBException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
}
