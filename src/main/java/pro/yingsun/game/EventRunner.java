package pro.yingsun.game;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pro.yingsun.game.queue.EventConsumer;

@Slf4j
@Component
@Profile("!integration")
@RequiredArgsConstructor
public class EventRunner implements CommandLineRunner {

  private final EventConsumer eventConsumer;

  @Override
  public void run(String... args) {
    this.consumeEvent();
  }

  private void consumeEvent() {
    log.debug("Event consumer is started.");
    while(true) {
      this.eventConsumer.consume();
    }
  }
}
