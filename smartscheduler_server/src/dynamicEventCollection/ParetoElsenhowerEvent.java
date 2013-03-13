/* @deprecated annotations mean that the class or 
 * method may need revision, not that it should not be used.
 */
package dynamicEventCollection;

import java.util.Calendar;

public class ParetoElsenhowerEvent extends DynamicEvent {

	/**The priority of the task in the Elsenhower Matrix:
	 * <ul>
	 * <li>Priority 2 'Do' : Tasks are of high importance second 
	 * only to static events. 80% time dedication.</li>
	 * <li>Priority 3 'Delegate' : Tasks are of medium importance 
	 * and are dedicated only 20% of the time.</li>
	 * <li>Priority 4 'Drop' : Tasks that are only done on free time.</li>
	 * </ul>
	 * 
	 */
	private int priority ;

	
	/**Creates an instance of the event that will be managed by the
	 * Pareto-Elsenhower Time Management method.
	 * @param name - the name of the event.
	 * @param start - the start date calendar of the event.
	 * @param end - the end date calendar of the event.
	 * @param priority - the priority of the event in the Pareto-Elsenhower hierarchy.
	 */
	public ParetoElsenhowerEvent(String name, Calendar start, Calendar end, int priority, int hours, int minutes){
		super(name, start, end, hours, minutes);
		this.priority = priority ;
	}
	
	/**Returns the event's priority in the Elsenhower Matrix.
	 * @return
	 */
	public int getPriority(){
		return this.priority;
	}

}
