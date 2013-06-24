package tests.scheduling;

import java.util.Calendar;
import java.util.GregorianCalendar;

import scheduling.PomodoroScheduler;

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
		
		EventTree c = new EventTree();
		EventQueue q = new EventQueue();
		PomodoroScheduler p = new PomodoroScheduler(25, 5, 30);
		
		Calendar start = new GregorianCalendar();
		start.set(2013, Calendar.MARCH, 3, 8, 0);
		
		Calendar end = (Calendar) start.clone();
		start.set(2013, Calendar.MARCH, 3, 9, 0);
		
		Event aclass = new Event("Class", start, end, true, true);
		
		start.add(Calendar.HOUR, 1);
		end.add(Calendar.HOUR_OF_DAY, 7);
		
		Event studytime = new Event("Study", start, end, false, true);
		
		Calendar recend = (Calendar) start.clone();
		recend.add(Calendar.DAY_OF_YEAR, 6);

		boolean[] lwv = {false,true,false,true,false,true,false};
		RecurrenceGroup classrec = new RecurrenceGroup(aclass, RecurrenceGroup.WEEKLY, 1, recend, lwv);
		RecurrenceGroup studyrec = new RecurrenceGroup(studytime, RecurrenceGroup.WEEKLY, 1, recend, lwv);
		
		//SIMULATING SOME TIME MANAGEMENT ALGORITHM
		for(Event e : classrec.getEventsInRecurrenceGroup())
			c.add(e);
		for(Event e : studyrec.getEventsInRecurrenceGroup())
			q.offer(e);
		c.add(p.implementPomodoroToList(q));
		for(Event e : c)
			System.out.println(e.toString());
		
	}

}
