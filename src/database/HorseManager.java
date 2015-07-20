package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import objects.Horse;
import objects.Stable;

public class HorseManager {
	private PreparedStatement getAllNames;
	private PreparedStatement getByStable;
	private PreparedStatement insert;
	private PreparedStatement update;
	private PreparedStatement delete;
	private PreparedStatement getHorses;
	String tableName;
	Connection connect;
	public HorseManager(Connection con) throws EmployeeDBException, SQLException {		
		connect = con;

		try {
			getHorses = connect.prepareStatement("SELECT * FROM horses");
			getAllNames = connect.prepareStatement("SELECT name FROM horses");
			getByStable = connect.prepareStatement("SELECT * FROM horses WHERE stable = ?");
			insert = connect.prepareStatement("INSERT INTO horses (name, stable) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
			update = connect.prepareStatement("UPDATE horses SET name = ? WHERE name = ?");
			delete = connect.prepareStatement("DELETE FROM horses  WHERE name = ?");
	
		} catch (SQLException e) {
			throw new EmployeeDBException("Unable to initialize prepared statements.", e);
		}
			
	}	

	
	public void addHorse(Horse horse) throws EmployeeDBException{
		try {
			String name = horse.getName();			
			
			insert.setString(1, name);		
			insert.setString(2, horse.stable);
			insert.executeUpdate();			
			

			ResultSet rs = insert.getGeneratedKeys();
			int id = 0;
			
			if (rs.next()){ 
				 id = rs.getInt(1);
			}
			
			horse.id = id;
			
		} catch (SQLException e) {
			throw new EmployeeDBException("Unable to create new horse", e);
		}			
	}
	
	public List<Horse> getHorses() throws EmployeeDBException{
		List<Horse> horses= new ArrayList<Horse>();
		try {
			try (ResultSet results = getHorses.executeQuery()) {
				while (results.next()){
					Horse horse = new Horse(results.getString(2), results.getString(3));			
					System.out.println(results.getInt(1) +" - "+ results.getString(2) +" - "+ results.getString(3));
					horse.id = results.getInt(1);
					horses.add(horse);
				}					
			}
		} catch (Exception e) {
			throw new EmployeeDBException("Unable to list all riders");
		}
		return horses;
	}
			
	public void deleteHorse(String name) throws EmployeeDBException{
		try {
			delete.setString(1, name);
			delete.executeUpdate();
		} catch (SQLException e) {
			throw new EmployeeDBException("Unable to delete Horse", e);
		}
	}
}
