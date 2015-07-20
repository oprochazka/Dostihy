package objects;

public class MyDate {
	public int year;
	public int day;
	public int month;
	public int hour;
	public int minute;
	
	public MyDate(int year, int month, int day, int hour, int minute){
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
	}
	public MyDate(String str){
		year = Integer.parseInt(str.substring(0, 4));
		month = Integer.parseInt(str.substring(5, 7));
		day = Integer.parseInt(str.substring(8, 10));
		
		hour = Integer.parseInt(str.substring(11, 13));
		minute = Integer.parseInt(str.substring(14, 16));	
	}
	 
	public String getStrDate(){
		String minutes;
		
		if(minute < 10){
			minutes = "0" + new Integer(minute).toString();
		}else{
			minutes = new Integer(minute).toString();
		}
				
		return year+"-"+month+"-"+day+" "+hour+":"+minutes+":"+"00";
	}
	
}
