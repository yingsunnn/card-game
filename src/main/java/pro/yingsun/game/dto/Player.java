package pro.yingsun.game.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Value
public class Player {

  String playerId;
  @NotEmpty
  String playerName;
  List<Card> cards;
}
