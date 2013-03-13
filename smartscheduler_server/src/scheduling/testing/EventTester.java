package scheduling.testing;

import java.util.Calendar;
import java.util.GregorianCalendar;
import eventCollection.Event;

public class EventTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Calendar as = new GregorianCalendar();
		
		as.set(2012, Calendar.OCTOBER, 24, 23, 00);
		Calendar ae = (Calendar)as.clone();
		ae.add(Calendar.HOUR_OF_DAY, 2);
		Event e1 = new Event("E1", as, ae, false, false);
		
		as.add(Calendar.DAY_OF_YEAR, 1);
		ae.add(Calendar.DAY_OF_YEAR, 1);
		Event e2 = new Event("E2", as, ae, false, false);
		
		System.out.println(e1.toString());
		System.out.println(e2.toString());
		
		e1.setDate(as, ae);
		
		System.out.println(e1);
		


		
	}

}
