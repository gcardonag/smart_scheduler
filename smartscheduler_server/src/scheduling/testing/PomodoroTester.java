package scheduling.testing;

import java.util.Calendar;
import java.util.GregorianCalendar;

import scheduler.PomodoroCreator;

import eventCollection.*;

/**
 * Tests the PomodoroCreator in conjunction with RecurrenceGroup.
 * @author user
 *
 */
public class PomodoroTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		EventCollection c = new EventCollection();
		EventQueue q = new EventQueue();
		PomodoroCreator p = new PomodoroCreator(25, 5, 30);
		
		Calendar start = new GregorianCalendar();
		start.set(2013, Calendar.MARCH, 3, 8, 0);
		
		Calendar end = (Calendar) start.clone();
		end.add(Calendar.HOUR_OF_DAY, 1);
		
		Event aclass = new Event("Class", start, end, true, true);
		
		start = (Calendar) end.clone();
		end.add(Calendar.HOUR_OF_DAY, 7);
		
		Event studytime = new Event("Study", start, end, false, true);
		
		Calendar recend = (Calendar) start.clone();
		recend.add(Calendar.DAY_OF_YEAR, 6);

		boolean[] w = {false,true,false,true,false,true,false};
		RecurrenceGroup classrec = new RecurrenceGroup(aclass, RecurrenceGroup.WEEKLY, 1, recend, w);
		RecurrenceGroup studyrec = new RecurrenceGroup(studytime, RecurrenceGroup.WEEKLY, 1, recend, w);
		
		//SIMULATING SOME TIME MANAGEMENT ALGORITHM
		for(Event e : classrec.getEventsInRecurrenceGroup())
			c.add(e);
		for(Event e : studyrec.getEventsInRecurrenceGroup())
			q.offer(e);
		while(!q.isEmpty()) {
			Iterable<Event> pomodoros = p.getPomodorosFromEvent(q.remove());
			for(Event e : pomodoros)
				c.add(e);
		}
		for(Event e : c)
			System.out.println(e.toString());
		
	}

}
