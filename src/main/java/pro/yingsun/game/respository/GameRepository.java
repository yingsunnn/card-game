package pro.yingsun.game.respository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import pro.yingsun.game.database.GameStorage;
import pro.yingsun.game.dto.Game;
import pro.yingsun.game.exception.DataNotFoundException;
import pro.yingsun.game.exception.DuplicateDataException;

@Repository
public class GameRepository {

  private final Map<String, Game> games = GameStorage.getInstance().getGames();

  public void createNewGame(Game game) {
    if (this.games.containsKey(game.getGameId())) {
      throw new DuplicateDataException("The game " + game.getGameId() + "already exists.");
    }

    this.games.put(game.getGameId(), game);
  }

  public void deleteGame (String id) {
    if (StringUtils.isBlank(id)) {
      throw new IllegalArgumentException("Game id cannot be blank");
    }

    if (!this.games.containsKey(id)) {
      throw new DataNotFoundException("Game " + id + " doesn't exist.");
    }

    this.games.remove(id);
  }

  public List<Game> getGames(List<String> ids) {
    if (CollectionUtils.isEmpty(ids)) {
      return new ArrayList<>(this.games.values());
    }

    return ids.stream().filter(this.games::containsKey).map(this.games::get).toList();
  }

  public Game getGame(String id) {

    if (!this.games.containsKey(id)) {
      throw new DataNotFoundException("Game " + id + " doesn't exist.");
    }

    return this.games.get(id);
  }
}
