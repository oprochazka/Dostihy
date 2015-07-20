package objects;

public class Bets {
	public String nick;
	public int horse;
	public int amount; 
	public int race;
	public Bets(String nick, int horse,  int amount, int race){
		this.nick = nick;
		this.horse = horse;
		this.amount = amount;
		this.race = race;
	}
}
