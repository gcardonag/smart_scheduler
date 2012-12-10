package testingclasses;

import scheduler.Event;


public abstract class BaseTester {
	
	protected String extraInformation;
	
	public BaseTester() { }
	
	public abstract String getName();
	
	public String getExtraInformation()
	{
		return extraInformation;
	}
	
	protected void setErrorMessage(Exception ex, Event[] content)
	{
		String message = "An unexpected exception occurred while testing.\nException: " + ex + "\nStack Trace:\n";
		for (StackTraceElement trace : ex.getStackTrace())
		{
			message += "\t" + trace.toString() + "\n";
		}
		setErrorMessage(message, content);
	}
	
	protected void setErrorMessage(BaseTester innerTester)
	{
		setErrorMessage("Failed inner " + innerTester.getName() + "\n" + innerTester.getExtraInformation());
	}

	protected void setErrorMessage(String message)
	{
		extraInformation = getName() + " Failed\nError: " + message;
	}

	protected void setErrorMessage(String message, Event[] content)
	{
		extraInformation = getName() + " Failed\nError: " + message;
		//Might want to concatenate the event content later
	}
}
