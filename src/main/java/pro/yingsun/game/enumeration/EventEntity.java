package pro.yingsun.game.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;

public enum EventEntity {
  GAME("game"),
  SHOE("shoe"),
  PLAYER("player");

  private final String entity;

  EventEntity(String entity) {
    this.entity = entity;
  }

  @Override
  @JsonValue
  public String toString() {
    return this.entity;
  }

  @JsonCreator
  public static EventEntity from (String entity) {
    return Arrays.stream(EventEntity.values())
        .filter(eventEntity -> eventEntity.entity.equalsIgnoreCase(entity))
        .findAny()
        .orElseThrow(() -> new IllegalArgumentException("Invalid value for EventEntity."));
  }
}
