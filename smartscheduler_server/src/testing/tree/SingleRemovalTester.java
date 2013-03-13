package testing.tree;

import java.util.ArrayList;

import scheduler.Event;
import scheduler.EventCollection;

public class SingleRemovalTester extends ParameterizedTester<Event[]> {

	public String getName() {
		return "Single Removal Tester";
	}

	protected boolean runInternalTest(Event[] data) {
		
		try
		{
			if (data.length < 2)
				return true;
			
			int k;
			Event last;
			boolean removed;
			boolean contained;
			EventCollection collection = new EventCollection();
			ArrayList<Event> content = new ArrayList<Event>();
			
			//Add events to the collection
			//We do not verify whether additions were performed correctly or not
			//here since that's already tested under the AdditionTester.
			for (k = 0; k < data.length - 1; k++)
			{
				if (collection.add(data[k]))
					content.add(data[k]);
			}
			
			//Remove the last element in data from content.
			last = data[data.length - 1];
			contained = content.remove(last);
			
			//Try removing the last element from the collection.
			removed = collection.remove(last);
			
			if (removed && !contained)
			{
				setErrorMessage("An event was apparently removed from the collection despite not being contained in it."
						+ "\nremoved event = " + data[k], data);
				return false;
			}
			if (!removed && contained)
			{
				setErrorMessage("An event was not removed from the collection but should have been removed."
						+ "\nremoved event = " + data[k], data);
				return false;
			}
			
			//Verify the final state of the collection is valid
			CorrectnessTester checker = new CorrectnessTester();
			if (!checker.runTest(collection, content.toArray( new Event[0] )))
			{
				setErrorMessage(checker);
				return false;
			}
			
			return true;
		}
		catch (Exception ex)
		{
			setErrorMessage(ex, data);
			return false;
		}
	}
}
