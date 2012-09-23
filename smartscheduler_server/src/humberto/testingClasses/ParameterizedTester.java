package testing;


public abstract class ParameterizedTester<T> extends BaseTester {
	
	public ParameterizedTester() { }
	
	protected abstract boolean runInternalTest(T data);
	
	public boolean runTest(T data)
	{
		extraInformation = "";
		return runInternalTest(data);
	}
}
