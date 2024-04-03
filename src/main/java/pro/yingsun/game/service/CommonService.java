package pro.yingsun.game.service;

import java.util.UUID;

public class CommonService {

  protected String generateId () {
    return UUID.randomUUID().toString();
  }
}
