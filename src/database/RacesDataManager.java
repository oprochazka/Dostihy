package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import objects.Bets;

public class RacesDataManager {
	private PreparedStatement getAllNames;
	private PreparedStatement getByName;
	private PreparedStatement insert;
	private PreparedStatement update;
	private PreparedStatement delete;
	private PreparedStatement getRaceData;
	String tableName;
	Connection connect;
	public RacesDataManager(Connection con) throws EmployeeDBException, SQLException {		
		connect = con;
		
		try {					
			getRaceData = connect.prepareStatement("SELECT * FROM HORSE_RACES");
			getByName = connect.prepareStatement("SELECT * FROM HORSE_RACES WHERE id_race = ?");
			insert = connect.prepareStatement("INSERT INTO HORSE_RACES (id_race, id_horse, id_rider, position) VALUES (?,?,?,?)");			
			delete = connect.prepareStatement("DELETE FROM HORSE_RACES  WHERE id_race = ?");
	
		} catch (SQLException e) {
			throw new EmployeeDBException("Unable to initialize prepared statements.", e);
		}			
	}
	public void addRaceData(int id_race, int id_horse, int id_rider, String position) throws EmployeeDBException{
		try {					
			insert.setInt(1, id_race);
			insert.setInt(2, id_horse);
			insert.setInt(3, id_rider);
			insert.setString(4, position);
			insert.executeUpdate();			
			
		} catch (SQLException e) {
			throw new EmployeeDBException("Unable to create new horse", e);
		}			
	}
	
	public  void getRaceData() throws EmployeeDBException{

		try {
			try (ResultSet results = getRaceData.executeQuery()) {
				while (results.next()){								
					//Rider rider = new Rider(results.getString(5), results.getString(6));
					//rider.setId(results.getInt(7));
							
					
					System.out.println(": "+ results.getInt(1) + ": " + results.getInt(2) + ": " + results.getInt(3) + ": " +results.getString(4));
					
					
				}					
			}
		} catch (Exception e) {
			throw new EmployeeDBException("Unable to list all riders");
		}		
	}

	
	
}
