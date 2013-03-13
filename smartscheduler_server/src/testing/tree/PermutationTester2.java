package testing.testingclasses;

import java.lang.reflect.Array;


public class PermutationTester2<T, S> extends ParameterizedTester<S> {

	private Class<T> elementType;
	private T[] dataSet;
	private ParameterizedTester2<T[], S> innerTester;
	private int minSubsetSize;
	private int maxSubsetSize;
	
	public PermutationTester2(Class<T> elementType, T[] dataSet, ParameterizedTester2<T[], S> innerTester)
	{
		this.elementType = elementType;
		this.dataSet = dataSet;
		this.innerTester = innerTester;
		this.minSubsetSize = 0;
		this.maxSubsetSize = dataSet.length;
	}
	
	public PermutationTester2(Class<T> elementType, T[] dataSet, ParameterizedTester2<T[], S> innerTester,
			int minSubsetSize, int maxSubsetSize)
	{
		if (minSubsetSize < 0)
			throw new IllegalArgumentException("minSubsetSize must be greater than or equal to 0");
		if (minSubsetSize > maxSubsetSize)
			throw new IllegalArgumentException("minSubsetSize must be less than or equal to maxSubsetSize");
		if (maxSubsetSize > dataSet.length)
			throw new IllegalArgumentException("maxSubsetSize must be less than or equal to the length of dataSet");
		
		this.elementType = elementType;
		this.dataSet = dataSet;
		this.innerTester = innerTester;
		this.minSubsetSize = minSubsetSize;
		this.maxSubsetSize = maxSubsetSize;
	}
	
	public T[] getDataSet() {
		return dataSet;
	}

	public ParameterizedTester2<T[], S> getInnerTester() {
		return innerTester;
	}

	public String getName() {
		return "Permutation Tester 2";
	}

	@SuppressWarnings("unchecked")
	protected boolean runInternalTest(S secondArgument) {
		//This tester implements an exhaustive test that checks a certain
		//subtest with every permutation of a given data set.

		int length;
		T[] subset;
		boolean[] used = new boolean[dataSet.length];
		
		if (minSubsetSize == 0)
		{
			//Check for length = 0
			subset = (T[]) Array.newInstance(elementType, 0);
			if (!innerTester.runTest(subset, secondArgument))
				return false;
		}
		
		//Check for length > 0
		length = (minSubsetSize > 0) ? minSubsetSize : 1;
		for (; length < maxSubsetSize; length++)
		{
			subset = (T[]) Array.newInstance(elementType, length);
			if (!testPermutations(0, subset, used, secondArgument))
				return false;
		}
		
		return true;
	}

	private boolean testPermutations(int index, T[] subset, boolean[] used, S secondArgument) {
		if (index + 1 < subset.length)
		{
			for (int k = 0; k < used.length; k++)
			{
				if (!used[k])
				{
					used[k] = true;
					subset[index] = dataSet[k];
					if (!testPermutations(index + 1, subset, used, secondArgument))
						return false;
					used[k] = false;
				}
			}
		}
		else
		{
			for (int k = 0; k < used.length; k++)
			{
				if (!used[k])
				{
					subset[index] = dataSet[k];
					if (!innerTester.runTest((T[]) subset.clone(), secondArgument))
					{
						extraInformation = innerTester.getExtraInformation();
						return false;
					}
				}
			}
		}
		return true;
	}
}
