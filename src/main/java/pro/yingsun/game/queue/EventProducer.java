package pro.yingsun.game.queue;

import java.util.concurrent.BlockingQueue;
import org.springframework.stereotype.Component;
import pro.yingsun.game.dto.Event;
import pro.yingsun.game.exception.ServerInternalException;

@Component
public class EventProducer {

  private final BlockingQueue<Event> eventQueue = EventQueue.getInstance().getEventQueue();

  public void produce(Event event) {
    try {
      this.eventQueue.put(event);
    } catch (InterruptedException e) {
      throw new ServerInternalException("Produce event failed. Event: " + event, e);
    }
  }
}
