package pro.yingsun.game.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;

public enum CardFaceValue {
  ACE("Ace", 1),
  TWO("2", 2),
  THREE("3", 3),
  FOUR("4", 4),
  FIVE("5", 5),
  SIX("6", 6),
  SEVEN("7", 7),
  EIGHT("8", 8),
  NINE("9", 9),
  TEN("10", 10),
  JACK("Jack", 11),
  QUEEN("Queen", 12),
  KING("King", 13);

  private final String name;
  private final int value;

  CardFaceValue(String name, int value) {
    this.name = name;
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return this.name;
  }

  public int getValue(){
    return this.value;
  }

  @JsonCreator
  public static CardFaceValue from (String name) {
    return Arrays.stream(CardFaceValue.values())
        .filter(cardFaceValue -> cardFaceValue.name.equalsIgnoreCase(name))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Invalid value for CardFaceValue."));
  }

}
