package pro.yingsun.game.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.yingsun.game.dto.Game;
import pro.yingsun.game.dto.GameCreation;
import pro.yingsun.game.lock.DistributedLock;
import pro.yingsun.game.lock.LockSignature;
import pro.yingsun.game.respository.GameRepository;

@Service
@RequiredArgsConstructor
public class GameService {

  private final static String LOCK_KEY = "games";

  private final GameRepository gameRepository;
  private final DistributedLock lock;

  public Game createGame(GameCreation gameCreation) {

    final LockSignature lockSignature = this.lock.lock(LOCK_KEY)
        .orElseThrow(() -> new IllegalMonitorStateException("Another game operation is in progress"));
    try {
      Game game = this.gameRepository.createNewGame();

      //TODO add decks

      return game;
    } finally {
      // TODO event
      this.lock.unlock(lockSignature);
    }
  }

  public void deleteGame(String id) {
    final LockSignature lockSignature = this.lock.lock(LOCK_KEY)
        .orElseThrow(() -> new IllegalMonitorStateException("Another game operation is in progress"));
    try {
      this.gameRepository.deleteGame(id);
    } finally {
      // TODO event
      this.lock.unlock(lockSignature);
    }
  }

  public List<Game> getGames(List<String> ids) {
    return this.gameRepository.getGame(ids);
  }
}
