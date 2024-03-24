package pro.yingsun.game.respository;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import org.springframework.stereotype.Repository;
import pro.yingsun.game.database.EventStorage;
import pro.yingsun.game.dto.Event;

@Repository
public class EventRepository {

  private final PriorityQueue<Event> priorityQueue = EventStorage.getInstance().getPriorityQueue();

  public void addEvent(Event event) {
    this.priorityQueue.add(event);
  }

  public List<Event> getEvents() {
    return new ArrayList<>(this.priorityQueue);
  }
}
