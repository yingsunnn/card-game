package pro.yingsun.game.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.stereotype.Service;
import pro.yingsun.game.dto.Card;
import pro.yingsun.game.dto.Deck;
import pro.yingsun.game.dto.Event;
import pro.yingsun.game.dto.Game;
import pro.yingsun.game.dto.GameCreation;
import pro.yingsun.game.enumeration.EventEntity;
import pro.yingsun.game.lock.DistributedLock;
import pro.yingsun.game.lock.LockKey;
import pro.yingsun.game.lock.LockSignature;
import pro.yingsun.game.queue.EventProducer;
import pro.yingsun.game.respository.GameRepository;

@Service
@RequiredArgsConstructor
public class GameService {

  private final GameRepository gameRepository;
  private final DistributedLock lock;
  private final EventProducer eventProducer;

  public Game createGame(GameCreation gameCreation) {

    final LockSignature lockSignature = this.lock.lock(LockKey.GAMES)
        .orElseThrow(() -> new IllegalMonitorStateException("Another game operation is in progress"));
    Game game = null;
    try {
      game = this.gameRepository.createNewGame();

      LinkedList<Card> shoe = new LinkedList<>();
      for (int i = 0; i < gameCreation.getShoeSize(); i ++) {
        shoe.addAll(Deck.cards);
      }

      game.setShoe(shoe);

      return game;
    } finally {
      this.eventProducer.produce(Event.builder()
              .entity(EventEntity.GAME)
              .description("Created game " + Optional.ofNullable(game).map(Game::getGameId).orElse(null))
              .createdAt(Instant.now())
          .build());
      this.lock.unlock(lockSignature);
    }
  }

  public void deleteGame(String id) {
    final LockSignature lockSignature = this.lock.lock(LockKey.GAMES)
        .orElseThrow(() -> new IllegalMonitorStateException("Another game operation is in progress"));
    try {
      this.gameRepository.deleteGame(id);
    } finally {
      this.eventProducer.produce(Event.builder()
          .entity(EventEntity.GAME)
          .description("Deleted game " + id)
          .createdAt(Instant.now())
          .build());
      this.lock.unlock(lockSignature);
    }
  }

  public List<Game> getGames(List<String> ids) {
    final LockSignature lockSignature = this.lock.lock(LockKey.GAMES)
        .orElseThrow(() -> new IllegalMonitorStateException("Another game operation is in progress"));
    try {
      List<Game> games = SerializationUtils.clone((ArrayList<Game>) this.gameRepository.getGames(ids));

      games.forEach(game -> {
        game.setShoe(null);
        Optional.ofNullable(game.getPlayers()).stream().flatMap(Collection::stream).forEach(player -> player.setCards(null));
      });

      return games;
    } finally {
      this.eventProducer.produce(Event.builder()
          .entity(EventEntity.GAME)
          .description("Retrieved game " + ids)
          .createdAt(Instant.now())
          .build());
      this.lock.unlock(lockSignature);
    }
  }
}
