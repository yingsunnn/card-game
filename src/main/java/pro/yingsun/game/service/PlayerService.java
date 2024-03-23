package pro.yingsun.game.service;

import java.util.List;
import org.springframework.stereotype.Service;
import pro.yingsun.game.dto.Player;
import pro.yingsun.game.dto.PlayerSummary;

@Service
public class PlayerService {

  public List<Player> addPlayerToGame(String gameId, Player player) {
    return null;
  }

  public List<Player> deletePlayerFromGame(String gameId, String playerId) {
    return null;
  }

  public List<PlayerSummary> getPlayers(String gameId) {
    return null;
  }
}
