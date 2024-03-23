package pro.yingsun.game.dto;

import java.util.List;
import pro.yingsun.game.enumeration.CardFaceValue;
import pro.yingsun.game.enumeration.CardSuit;

public interface Deck {

  List<Card> cards = List.of(
      Card.builder().suit(CardSuit.HEART).faceValue(CardFaceValue.ACE).build(),
      Card.builder().suit(CardSuit.HEART).faceValue(CardFaceValue.TWO).build(),
      Card.builder().suit(CardSuit.HEART).faceValue(CardFaceValue.THREE).build(),
      Card.builder().suit(CardSuit.HEART).faceValue(CardFaceValue.FOUR).build(),
      Card.builder().suit(CardSuit.HEART).faceValue(CardFaceValue.FIVE).build(),
      Card.builder().suit(CardSuit.HEART).faceValue(CardFaceValue.SIX).build(),
      Card.builder().suit(CardSuit.HEART).faceValue(CardFaceValue.SEVEN).build(),
      Card.builder().suit(CardSuit.HEART).faceValue(CardFaceValue.EIGHT).build(),
      Card.builder().suit(CardSuit.HEART).faceValue(CardFaceValue.NINE).build(),
      Card.builder().suit(CardSuit.HEART).faceValue(CardFaceValue.TEN).build(),
      Card.builder().suit(CardSuit.HEART).faceValue(CardFaceValue.JACK).build(),
      Card.builder().suit(CardSuit.HEART).faceValue(CardFaceValue.QUEEN).build(),
      Card.builder().suit(CardSuit.HEART).faceValue(CardFaceValue.KING).build(),

      Card.builder().suit(CardSuit.SPADE).faceValue(CardFaceValue.ACE).build(),
      Card.builder().suit(CardSuit.SPADE).faceValue(CardFaceValue.TWO).build(),
      Card.builder().suit(CardSuit.SPADE).faceValue(CardFaceValue.THREE).build(),
      Card.builder().suit(CardSuit.SPADE).faceValue(CardFaceValue.FOUR).build(),
      Card.builder().suit(CardSuit.SPADE).faceValue(CardFaceValue.FIVE).build(),
      Card.builder().suit(CardSuit.SPADE).faceValue(CardFaceValue.SIX).build(),
      Card.builder().suit(CardSuit.SPADE).faceValue(CardFaceValue.SEVEN).build(),
      Card.builder().suit(CardSuit.SPADE).faceValue(CardFaceValue.EIGHT).build(),
      Card.builder().suit(CardSuit.SPADE).faceValue(CardFaceValue.NINE).build(),
      Card.builder().suit(CardSuit.SPADE).faceValue(CardFaceValue.TEN).build(),
      Card.builder().suit(CardSuit.SPADE).faceValue(CardFaceValue.JACK).build(),
      Card.builder().suit(CardSuit.SPADE).faceValue(CardFaceValue.QUEEN).build(),
      Card.builder().suit(CardSuit.SPADE).faceValue(CardFaceValue.KING).build(),

      Card.builder().suit(CardSuit.CLUB).faceValue(CardFaceValue.ACE).build(),
      Card.builder().suit(CardSuit.CLUB).faceValue(CardFaceValue.TWO).build(),
      Card.builder().suit(CardSuit.CLUB).faceValue(CardFaceValue.THREE).build(),
      Card.builder().suit(CardSuit.CLUB).faceValue(CardFaceValue.FOUR).build(),
      Card.builder().suit(CardSuit.CLUB).faceValue(CardFaceValue.FIVE).build(),
      Card.builder().suit(CardSuit.CLUB).faceValue(CardFaceValue.SIX).build(),
      Card.builder().suit(CardSuit.CLUB).faceValue(CardFaceValue.SEVEN).build(),
      Card.builder().suit(CardSuit.CLUB).faceValue(CardFaceValue.EIGHT).build(),
      Card.builder().suit(CardSuit.CLUB).faceValue(CardFaceValue.NINE).build(),
      Card.builder().suit(CardSuit.CLUB).faceValue(CardFaceValue.TEN).build(),
      Card.builder().suit(CardSuit.CLUB).faceValue(CardFaceValue.JACK).build(),
      Card.builder().suit(CardSuit.CLUB).faceValue(CardFaceValue.QUEEN).build(),
      Card.builder().suit(CardSuit.CLUB).faceValue(CardFaceValue.KING).build(),

      Card.builder().suit(CardSuit.DIAMOND).faceValue(CardFaceValue.ACE).build(),
      Card.builder().suit(CardSuit.DIAMOND).faceValue(CardFaceValue.TWO).build(),
      Card.builder().suit(CardSuit.DIAMOND).faceValue(CardFaceValue.THREE).build(),
      Card.builder().suit(CardSuit.DIAMOND).faceValue(CardFaceValue.FOUR).build(),
      Card.builder().suit(CardSuit.DIAMOND).faceValue(CardFaceValue.FIVE).build(),
      Card.builder().suit(CardSuit.DIAMOND).faceValue(CardFaceValue.SIX).build(),
      Card.builder().suit(CardSuit.DIAMOND).faceValue(CardFaceValue.SEVEN).build(),
      Card.builder().suit(CardSuit.DIAMOND).faceValue(CardFaceValue.EIGHT).build(),
      Card.builder().suit(CardSuit.DIAMOND).faceValue(CardFaceValue.NINE).build(),
      Card.builder().suit(CardSuit.DIAMOND).faceValue(CardFaceValue.TEN).build(),
      Card.builder().suit(CardSuit.DIAMOND).faceValue(CardFaceValue.JACK).build(),
      Card.builder().suit(CardSuit.DIAMOND).faceValue(CardFaceValue.QUEEN).build(),
      Card.builder().suit(CardSuit.DIAMOND).faceValue(CardFaceValue.KING).build()
      );
}
