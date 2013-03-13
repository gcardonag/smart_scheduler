package scheduler;

import java.util.Calendar;
import java.util.LinkedList;

import eventCollection.Event;

public class PomodoroCreator {
	
	private int pomodoro_duration;
	private int short_break_duration;
	private int long_break_duration;
	
	public PomodoroCreator(int pomodoro, int short_break, int long_break) {
		pomodoro_duration = pomodoro;
		short_break_duration = short_break;
		long_break_duration = long_break;
	}

	public Iterable<Event> getPomodorosFromEvent(Event e) {
		LinkedList<Event> list = new LinkedList<Event>();
		int duration = e.getDuration();
		if(duration < pomodoro_duration) {
			System.out.println("SINGLE: Duration: "+duration);
			list.add(e);
			return list;
		}
		int pomodoros = 0;
		Calendar start = e.getStart();
		Calendar end = e.getStart();
		while(duration >= pomodoro_duration) {
			end = (Calendar) start.clone();
			end.add(Calendar.MINUTE, pomodoro_duration);
			Event p = new Event(e.getName(), start, end, false, e.isRepeating());
			list.add(p);
			duration-=pomodoro_duration;
			pomodoros++;
			if(pomodoros >= 4 && duration >= long_break_duration) {
				start = (Calendar) end.clone();
				end = (Calendar) start.clone();
				end.add(Calendar.MINUTE, long_break_duration);
				Event lb = new Event("Long Break", start, end, false, e.isRepeating());
				list.add(lb);
				duration-=long_break_duration;
				pomodoros = 0;
			}
			else if(duration >= short_break_duration) {
				start = (Calendar) end.clone();
				end = (Calendar) start.clone();
				end.add(Calendar.MINUTE, short_break_duration);
				Event sb = new Event("Short Break", start, end, false, e.isRepeating());
				list.add(sb);
				duration-=short_break_duration;
			}
			start = (Calendar) end.clone();
		}
		return list;
	}
	
}
