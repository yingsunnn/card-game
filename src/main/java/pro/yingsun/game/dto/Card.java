package pro.yingsun.game.dto;

import java.io.Serializable;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import pro.yingsun.game.enumeration.CardFaceValue;
import pro.yingsun.game.enumeration.CardSuit;

@Jacksonized
@Builder
@Value
public class Card implements Serializable {

  CardSuit suit;
  CardFaceValue faceValue;
}
