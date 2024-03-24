package pro.yingsun.game.lock;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pro.yingsun.game.database.LockStorage;

@Slf4j
@Component
public class DistributedLock {

  private final Map<String, String> locks = LockStorage.getInstance().getLocks();

  private final Duration lockWaitTime = Duration.ofSeconds(5);
  private final Duration lockRetryInterval = Duration.ofMillis(500);

  public Optional<LockSignature> lock(String... keySegments) {
    return this.lockOrWait(Thread.currentThread().getName(), keySegments);
  }

  public Optional<LockSignature> lockOrWait(String threadName, String... keySegments) {
    final Instant waitUntil = Instant.now().plus(this.lockWaitTime);

    Optional<LockSignature> lockSignature;
    while ((lockSignature = this.lockOrFail(threadName, keySegments)).isEmpty()) {
      if (Instant.now().isAfter(waitUntil)) {
        break;
      }

      try {
        TimeUnit.MILLISECONDS.sleep(this.lockRetryInterval.toMillis());
      } catch (Exception e) {
        log.error(e.getMessage(), e);
        Thread.currentThread().interrupt();
      }
    }

    return lockSignature;
  }

  public Optional<LockSignature> lockOrFail(String threadName, String... keySegments) {
    LockSignature lockSignature = this.buildLockSignature(threadName, keySegments);

    if (this.locks.containsKey(lockSignature.getKey())) {
      return Optional.empty();
    }

    this.locks.put(lockSignature.getKey(), lockSignature.getThreadName());
    return Optional.of(lockSignature);
  }

  public void unlock(LockSignature lockSignature) {

    Optional.ofNullable(lockSignature)
        .map(LockSignature::getKey)
        .filter(this.locks::containsKey)
        .ifPresent(key -> {
          this.locks.remove(key);
          log.debug("Unlocked lockSignature: " + lockSignature);
        });
  }

  protected LockSignature buildLockSignature(String threadName, String... keySegments) {
    return LockSignature.builder()
        .key("DIST-LOCK:" + String.join(":", keySegments))
        .threadName(threadName)
        .build();
  }
}
