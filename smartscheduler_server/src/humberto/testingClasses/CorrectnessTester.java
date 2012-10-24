package humberto.testingClasses;

import java.util.Arrays;

import scheduler.Event;
import scheduler.EventCollection;

public class CorrectnessTester extends ParameterizedTester2<EventCollection, Event[]> {

	public String getName() {
		return "Correctness Tester";
	}

	protected boolean runInternalTest(EventCollection collection, Event[] content) {
		
		try
		{
			int k;
			
			//Check that size() is correct
			if (collection.size() != content.length)
			{
				setErrorMessage("The size of the collection is not equal to number of elements in content."
						+ "\ncollection.size() = " + collection.size() + "\ncontent.length = " + content.length, content);
				return false;
			}
			
			//Check that isEmpty() is correct
			if (collection.isEmpty() != (content.length == 0))
			{
				setErrorMessage("EventCollection.isEmpty() returned an incorrect result for the number of elements in content."
						+ "\ncollection.isEmpty() = " + collection.isEmpty() + "\ncontent.length = " + content.length, content);
				return false;
			}
			
			//Sort the source array
			Arrays.sort(content);
			
			if (content.length > 0)
			{
				//Check that the minimum and maximum elements are correct
				if (collection.getMinimum() != content[0])
				{
					setErrorMessage("The collection returned an incorrect minimum event."
							+ "\ncollection.getMinimum() = " + collection.getMinimum() + "\ncontent minimum = " + content[0], content);
					return false;
				}
				
				if (collection.getMaximum() != content[content.length - 1])
				{
					setErrorMessage("The collection returned an incorrect maximum event."
							+ "\ncollection.getMaximum() = " + collection.getMaximum() + "\ncontent maximum = " + content[content.length - 1], content);
					return false;
				}
			}
			else
			{
				//Check that the minimum and maximum elements are null since the collection is empty
				if (collection.getMinimum() != null)
				{
					setErrorMessage("The collection returned a non-null minimum event while the collection should be empty."
							+ "\ncollection.getMinimum() = " + collection.getMinimum(), content);
					return false;
				}
				
				if (collection.getMaximum() != null)
				{
					setErrorMessage("The collection returned a non-null maximum event while the collection should be empty."
							+ "\ncollection.getMaximum() = " + collection.getMaximum(), content);
					return false;
				}
			}
			
			//Check that iterating over the EventCollection outputs the events
			//in the same order as the events in the sorted array.
			k = 0;
			for (Event event : collection)
			{
				if (k >= content.length)
				{
					setErrorMessage("The iterator provided more events than should be stored in the collection."
							+ "\ncontent.length = " + content.length + "\niterations = " + (k + 1), content);
					return false;
				}
				if (content[k] != event)
				{
					setErrorMessage("The events in the collection are ordered differently from those in a sorted array.", content);
					return false;
				}
				k++;
			}
			
			if (k < content.length)
			{
				setErrorMessage("The iterator provided fewer events than should be stored in the collection."
						+ "\ncontent.length = " + content.length + "\niterations = " + (k + 1), content);
				return false;
			}
			
			return true;
		}
		catch (Exception ex)
		{
			setErrorMessage(ex, content);
			return false;
		}
	}
}
