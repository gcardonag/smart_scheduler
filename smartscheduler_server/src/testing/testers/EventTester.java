package testing.testers;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class EventTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Calendar as = new GregorianCalendar();
		as.set(2012, Calendar.OCTOBER, 24, 4, 35);
		System.out.println(as.toString());
		Calendar ae = (Calendar)as.clone();
		ae.add(Calendar.HOUR, 1);
		System.out.println(ae.toString());

	}

}
