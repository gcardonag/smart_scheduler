package testing.tree;

import java.util.ArrayList;

import scheduler.Event;
import scheduler.EventCollection;

public class MultipleRemovalTester extends ParameterizedTester2<Event[], Event[]> {

	public String getName() {
		return "Multiple Removal Tester";
	}

	protected boolean runInternalTest(Event[] additions, Event[] removals) {
		
		try
		{
			int k;
			boolean removed;
			boolean contained;
			EventTree collection = new EventTree();
			ArrayList<Event> content = new ArrayList<Event>();
			
			//Add events to the collection
			//We do not verify whether additions were performed correctly or not
			//here since that's already tested under the AdditionTester.
			for (k = 0; k < additions.length; k++)
			{
				if (collection.add(additions[k]))
					content.add(additions[k]);
			}
			
			//Remove the elements in removals
			for (k = 0; k < removals.length; k++)
			{
				contained = content.remove(removals[k]);
				removed = collection.remove(removals[k]);
				
				if (removed && !contained)
				{
					setErrorMessage("An event was apparently removed from the collection despite not being contained in it."
							+ "\nremoved event = " + removals[k], additions);
					return false;
				}
				if (!removed && contained)
				{
					setErrorMessage("An event was not removed from the collection but should have been removed."
							+ "\nremoved event = " + removals[k], additions);
					return false;
				}
				
				//Verify that the state of the collection is valid
				CorrectnessTester checker = new CorrectnessTester();
				if (!checker.runTest(collection, content.toArray( new Event[0] )))
				{
					setErrorMessage(checker);
					return false;
				}
			}
			
			return true;
		}
		catch (Exception ex)
		{
			setErrorMessage(ex, additions);
			return false;
		}
	}
}
