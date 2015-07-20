package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import objects.AbsolvateHorse;
import objects.Bets;
import objects.Horse;
import objects.MyDate;
import objects.Race;
import objects.RaceData;
import objects.Rider;
import objects.Stable;

public class EditDatabase {
	private static Connection connect;
	
	public EditDatabase() throws Exception{
		connect = DriverManager.getConnection("jdbc:derby:demo;create=true");
		prepareDB(connect);
		/*
		try (Statement stmt = connect.createStatement()) {
			
		}
		*/
	}
	private static void prepareDB(Connection con) throws SQLException {
		try (Statement stmt = con.createStatement()) {
			if( isReady(con, "RIDERS") )
				//stmt.executeUpdate("DROP TABLE riders");
			if( isReady(con, "RACES") )
			//	stmt.executeUpdate("DROP TABLE races");
			if( isReady(con, "STABLES") )
				//stmt.executeUpdate("DROP TABLE stables");
			
			if (!isReady(con, "RACES")) {
				stmt.executeUpdate("CREATE TABLE races (id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)," +
							"name VARCHAR(50), date TIMESTAMP, place VARCHAR(50), state VARCHAR(10), PRIMARY KEY(id))");
			}			
			if (!isReady(con,"RIDERS")) {			
				stmt.executeUpdate("CREATE TABLE riders (id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)," +
						"firstname VARCHAR(30), lastname VARCHAR(30), PRIMARY KEY(id))"); 
			}
			if (!isReady(con,"STABLES")){
				stmt.executeUpdate("CREATE TABLE stables (name VARCHAR(30), PRIMARY KEY(name))");
			}		
			
			if (EditDatabase.isReady(con, "HORSES")){
				//stmt.executeUpdate("DROP TABLE HORSES");
			}
			if (!EditDatabase.isReady(con,"HORSES")) {			
				stmt.executeUpdate("CREATE TABLE HORSES (id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)," +
						"name VARCHAR(30), stable VARCHAR(30), PRIMARY KEY(id))");			
			}		
			if (EditDatabase.isReady(connect,"HORSE_RACES")){
			//			stmt.executeUpdate("DROP TABLE horse_races");
			}
			if (!EditDatabase.isReady(connect,"HORSE_RACES")) {			
				stmt.executeUpdate("CREATE TABLE horse_races (id_horse INT, id_race INT, id_rider INT, position VARCHAR(20))");
			}
			if (EditDatabase.isReady(connect,"BETS")){
		//		stmt.executeUpdate("DROP TABLE BETS");
			}
			if (!EditDatabase.isReady(connect,"BETS")) {			
				stmt.executeUpdate("CREATE TABLE BETS (nick VARCHAR(30), id_race INT, id_horse INT, amount INT," +
						"PRIMARY KEY(nick,id_race))");
			}
		}	
		
		
	}
	
	protected static boolean isReady(Connection con, String table) throws SQLException {
		DatabaseMetaData dbm = con.getMetaData();
		try (ResultSet tables = dbm.getTables(null, null, table, null)) {
			return tables.next();
		}
	}
	
	public List<Horse> getHorses(String stable) throws EmployeeDBException{
		List<Horse> horses= new ArrayList<Horse>();
		try (Statement stmt = connect.createStatement()){			
			try (ResultSet results = stmt.executeQuery("SELECT * FROM horses WHERE stable = '" + stable + "'")) {
				while (results.next()){
					Horse horse = new Horse(results.getString(1), results.getString(2));					
					horses.add(horse);
				}					
			}
		} catch (Exception e) {
			throw new EmployeeDBException("Unable to list all horses",e);
		}
		return horses;
	}
	
