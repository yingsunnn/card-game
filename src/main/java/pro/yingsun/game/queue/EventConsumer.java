package pro.yingsun.game.queue;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pro.yingsun.game.dto.Event;
import pro.yingsun.game.exception.ServerInternalException;
import pro.yingsun.game.respository.EventRepository;
import pro.yingsun.game.service.EventService;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventConsumer {

  private final BlockingQueue<Event> eventQueue = EventQueue.getInstance().getEventQueue();
  private final EventService eventService;

  public void consume() {
    try {
      Event event = this.eventQueue.take();
      log.debug("Consuming event: " + event);
      this.eventService.addEvent(event);
    } catch (InterruptedException e) {
      throw new ServerInternalException("Failed when consuming event.", e);
    }
  }
}
