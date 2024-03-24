package pro.yingsun.game.database;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import pro.yingsun.game.dto.Game;


public class GameStorage {

  private volatile static GameStorage singleton;

  @Getter
  private final Map<String, Game> games = new HashMap<>();

  private GameStorage() {}

  public static GameStorage getInstance() {
    if (singleton == null) {
      synchronized (GameStorage.class) {
        if (singleton == null) {
          singleton = new GameStorage();
        }
      }
    }
    return singleton;
  }
}
