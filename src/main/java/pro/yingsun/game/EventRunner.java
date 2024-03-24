package pro.yingsun.game;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pro.yingsun.game.queue.EventConsumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventRunner implements CommandLineRunner {

  private final EventConsumer eventConsumer;

  private int intervalMilliseconds = 1000;

  @Override
  public void run(String... args) throws Exception {
    this.consumeEvent();
  }

  private void consumeEvent() {

    log.debug("Event consumer is started.");

    while(true) {
      this.eventConsumer.consume();
      try {
        TimeUnit.MILLISECONDS.sleep(intervalMilliseconds);
      } catch (Exception e) {
        log.error(e.getMessage(), e);
        Thread.currentThread().interrupt();
      }
    }
  }
}
