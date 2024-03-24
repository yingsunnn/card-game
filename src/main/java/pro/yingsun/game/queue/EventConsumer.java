package pro.yingsun.game.queue;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pro.yingsun.game.dto.Event;
import pro.yingsun.game.respository.EventRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventConsumer {

  private final BlockingQueue<Event> eventQueue = EventQueue.getInstance().getEventQueue();
  private final EventRepository eventRepository;

  public void consume() {
    Event event = this.eventQueue.poll();
    log.debug("Consuming event: " + event);

    if (Objects.nonNull(event)) {
      this.eventRepository.addEvent(event);
    }
  }
}
