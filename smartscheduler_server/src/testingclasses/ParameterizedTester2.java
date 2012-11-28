package testingclasses;


public abstract class ParameterizedTester2<T, S> extends BaseTester {
	
	public ParameterizedTester2() { }
	
	protected abstract boolean runInternalTest(T first, S second);
	
	public boolean runTest(T first, S second)
	{
		extraInformation = "";
		return runInternalTest(first, second);
	}
}