	public List<RaceData> getRaceData(int id_race) throws SQLException, EmployeeDBException{
		PreparedStatement getData;
		
		getData = connect.prepareStatement("SELECT place, date,name,stable,firstname,lastname,id_rider, position, horse_name,id_horse FROM" +
				"(SELECT * FROM (SELECT * FROM (SELECT * FROM races,horse_races WHERE id = id_race) as foo WHERE id = ?) as blah " +
				"NATURAL JOIN " +
				"(SELECT stable, name as horse_name, id as id_horse FROM horses)as heh) as x" +
				" NATURAL JOIN " +
				"(SELECT id as id_rider, firstname,lastname FROM riders) as b");

		List<RaceData> raceData= new ArrayList<RaceData>();

		try {
			getData.setInt(1, id_race);
			try (ResultSet results = getData.executeQuery()) {
				
				while (results.next()){
					Rider rider = new Rider(results.getInt(7),results.getString(5), results.getString(6));
					
					RaceData rD = new RaceData(new Horse(results.getString(9), results.getString(4), results.getInt(10)), rider, results.getString(8), 
									new Race(new MyDate(results.getString(2)), results.getString(1), results.getString(3)));
				
					raceData.add(rD);
				}					
			}
		} catch (Exception e) {
			throw new EmployeeDBException("Unable to list all Race data",e);
		}
		return raceData;			
	}

	public void addRider(Rider rider) throws EmployeeDBException, SQLException{
		RiderManager rm = new RiderManager(connect);
		rm.addRider(rider);
	}
	
	public List<Rider> getRider() throws EmployeeDBException{
		RiderManager rm = new RiderManager(connect);
		return rm.getRiders();
	}
	
	public void addStable(Stable stable) throws EmployeeDBException{
		StableManager sm = new StableManager(connect);
		sm.addStable(stable);
	}
	
	public void addHorse(Horse horse) throws EmployeeDBException, SQLException{
		HorseManager hm = new HorseManager(connect);
		hm.addHorse(horse);
	}
	
	public void addRace(Race race) throws EmployeeDBException{
		RacesManager rama = new RacesManager(connect);
		rama.addRace(race);
	}
	public void addBets(Bets bets) throws EmployeeDBException, SQLException{
		BetsManager bM = new BetsManager(connect);
		bM.addBets(bets.nick, bets.race, bets.horse, bets.amount);
	}
	public void addRaceData(RaceData data) throws EmployeeDBException, SQLException{
		RacesDataManager rDM = new RacesDataManager(connect);
		rDM.addRaceData(data.race.getIdTables(), data.horse.id, data.rider.getId(), data.position);
	}
	public List<Stable> getStable() throws EmployeeDBException{
		StableManager sm = new StableManager(connect);
		return sm.getStables();
	}
	public List<Horse> getHorse() throws EmployeeDBException, SQLException{
		HorseManager hM = new HorseManager(connect);
		return hM.getHorses();
	}
	public List<Horse> getHorse(Stable stable) throws SQLException, EmployeeDBException{
		PreparedStatement getData;
		getData = connect.prepareStatement("SELECT id,name,stable FROM horses WHERE stable = ?");

		List<Horse> horseData= new ArrayList<Horse>();
		try {
			getData.setString(1, stable.getName());
			try (ResultSet results = getData.executeQuery()) {				
				while (results.next()){
					Horse horse = new Horse(results.getString(2), results.getString(3));
					horse.id = results.getInt(1);
					horseData.add(horse);
				}	
			}
			} catch (Exception e) {
				throw new EmployeeDBException("Unable to list all Race data",e);			
		}
		return horseData;			
	}
		
	public List<Race> getRace() throws EmployeeDBException{
		RacesManager rM = new RacesManager(connect);
		
		return rM.getRaces();
	}
	public List<Bets> getBets() throws EmployeeDBException, SQLException{
		BetsManager bM = new BetsManager(connect);
		return bM.getBets();
	}
	
	public void updatePosition(RaceData data, String position) throws SQLException{
		PreparedStatement stmt = connect.prepareStatement("UPDATE TABLE horse_races SET position=? WHERE id_race = ?, id_horse = ?");
		stmt.setString(1, position);
		stmt.setInt(2, data.race.getIdTables());
		stmt.setInt(3, data.horse.id);
		stmt.executeUpdate();
	}
	
