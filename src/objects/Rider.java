package objects;

public class Rider {
	String firstName;
	String lastName;
	int id;
	
	public Rider(String firstName, String lastName)
	{
		this.firstName = firstName;
		this.lastName = lastName;		
	}
	public Rider(int id, String firstName, String lastName)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.id = id;
	}
	public String getFirstName()
	{
		return firstName;
	}
	public String getLastName()
	{
		return lastName;
	}
	public void setId(int id){
		this.id = id; 
	}
	public int getId(){
		return id;
	}
	 @Override
	 public String toString(){
		 return firstName + " - " + lastName;
	 }
}
