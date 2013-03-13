package testing.tree;


public abstract class SimpleTester extends BaseTester {
	
	public SimpleTester() { }
	
	protected abstract boolean runInternalTest();
	
	public boolean runTest()
	{
		extraInformation = "";
		return runInternalTest();
	}
}
