package database;

import java.util.List;

import objects.Bets;
import objects.Horse;
import objects.MyDate;
import objects.Race;
import objects.RaceData;
import objects.Rider;
import objects.Stable;

public class Main {
	public static void main(String[] args) throws Exception {
		EditDatabase racesDb = new EditDatabase();
	/*
		racesDb.addStable(new Stable("Kozý sady", null));
		racesDb.addStable(new Stable("Dlouhé strany", null));
	
		racesDb.addRider(new Rider("Tomáš", "Marný"));
		racesDb.addRider(new Rider("Venca", "Pašák"));
		racesDb.addRider(new Rider("Venca", "Kozák"));
	
		racesDb.addHorse(new Horse("mařena", "Kozý sady"));
		racesDb.addHorse(new Horse("Venca", "Kozý sady"));
		racesDb.addHorse(new Horse("Šemík", "Kozý sady"));
		
		racesDb.addHorse(new Horse("Helena", "Dlouhé strany"));
		racesDb.addHorse(new Horse("Kozena", "Dlouhé strany"));
		racesDb.addHorse(new Horse("Božena", "Dlouhé strany"));
		
		racesDb.addRace(new Race(new MyDate(2001, 10, 5, 12, 10), "Kozov", "koňský přebor"));
	
		List<Horse> horses = racesDb.getHorse(new Stable("Kozý sady",null));
		List<Horse> horses2 = racesDb.getHorse(new Stable("Dlouhé strany",null));
		
		

		racesDb.addRaceData(new RaceData(horses.get(1), riders.get(1), "none", races.get(0)));
		racesDb.addRaceData(new RaceData(horses2.get(1), riders.get(2), "none", races.get(0)));

		racesDb.addBets(new Bets("Lord", horses.get(1).id, 5000, races.get(0).getIdTables()), races.get(0));
		*/

		List<Rider> riders = racesDb.getRider();
		List<Race> races = racesDb.getRace();
		List<Bets> bets = racesDb.getBets(); 
		
		System.out.println("WHAT?: " + bets);
		for(Race r : races){
			System.out.println(r.getIdTables()+" \n "+r.getDate().getStrDate()+" \n "+ r.getName()+" \n "+r.getPlace() + "\n" + r.state);
		}
		
		racesDb.getRaceData(1);
/*		
		racesDb.getPrint4(new MyDate(1999,1,2,1,1), new MyDate(2009, 10, 2, 3, 1));
		
		for(RaceData rD : racesDb.getRaceData(1)){
			System.out.println(rD.race.getName()+ " : "+ rD.horse.getName());
		}
*/
	}
}
