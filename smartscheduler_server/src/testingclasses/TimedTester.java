package testingclasses;



public abstract class TimedTester extends SimpleTester {
	
	public TimedTester() { }
		
	public boolean runTest()
	{
		boolean result;
		
		extraInformation = "";
		long time = System.currentTimeMillis();
		System.out.println("Running tester: " + getName());
		result = runInternalTest();
		time = System.currentTimeMillis() - time;
		extraInformation += "\nTesting time: " + (time / 1000.0) + " s";
		return result;
	}
}
