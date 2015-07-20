package objects;

public class RaceData {
	public Horse horse;
	public Rider rider;
	public String position;
	public Race race;
	
	public RaceData(Horse horse, Rider rider, String position, Race race){
		this.horse = horse;
		this.rider = rider;
		this.position = position;
		this.race = race;
	}
}
