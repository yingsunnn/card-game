package pro.yingsun.game.dto;

import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.With;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Value
public class Game {

  @With
  String gameId;
  List<Player> players;
  List<Card> shoe;
}
