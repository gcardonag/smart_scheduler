package practiceclasses;

public class DateTime implements Comparable<DateTime> {
	
	private int ticks,minutes,hour,Day,Year;

	public int getTicks() {
		return ticks;
	}

	public int getMinutes() {
		return minutes;
	}

	public int getHour() {
		return hour;
	}

	public int getDay() {
		
		return Day;
	}

	public int getYear() {
		
		return Year;
	}

	@Override
	public int compareTo(DateTime arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

}
