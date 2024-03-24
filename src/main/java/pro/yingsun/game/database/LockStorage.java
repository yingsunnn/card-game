package pro.yingsun.game.database;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

public class LockStorage {

  private volatile static LockStorage singleton;

  @Getter
  private final Map<String, String> locks = new HashMap<>();

  private LockStorage() {}

  public static LockStorage getInstance() {
    if (singleton == null) {
      synchronized (LockStorage.class) {
        if (singleton == null) {
          singleton = new LockStorage();
        }
      }
    }

    return singleton;
  }
}
