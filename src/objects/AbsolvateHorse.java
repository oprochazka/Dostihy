package objects;

public class AbsolvateHorse {
	public String name;
	public int count;
	public String str;
	public int count1; 
	public AbsolvateHorse(String name, int count){
		this.name = name;
		this.count = count;
	}
	public AbsolvateHorse(String name, int count, String str){
		this.name = name;
		this.count = count;
		this.str = str;
	}
	public AbsolvateHorse(String name, int count, String str, int count1){
		this.name = name;
		this.count = count;
		this.str = str;
		this.count1 = count1;
	}
	public AbsolvateHorse(int count1, int count){
		this.count1 = count1;
		this.count = count;
		
	}
	public AbsolvateHorse(int count1, int count,String str){
		this.count1 = count1;
		this.count = count;
		this.str = str;
		
	}
}
