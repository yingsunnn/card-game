package pro.yingsun.game.database;

import java.util.Comparator;
import java.util.PriorityQueue;
import lombok.Getter;
import pro.yingsun.game.dto.Event;

public class EventStorage {

  private volatile static EventStorage singleton;

  @Getter
  private final PriorityQueue<Event> priorityQueue = new PriorityQueue<>(Comparator.comparing(Event::getCreatedAt));

  private EventStorage() {}

  public static EventStorage getInstance() {
    if (singleton == null) {
      synchronized (EventStorage.class) {
        if (singleton == null) {
          singleton = new EventStorage();
        }
      }
    }

    return singleton;
  }
}
