package pro.yingsun.game.database;

public class EventStorage {

  private volatile static EventStorage singleton;

  private EventStorage() {}

  public static EventStorage getInstance() {
    if (singleton == null) {
      synchronized (EventStorage.class) {
        if (singleton == null) {
          singleton = new EventStorage();
        }
      }
    }

    return singleton;
  }
}
