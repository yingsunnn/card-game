package pro.yingsun.game.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.yingsun.game.dto.Event;
import pro.yingsun.game.enumeration.EventEntity;
import pro.yingsun.game.respository.EventRepository;

@Service
@RequiredArgsConstructor
public class EventService {

  private final EventRepository eventRepository;

  public void addEvent(Event event) {
    this.eventRepository.addEvent(event);
  }

  public List<Event> getEvents(EventEntity entity, String entityId) {
    return this.eventRepository.getEvents(entity, entityId);
  }
}
