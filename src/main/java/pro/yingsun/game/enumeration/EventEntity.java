package pro.yingsun.game.enumeration;

public enum EventEntity {
  GAME("game"),
  SHOE("shoe"),
  PLAYER("player"),
  PLAYER_CARD("player card");

  private final String entity;

  EventEntity(String entity) {
    this.entity = entity;
  }

  @Override
  public String toString() {
    return this.entity;
  }
}
