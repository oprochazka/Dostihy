package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



import objects.Bets;
import objects.Horse;
import objects.MyDate;
import objects.Race;
import objects.Stable;

public class RacesManager {
	private PreparedStatement getAllNames;
	private PreparedStatement getByName;
	private PreparedStatement insert;
	private PreparedStatement update;
	private PreparedStatement delete;
	private PreparedStatement getRaces;
	
	Connection connect;
	public RacesManager(Connection con) throws EmployeeDBException {
		connect = con;
		try {
			getRaces = connect.prepareStatement("SELECT * FROM races");
			getAllNames = connect.prepareStatement("SELECT name FROM races");
			getByName = connect.prepareStatement("SELECT * FROM races WHERE name = ?");
			insert = connect.prepareStatement("INSERT INTO races (name, date, place, state) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			update = connect.prepareStatement("UPDATE races SET name = ? WHERE name = ?");
			delete = connect.prepareStatement("DELETE FROM races WHERE name = ?");
				
		} catch (SQLException e) {
			throw new EmployeeDBException("Unable to initialize prepared statements.", e);
		}
	}	
	
	public void addRace(Race races) throws EmployeeDBException{
		try {			
			String name = races.getName();
			String place = races.getPlace();
			MyDate date = races.getDate();
					
			insert.setString(1, name);
			insert.setString(2, date.getStrDate());
			insert.setString(3, place);
			insert.setString(4, races.state);
			
			insert.executeUpdate();

			ResultSet rs = insert.getGeneratedKeys();
			int id = 0;
			
			if (rs.next()){ 
				 id = rs.getInt(1);
			}
			
			races.setIdTables(id);
			
		} catch (SQLException e) {
			throw new EmployeeDBException("Unable to create new Race", e);
		}	
	}
	
	public List<Race> getRaces() throws EmployeeDBException{
		List<Race> races = new ArrayList<Race>();
		try {
			try (ResultSet results = getRaces.executeQuery()) {
				while (results.next()){
					Race race = new Race(new MyDate(results.getString(3)),results.getString(2),results.getString(4));
					race.setIdTables(results.getInt(1));					
					race.state= results.getString(5);
					races.add(race);
				}					
			}
		} catch (Exception e) {
			throw new EmployeeDBException("Unable to list all riders");
		}
		return races;
	}
}
