package pro.yingsun.game.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Value
public class ShoeSummary {

  Integer hearts;
  Integer spades;
  Integer clubs;
  Integer diamonds;
}
