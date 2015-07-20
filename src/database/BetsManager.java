package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import objects.Bets;
import objects.Horse;
import objects.Rider;
import objects.Stable;

public class BetsManager {
	private PreparedStatement getAllNames;
	private PreparedStatement getByName;
	private PreparedStatement insert;
	private PreparedStatement update;
	private PreparedStatement delete;
	private PreparedStatement getBets;
	String tableName;
	Connection connect;
	public BetsManager(Connection con) throws EmployeeDBException, SQLException {		
		connect = con;
		
		try {		
			getBets = connect.prepareStatement("SELECT * FROM bets BETS");
			getAllNames = connect.prepareStatement("SELECT nick FROM bets");
			getByName = connect.prepareStatement("SELECT * FROM bets WHERE nick = ?");
			insert = connect.prepareStatement("INSERT INTO bets (nick, id_race, id_horse, amount) VALUES (?,?,?,?)");	
			
			delete = connect.prepareStatement("DELETE FROM bets WHERE nick = ?");
	
		} catch (SQLException e) {
			throw new EmployeeDBException("Unable to initialize prepared statements.", e);
		}
			
	}	

	public void addBets(String nick, int id_race, int id_horse, int amount ) throws EmployeeDBException{
		try {
			System.out.println("insert");
			insert.setString(1,nick);
			insert.setInt(2,id_race);
			insert.setInt(3,id_horse);
			insert.setInt(4,amount);
			
			insert.executeUpdate();						
		} catch (SQLException e) {
			throw new EmployeeDBException("Unable to create new horse", e);
		}
	}
	
	public  List<Bets> getBets() throws EmployeeDBException{
		List<Bets> bets= new ArrayList<Bets>();
		try {
			try (ResultSet results = getBets.executeQuery()) {
				
				while (results.next()){								
					//Rider rider = new Rider(results.getString(5), results.getString(6));
					//rider.setId(results.getInt(7));
					System.out.println("here");	
					Bets bet = new Bets(results.getString(1),  results.getInt(2), results.getInt(3), results.getInt(4));
					
					System.out.println(": "+ results.getString(1) + ": " + results.getInt(2) + ": " + results.getInt(3) + ": " +results.getInt(4));
					
					bets.add(bet);
				}					
			}
		} catch (Exception e) {
			throw new EmployeeDBException("Unable to list all riders");
		}
		return bets;
	}
	
	public void removeBets(String nick) throws EmployeeDBException{
		try{
			delete.setString(1, nick);
			delete.executeUpdate();
		}
		catch (SQLException e) {
			throw new EmployeeDBException("Unable to delete Bets", e);
		}
	}
	
}
