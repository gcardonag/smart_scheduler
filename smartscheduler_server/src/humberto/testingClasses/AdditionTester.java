package testing;

import java.util.ArrayList;

import scheduler.Event;
import scheduler.EventCollection;

public class AdditionTester extends ParameterizedTester<Event[]> {

	public String getName() {
		return "Addition Tester";
	}

	protected boolean runInternalTest(Event[] data) {
		
		try
		{
			int k;
			int h;
			boolean conflict;
			EventCollection collection = new EventCollection();
			ArrayList<Event> content = new ArrayList<Event>();
			
			//Add events to the collection
			for (k = 0; k < data.length; k++)
			{
				if (collection.add(data[k]))
				{
					//Check that the event does not conflict with any previous events
					conflict = false;
					for (h = 0; h < k; h++)
					{
						if (data[k].conflictsWith(data[h]))
						{
							conflict = true;
							break;
						}
					}
					if (conflict)
					{
						setErrorMessage("An event was added to the collection that should not have been accepted due to" +
								" a conflict with an existing event in the collection."
								+ "\nnew event = " + data[k] + "\nold event = " + data[h], data);
						return false;
					}
					content.add(data[k]);
				}
				else
				{
					//Check that the event did conflict with some other event
					conflict = false;
					for (h = 0; h < k; h++)
					{
						if (data[k].conflictsWith(data[h]))
						{
							conflict = true;
							break;
						}
					}
					if (!conflict)
					{
						setErrorMessage("An event was added to the collection that should have been accepted but was" +
								" rejected due to a non-existent conflict."
								+ "\nrejected event = " + data[k], data);
						return false;
					}
				}
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
