package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import objects.Horse;
import objects.Rider;
import objects.Stable;

public class StableManager {
	private PreparedStatement getAllNames;
	private PreparedStatement getByName;
	private PreparedStatement insert;
	private PreparedStatement update;
	private PreparedStatement delete;
	private PreparedStatement getStables;
	private PreparedStatement addHorseStable;
	private PreparedStatement dropHorse;
	Connection connect;
	public StableManager(Connection con) throws EmployeeDBException {
		connect = con;
		try {
			getStables = connect.prepareStatement("SELECT * FROM stables");
			getAllNames = connect.prepareStatement("SELECT name FROM stables");
			getByName = connect.prepareStatement("SELECT * FROM stables WHERE name = ?");
			insert = connect.prepareStatement("INSERT INTO stables (name) VALUES (?)");
			update = connect.prepareStatement("UPDATE stables SET name = ? WHERE name = ?");
			delete = connect.prepareStatement("DELETE FROM stables WHERE name = ?");
		} catch (SQLException e) {
			throw new EmployeeDBException("Unable to initialize prepared statements.", e);
		}
	}	
	
	public void addStable(Stable stable) throws EmployeeDBException{
		try {			
			String name = stable.getName();			
			insert.setString(1, name);
			insert.executeUpdate();
						
		} catch (SQLException e) {
			throw new EmployeeDBException("Unable to create new Stable", e);
		}	
	}
	
	
	public List<Stable> getStables() throws EmployeeDBException{
		List<Stable> stables = new ArrayList<Stable>();
		try {
			try (ResultSet results = getStables.executeQuery()) {
				while (results.next()){
					Stable stable = new Stable(results.getString(1),null);					
					stables.add(stable);
				}					
			}
		} catch (Exception e) {
			throw new EmployeeDBException("Unable to list all riders");
		}
		return stables;
	}
		
	public void deleteStable(int id) throws EmployeeDBException{
		try {
			delete.setInt(1, id);
			delete.executeUpdate();

		} catch (SQLException e) {
			throw new EmployeeDBException("Unable to delete Stable", e);
		}
	}
	
}
