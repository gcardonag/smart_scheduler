package scheduling.testing;

import java.util.Calendar;
import java.util.GregorianCalendar;

import eventCollection.*;

/**
 * Tester that will test the all the scheduling classes at the same time.
 * @author user
 *
 */
public class SchedulingTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		EventTree tree = new EventTree();
		createMyStaticSchedule(tree);

	}
	
	 static void createMyStaticSchedule(EventTree tree) {
		
			
		Calendar start = new GregorianCalendar();
		Calendar end = new GregorianCalendar();
		Calendar recend = new GregorianCalendar();

		boolean[] lwv = {false,true,false,true,false,true,false};
		boolean[] mj = {false,false,true,false,true,false,false};
		boolean[] l = {false,true,false,false,false,false,false};
		boolean[] w = {false,false,false,true,false,false,false};
		recend.set(2013, 4, 14);
		
		//Statics & Dynamics
		start.set(2013, 0, 16, 7, 30);
		end.set(2013, 0, 16, 8, 20);
		Event sde = new Event("SD", start, end, true, true);
		RecurrenceGroup sdr = new RecurrenceGroup(sde, RecurrenceGroup.WEEKLY, 1, recend, lwv);
		for(Event e : sdr)
			tree.add(e);
		
		//Digital
		start.set(2013, 0, 16, 8, 30);
		end.set(2013, 0, 16, 9, 20);
		Event dige = new Event("Digital", start, end, true, true);
		RecurrenceGroup digr = new RecurrenceGroup(dige, RecurrenceGroup.WEEKLY, 1, recend, lwv);
		for(Event e : digr)
			tree.add(e);
		
		//CCNP
		start.set(2013, 0, 16, 11, 30);
		end.set(2013, 0, 16, 12, 20);
		Event ccnpe = new Event("CCNP", start, end, true, true);
		RecurrenceGroup ccnpr = new RecurrenceGroup(ccnpe, RecurrenceGroup.WEEKLY, 1, recend, lwv);
		for(Event e : ccnpr)
			tree.add(e);
		
		//Algorithms
		start.set(2013, 0, 16, 15, 30);
		end.set(2013, 0, 16, 18, 20);
		Event ae = new Event("Algorithms", start, end, true, true);
		RecurrenceGroup ar = new RecurrenceGroup(ae, RecurrenceGroup.WEEKLY, 1, recend, l);
		for(Event e : ar)
			tree.add(e);
		
		//Micro
		start.set(2013, 0, 17, 9, 00);
		end.set(2013, 0, 17, 10, 15);
		Event mpe = new Event("Micro", start, end, true, true);
		RecurrenceGroup mpr = new RecurrenceGroup(mpe, RecurrenceGroup.WEEKLY, 1, recend, mj);
		for(Event e : mpr)
			tree.add(e);
		
		//Networking
		start.set(2013, 0, 17, 12, 30);
		end.set(2013, 0, 17, 13, 45);
		Event nete = new Event("Networking", start, end, true, true);
		RecurrenceGroup netr = new RecurrenceGroup(nete, RecurrenceGroup.WEEKLY, 1, recend, mj);
		for(Event e : netr)
			tree.add(e);
		
		//Cloud
		start.set(2013, 0, 16, 14, 30);
		end.set(2013, 0, 16, 15, 20);
		Event cloude = new Event("Cloud", start, end, true, true);
		RecurrenceGroup cloudr = new RecurrenceGroup(cloude, RecurrenceGroup.WEEKLY, 1, recend, w);
		for(Event e : cloudr)
			tree.add(e);
		
	}

}
