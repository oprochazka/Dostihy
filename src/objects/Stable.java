package objects;

import java.util.List;

public class Stable {
	String name;
	List<Horse> horses;
	int id_horses;
	
	public Stable(String name, List<Horse> horses)
	{
		this.name = name;
		this.horses = horses; 
	}
	public void addHorse(Horse horse)
	{
		horses.add(horse);
	}
	public String getName()
	{
		return name;
	}
	public List<Horse> getHorses(){
		return horses;
	}
	public void setId_horses(int id){
		id_horses = id;
	}
	public int getId_horses(){
		return id_horses;
	}
	
}
