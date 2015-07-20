package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import objects.Rider;

public class RiderManager {
	private PreparedStatement getAllNames;
	private PreparedStatement getByName;
	private PreparedStatement insert;
	private PreparedStatement update;
	private PreparedStatement delete;
	private PreparedStatement getRiders;
	
	Connection connect;
	public RiderManager(Connection con) throws EmployeeDBException {
		connect = con;
		try {
			getRiders = connect.prepareStatement("SELECT * FROM riders");
			getAllNames = connect.prepareStatement("SELECT firstname, lastname FROM riders");
			getByName = connect.prepareStatement("SELECT * FROM riders WHERE firstname = ? AND lastname = ?");
			insert = connect.prepareStatement("INSERT INTO riders (firstname, lastname) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
			update = connect.prepareStatement("UPDATE riders SET firstname = ?, lastname = ? WHERE firstname = ? AND lastname = ?");
			delete = connect.prepareStatement("DELETE FROM riders WHERE id = ?");			
		} catch (SQLException e) {
			throw new EmployeeDBException("Unable to initialize prepared statements.", e);
		}
	}
	
	public Rider addRider(Rider rider) throws SQLException, EmployeeDBException{
		try {
			String firstName = rider.getFirstName();
			String lastName = rider.getLastName();
			insert.setString(1, firstName);
			insert.setString(2, lastName);
		
			insert.executeUpdate();
			

			ResultSet rs = insert.getGeneratedKeys();
			int id = 0;
			
			if (rs.next()){ 
				 id = rs.getInt(1);
			}
			
			rider.setId(id);
				
			return null;
		} catch (SQLException e) {
			throw new EmployeeDBException("Unable to create new employee", e);
		}
	
	}
	
	public void deleteRider(int id) throws EmployeeDBException{
		try {
			delete.setInt(1, id);
			delete.executeUpdate();
		} catch (SQLException e) {
			throw new EmployeeDBException("Unable to delete sortiment", e);
		}
	}
	
	public List<Rider> getRiders() throws EmployeeDBException{
		List<Rider> riders = new ArrayList<Rider>();
		try {
			try (ResultSet results = getRiders.executeQuery()) {
				while (results.next()){
					Rider rider = new Rider(results.getString(2), results.getString(3));
					rider.setId(results.getInt(1));
					riders.add(rider);
				}					
			}
		} catch (Exception e) {
			throw new EmployeeDBException("Unable to list all riders");
		}
		return riders;
	}
	
}
