package events;

public class TimeInterval {
	private int startTime;
	private int endTime;
	
	
	public TimeInterval(int sT, int eT){
		this.startTime = sT;
		this.endTime = eT;
	}
	
	public int getStartTime(){
		return startTime;
	}
	
	public int getEndTime(){
		return endTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}
	
	

}
