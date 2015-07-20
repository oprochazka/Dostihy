package objects;

public class Horse {
	String name;
	public String stable;
	public int id;
	
	public Horse(String name, String stable)
	{
		this.name = name;
		this.stable = stable;
	}
	public Horse(String name, String stable, int id)
	{
		this.name = name;
		this.stable = stable;
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	 @Override
	 public String toString(){
		 return name;
	 }
}
