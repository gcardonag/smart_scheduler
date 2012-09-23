package testing;

import java.util.ArrayList;
import java.util.Date;

import scheduler.Event;



public class MainTester extends TimedTester {

	public static void main(String[] args) {
		
		MainTester mainTester = new MainTester();
		if (mainTester.runTest())
			System.out.println("All Tests Passed");
		else
			System.out.println("Test Failed");
		
		System.out.println(mainTester.getExtraInformation());
	}
	
	public MainTester()
	{ }

	public String getName() {
		return "Main Tester";
	}

	protected boolean runInternalTest() {
		
		/* Pending issues:
		 * 1. Should Events use Date or Calendar for their dates?
		 * 2. Events should be specified in terms of an interval that is inclusive on the
		 * left but exclusive on the right. Conflicts should be checked accounting for that.
		 */
		
		/* Missing tests:
		 * 1. Event.compareTo()
		 * 2. Event.conflictsWith()
		 * 3. Event.containsTime()
		 * 4. EventCollection.conflictsWith()
		 * 5. EventCollection.find()
		 * 6. EventCollection.clone()
		 * 7. EventCollection.clear()
		 */
		
		PermutationTester<Event> permuter;
		PermutationTester2<Event, Event[]> permuter2;
		Event[] lessEvents = createTestEvents(7);
		Event[] moreEvents = createTestEvents(10);
		Event[] duplicateEvents = duplicateEvents(moreEvents);
		
		//Test adding elements to the tree
		permuter = new PermutationTester<Event>(Event.class, moreEvents, new AdditionTester());
		if (!permuter.runTest())
		{
			extraInformation += permuter.getExtraInformation();
			return false;
		}
		
		//Test adding elements to the tree with duplicates
		permuter = new PermutationTester<Event>(Event.class, duplicateEvents, new AdditionTester(), 2, 7);
		if (!permuter.runTest())
		{
			extraInformation += permuter.getExtraInformation();
			return false;
		}
		
		//Test adding elements to the tree and removing one
		permuter = new PermutationTester<Event>(Event.class, duplicateEvents, new SingleRemovalTester(), 2, 7);
		if (!permuter.runTest())
		{
			extraInformation += permuter.getExtraInformation();
			return false;
		}
		
		//Test adding elements to the tree in all possible orderings
		//and then removing them all in all possible orderings
		permuter2 = new PermutationTester2<Event, Event[]>(Event.class, lessEvents, new MultipleRemovalTester());
		permuter = new PermutationTester<Event>(Event.class, lessEvents, permuter2);
		if (!permuter.runTest())
		{
			extraInformation += permuter.getExtraInformation();
			return false;
		}
		
		return true;
	}

	private static Event[] createTestEvents(int count)
	{
		Date start;
		Date end;
		ArrayList<Event> events = new ArrayList<Event>();
		for (int k = 0; k < count; k++)
		{
			start = new Date(3900, 0, 0, 0, 3 * k);
			end = new Date(3900, 0, 0, 0, 3 * k + 1);
			events.add(new Event(String.valueOf(k + 1), start, end));
		}
		return events.toArray(new Event[0]);
	}
	
	private static Event[] duplicateEvents(Event[] testEvents) {
		
		Event[] duplicates = new Event[2 * testEvents.length];
		for (int k = 0; k < testEvents.length; k++)
		{
			duplicates[2 * k] = testEvents[k];
			duplicates[2 * k + 1] = testEvents[k];
		}
		return duplicates;
	}
}
