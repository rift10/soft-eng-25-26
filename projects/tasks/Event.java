package projects.tasks;
import java.util.PriorityQueue;

public interface Event extends Comparable<Event> {

    public int getTimestamp();
    
    @Override
    default int compareTo(Event other) {
        return getTimestamp() - other.getTimestamp();
    }

    default void schedule(PriorityQueue<Event> queue, Event event) {
        queue.add(event);
    }

    public void run(Resources resources);
}
