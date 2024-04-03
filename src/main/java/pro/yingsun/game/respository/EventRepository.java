package pro.yingsun.game.respository;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import pro.yingsun.game.database.EventStorage;
import pro.yingsun.game.dto.Event;
import pro.yingsun.game.enumeration.EventEntity;

@Repository
public class EventRepository {

  private final PriorityQueue<Event> priorityQueue = EventStorage.getInstance().getPriorityQueue();

  public void addEvent(Event event) {
    this.priorityQueue.add(event);
  }

  public List<Event> getEvents(String gameId, EventEntity entity, String entityId) {
    List<Event> events = new ArrayList<>(this.priorityQueue);

    return events.stream()
        .filter(event -> StringUtils.isBlank(gameId) || gameId.equalsIgnoreCase(event.getGameId()))
        .filter(event -> entity == null || entity == event.getEntity())
        .filter(event -> StringUtils.isBlank(entityId) || entityId.equals(event.getEntityId()))
        .toList();
  }
}
