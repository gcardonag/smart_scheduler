package scheduler;

import java.util.Calendar;
import java.util.LinkedList;

import eventCollection.*;

/**
 * Breaks an event into it's corresponding list of pomodoro events, event durations are in minutes
 * @author Nelson Reyes Ciena
 * @author Anthony Llanos
 * @version 0.1
 *
 */
public class PomodoroCreator {
	
	public static final int DEFAULT_POMODORO_DURATION = 25;
	public static final int DEFAULT_SHORT_BREAK_DURATION = 5;
	public static final int DEFAULT_LONG_BREAK_DURATION = 30;
	private int pomodoro_duration;
	private int short_break_duration;
	private int long_break_duration;
	
	/**
	 * Constructs a creator using the given durations for each event
	 * @param pomodoro duration for each pomodoro event
	 * @param short_break duration for each of the short breaks between pomodoros
	 * @param long_break duration for each long break after 4 pomodoro events
	 */
	public PomodoroCreator(int pomodoro, int short_break, int long_break) {
		pomodoro_duration = pomodoro;
		short_break_duration = short_break;
		long_break_duration = long_break;
	}
	
	/**
	 * Default constructor, uses the default values for event durations
	 */
	public PomodoroCreator() {
		pomodoro_duration = DEFAULT_POMODORO_DURATION;
		short_break_duration = DEFAULT_SHORT_BREAK_DURATION;
		long_break_duration = DEFAULT_LONG_BREAK_DURATION;
	}
	
	/**
	* Generates a new list of schedules pomodoro events from a list of scheduled events.
	* @param list the source list to generate pomodoros from
	* @return the generated list of pomodoro events.
	*/
	public Iterable<Event> implementPomodoroToList(Iterable<Event> list) {
		EventTree pomodoros = new EventTree();
		for(Event e : list) {
			pomodoros.add(this.getPomodorosFromEvent(e));
		}
		return pomodoros;
	}
	
	/**
	 * Generates a list of pomodoro events from a single Event object
	 * @param e an event to break into pomodoro events
	 * @return a list of pomodoro events generates from an ordinary event
	 */
	private Iterable<Event> getPomodorosFromEvent(Event e) {
		EventTree list = new EventTree();
		int duration = e.getDuration();
		
		//Case for a single pomodoro event (duration is shorter than a single pomodoro)
		if(duration < pomodoro_duration) {
			list.add(e);
			return list;
		}
		
		int count = 0;
		int pomodoros = 0;
		Calendar start = e.getStart();
		Calendar end = e.getStart();
		/**
		 * This while loop can be improved
		 */
		while(duration >= pomodoro_duration) {
			end.add(Calendar.MINUTE, pomodoro_duration);
			duration -= pomodoro_duration;
			pomodoros++;
			count++;
			Event p = new Event(e.getName()+" #"+count, start, end, false, e.isRepeating());
			list.add(p);
			//System.out.println("ADDED POMODORO: "+p.toString());
			start.add(Calendar.MINUTE, pomodoro_duration);
			
			if(pomodoros >= 4 && duration >= long_break_duration + pomodoro_duration) {
				end.add(Calendar.MINUTE, long_break_duration);
				duration -= long_break_duration;
				pomodoros = 0;
				Event lb = new Event(long_break_duration+" min. break", start, end, false, e.isRepeating());
				list.add(lb);
				//System.out.println("ADDED LONG BREAK: "+lb.toString());
				start.add(Calendar.MINUTE, long_break_duration);
			}
			
			else if(duration >= short_break_duration + pomodoro_duration) {
				end.add(Calendar.MINUTE, short_break_duration);
				Event sb = new Event(short_break_duration+" min. break", start, end, false, e.isRepeating());
				list.add(sb);
				//System.out.println("ADDED SHORT BREAK: "+sb.toString());
				duration -= short_break_duration;
				start.add(Calendar.MINUTE, short_break_duration);
			}
		}
		return list;
	}
	
}
