package objects;

import java.util.List;

public class Race {
	private int idTables;
	private MyDate date;
	private String place;
	private String name;
	public String state = new String("-");
	public Race(MyDate dateTime, String place, String name){
		date = dateTime;
		this.place = place;
		this.name = name;
	}
	public Race(MyDate dateTime, String place, String name, String state){
		date = dateTime;
		this.place = place;
		this.name = name;
		this.state = state;
	}
	public Race(String time, String place, String name){
		System.out.println(time + " " + place);
	}
	
	public void setIdTables(int id){
		idTables = id;
	}

	public int getIdTables(){
		return idTables;
	}
	public MyDate getDate(){
		return date;
	}
	public String getPlace(){
		return place;
	}
	public String getName(){
		return name;
	}
}
