package pro.yingsun.game.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import lombok.Getter;
import pro.yingsun.game.dto.Event;

public class EventQueue {

  private volatile static EventQueue singleton;

  @Getter
  private final BlockingQueue<Event> eventQueue = new ArrayBlockingQueue<>(1000);

  private EventQueue() {}

  public static EventQueue getInstance() {
    if (singleton == null) {
      synchronized (EventQueue.class) {
        if (singleton == null) {
          singleton = new EventQueue();
        }
      }
    }

    return singleton;
  }

}
