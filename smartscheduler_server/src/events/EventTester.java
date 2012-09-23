package events;

import java.text.ParseException;
import java.util.ArrayList;

public class EventTester {
		
	public static void main(String[] args) throws ParseException{
		Event e = new Event("Clases",787,878);
		Event e1 = new Event(500,615);
		//Event e2 = new Event("Clases",715,640);
		Event e3 = new Event("Cita en el Medico", 400,500, 1,"Marzo 12 2012");
		Event e4 = new Event("Cita en el Medico",new TimeInterval(300,430),3);
		Event e5 = new Event("Jugar Basket",new TimeInterval(1000,1100));
		
		
		System.out.println(""+ e1.isvalidSTime(e1.getStartTime())+ "," + e1.isvalidETime(e1.getEndTime()));
		//System.out.println("" + e1.checkBreak(e1,e2));
		System.out.println("" + e1.durationEvent());
		
		System.out.println("" + e3.getDate());
		
		ArrayList<Event> a = new ArrayList<Event>();
		a.add(e);
		a.add(e1);
		//a.add(e2);
		a.add(e3);
		a.add(e4);
		a.add(e5);
		
		System.out.println("Event: " + e.getName() + ",Start Time: "
				+ e.returnHour(e.getStartTime())+ ":0" + e.returnMinutes(e.getStartTime())
						+ ", End Time: " + e.returnHour(e.getEndTime())+ ":" + e.returnMinutes(e.getEndTime()));
		
		System.out.println("Start Time: " + e1.returnHour(e1.getStartTime())
				+ ":" + e1.returnMinutes(e1.getStartTime()) + ", End Time: " 
				+ e1.returnHour(e1.getEndTime())+ ":" + e1.returnMinutes(e1.getEndTime()));
		
		System.out.println("Event: " + e.getName() + ",Start Time: "
				+ e1.returnHour(e1.getStartTime())+ ":" + e1.returnMinutes(e1.getStartTime())
				+ ",End Time: "+ e1.returnHour(e1.getEndTime())+ ":" + e1.returnMinutes(e1.getEndTime()));
		
		System.out.println("Event 1 : " + e1.toStringTimeInterval());
		
		printEvents(a);
				
	}
	
	/**
	DateFormat df = new SimpleDateFormat("dd/MM/yy");
	String formattedDate = df.format(new Date());

	 String date1 = sdf.format(calendar1.getTime());
	         String date2 = sdf.format(calendar2.getTime());
	         if((calendar1.compareTo(calendar2)) < 0)
	         {
	             System.out.println(date1 + " occurs before " + date2);
	         }
	         else if((calendar1.compareTo(calendar2)) > 0)
	         {
	             System.out.println(date1 + " occurs after " + date2);
	         }
	         else
	         {
	             System.out.println("The two dates are identical: " + date1);
	         }
	         **/
	
	public static void printEvents(ArrayList<Event>a){
		for(int i =0;i<a.size();i++){
			
			if((a.get(i).returnMinutes(a.get(i).getStartTime())< 10)){
				System.out.println("Start Time: " + a.get(i).returnHour(a.get(i).getStartTime())
						+ ":0" + a.get(i).returnMinutes(a.get(i).getStartTime()) + ", End Time: " 
						+ a.get(i).returnHour(a.get(i).getEndTime())+ ":" + a.get(i).returnMinutes(a.get(i).getEndTime()));
			}
			else if((a.get(i).returnMinutes(a.get(i).getEndTime())< 10)){
				System.out.println("Start Time: " + a.get(i).returnHour(a.get(i).getStartTime())
						+ ":" + a.get(i).returnMinutes(a.get(i).getStartTime()) + ", End Time: " 
						+ a.get(i).returnHour(a.get(i).getEndTime())+ ":0" + a.get(i).returnMinutes(a.get(i).getEndTime()));
			}
			else if(a.get(i).getEndTime()< a.get(i).getStartTime()){
				System.out.println("Invalid Time");
			}
				
			else
				System.out.println(a.get(i));
			
		}
	}
}
