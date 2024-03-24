package pro.yingsun.game.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Data
public class ShoeSummary {

  private int hearts;
  private int spades;
  private int clubs;
  private int diamonds;

  public void increaseHearts(){
    this.hearts++;
  }

  public void increaseSpades(){
    this.spades++;
  }

  public void increaseClubs(){
    this.clubs++;
  }

  public void increaseDiamonds(){
    this.diamonds++;
  }
}
