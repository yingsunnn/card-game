package pro.yingsun.game.respository;

import java.util.UUID;

public class CommonRepository {

  protected String generateId () {
    return UUID.randomUUID().toString();
  }
}
