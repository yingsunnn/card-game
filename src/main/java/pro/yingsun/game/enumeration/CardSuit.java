package pro.yingsun.game.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;

public enum CardSuit {
  HEART("heart"),
  SPADE("spade"),
  CLUB("club"),
  DIAMOND("diamond");

  private final String value;

  CardSuit(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return this.value;
  }

  @JsonCreator
  public static CardSuit from (String value) {
    return Arrays.stream(CardSuit.values())
        .filter(cardSuit ->  cardSuit.value.equalsIgnoreCase(value))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Invalid value for CardSuit."));
  }
}
