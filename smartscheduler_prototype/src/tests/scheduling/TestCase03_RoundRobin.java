package tests.scheduling;

import eventCollection.EventTree;
import scheduling.dayScheduling.DSRoundRobin;
import scheduling.dayScheduling.DayScheduler;

/**Tests the RoundRobin time scheduler. Maximum duration of an event
 * is set to be 60 minutes.
 * @author Anthony Llanos Velazquez
 *
 */
public class TestCase03_RoundRobin{

	/**Main method.
	 * @param args
	 */
	public static void main(String[] args){
		new TestCase().runTest();
	}
	
	/**Implementation of this test case <code>(TestCase03)</code>.
	 * @author Anthony
	 *
	 */
	protected static class TestCase extends TestCase02_ShortestTaskFirst.TestCase{
		
		/* (non-Javadoc)
		 * @see tests.scheduling.TestCase02.TestCase#checkResults(eventCollection.EventTree)
		 */
		@Override
		protected boolean checkResults(EventTree results) {
			return results.size() == 20;
		}

		/* (non-Javadoc)
		 * @see tests.scheduling.TestCase02.TestCase#makeTimeScheduler()
		 */
		@Override
		protected DayScheduler makeTimeScheduler() {
			final int QUANTUM = 60; //60 minutes quantum.
			return new DSRoundRobin(QUANTUM);
		}
	}
}