	public List<AbsolvateHorse> getPrint4(MyDate date1, MyDate date2) throws SQLException, EmployeeDBException{
		PreparedStatement stmt = connect.prepareStatement("SELECT horse_name, COUNT(*) as pocet FROM (SELECT * FROM (SELECT id as id_race FROM races WHERE date > ? and date < ?) as b NATURAL JOIN horse_races)as x  " +
				"NATURAL JOIN" +
				"(SELECT id as id_horse, name as horse_name FROM horses) as foo GROUP BY horse_name");
		
		List<AbsolvateHorse> horseData= new ArrayList<AbsolvateHorse>();
		try {
			stmt.setString(1, date1.getStrDate());
			stmt.setString(2, date2.getStrDate());
			try (ResultSet results = stmt.executeQuery()) {				
				while (results.next()){
					AbsolvateHorse horse = new AbsolvateHorse(results.getString(1), results.getInt(2));										
					
					horseData.add(horse);
				}	
			}}
			 catch (Exception e) {
				throw new EmployeeDBException("Unable to list all Race data",e);			
		}
					
	
		return horseData;
	}
	public List<AbsolvateHorse> getPrint1(int id) throws SQLException, EmployeeDBException{
			PreparedStatement stmt = connect.prepareStatement("SELECT horse_name, suma FROM "+
	"(SELECT id_horse, sum(amount) as suma FROM "+ 
		"(SELECT id_horse, amount FROM (SELECT id as id_race FROM races  WHERE id = ?) as x NATURAL JOIN bets) as r GROUP BY id_horse) as r1 "+
	"NATURAL JOIN " +  
	"(SELECT id as id_horse, name as horse_name  FROM horses) as t");
	
		
			
		
	//	PreparedStatement stmt = connect.prepareStatement("SELECT id as id_horse,name FROM horses");*/
		List<AbsolvateHorse> horseData= new ArrayList<AbsolvateHorse>();
		stmt.setInt(1, id);
		try {
		/*	stmt.setString(1, date1.getStrDate());
			stmt.setString(2, date2.getStrDate());*/
			try (ResultSet results = stmt.executeQuery()) {				
				while (results.next()){
					AbsolvateHorse horse = new AbsolvateHorse(results.getString(1), results.getInt(2));										
					System.out.println(results.getInt(2) + " " + results.getString(1));
					horseData.add(horse);
				}	
			}}
					
			 catch (Exception e) {
				throw new EmployeeDBException("Unable to list all Race data",e);			
		}
					
	
		return horseData;
	}
	public List<AbsolvateHorse> getPrint2(String name,MyDate date1, MyDate date2) throws SQLException, EmployeeDBException{
		PreparedStatement stmt = connect.prepareStatement("SELECT horse_name,id_rider, position, id_race FROM (SELECT * FROM (SELECT id as id_race FROM races WHERE date > ? and date < ?) as b NATURAL JOIN horse_races WHERE NOT position = '-')as x  " +
				"NATURAL JOIN" +
				"(SELECT id as id_horse, name as horse_name FROM horses WHERE name = ?) as foo ");
		
		List<AbsolvateHorse> horseData= new ArrayList<AbsolvateHorse>();
		try {
			stmt.setString(1, date1.getStrDate());
			stmt.setString(2, date2.getStrDate());
			stmt.setString(3, name);
			try (ResultSet results = stmt.executeQuery()) {				
				while (results.next()){
					AbsolvateHorse horse = new AbsolvateHorse(results.getString(1), results.getInt(2), results.getString(3),results.getInt(4));										
					System.out.println(results.getString(1));
					horseData.add(horse);
				}	
			}}
			 catch (Exception e) {
				throw new EmployeeDBException("Unable to list all Race data",e);			
		}
					
	
		return horseData;
	}
	
	public List<AbsolvateHorse> getPrint3(int month1, int month2) throws SQLException, EmployeeDBException{
		PreparedStatement stmt = connect.prepareStatement("SELECT suma, id_race, name FROM (SELECT sum(amount) as suma, id_race FROM (" +
				"SELECT id as id_race FROM races WHERE month(date) > ? and month(date) < ?) as b NATURAL JOIN bets GROUP BY id_race) as b NATURAL JOIN " +
				"(SELECT id as id_race, name FROM races) as k");
		
		List<AbsolvateHorse> horseData= new ArrayList<AbsolvateHorse>();
		
		try {
			stmt.setInt(1, month1);
			stmt.setInt(2, month2);
			try (ResultSet results = stmt.executeQuery()) {				
				while (results.next()){
					AbsolvateHorse horse = new AbsolvateHorse(results.getInt(2),results.getInt(1),results.getString(3));										
			
					horseData.add(horse);
				}	
			}}
			 catch (Exception e) {
				throw new EmployeeDBException("Unable to list all Race data",e);			
		}
					
	
		return horseData;
	}
	
	
}
