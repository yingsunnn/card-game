package pro.yingsun.game.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.stereotype.Service;
import pro.yingsun.game.dto.Card;
import pro.yingsun.game.dto.Event;
import pro.yingsun.game.dto.Game;
import pro.yingsun.game.dto.Player;
import pro.yingsun.game.dto.PlayerSummary;
import pro.yingsun.game.enumeration.EventEntity;
import pro.yingsun.game.exception.DataNotFoundException;
import pro.yingsun.game.lock.DistributedLock;
import pro.yingsun.game.lock.LockKey;
import pro.yingsun.game.lock.LockSignature;
import pro.yingsun.game.queue.EventProducer;
import pro.yingsun.game.respository.GameRepository;

@Service
@RequiredArgsConstructor
public class PlayerService {
  private final GameRepository gameRepository;
  private final DistributedLock lock;
  private final EventProducer eventProducer;

  public Player addPlayerToGame(String gameId, Player player) {

    final LockSignature lockSignature = this.lock.lock(LockKey.GAMES, gameId, LockKey.PLAYER)
        .orElseThrow(() -> new IllegalMonitorStateException("Another player operation is in progress"));
    try {
      Game game = this.gameRepository.getGame(gameId);

      if (Objects.isNull(player.getPlayerId())) {
        player.setPlayerId(UUID.randomUUID().toString());
      }

      if (Objects.isNull(player.getCards())) {
        player.setCards(new ArrayList<>());
      }

      if (Objects.isNull(game.getPlayers())) {
        game.setPlayers(new ArrayList<>());
      }

      game.getPlayers().add(player);

      return player;
    } finally {
      this.eventProducer.produce(Event.builder()
          .entity(EventEntity.PLAYER)
          .entityId(player.getPlayerId())
          .description("Add the player to the game " + gameId)
          .createdAt(Instant.now())
          .build());
      this.lock.unlock(lockSignature);
    }
  }

  public List<Player> deletePlayerFromGame(String gameId, String playerId) {
    final LockSignature lockSignature = this.lock.lock(LockKey.GAMES, gameId, LockKey.PLAYER)
        .orElseThrow(() -> new IllegalMonitorStateException("Another player operation is in progress"));
    try {
      Game game = this.gameRepository.getGame(gameId);

      Player player = game.getPlayers().stream()
          .filter(p -> playerId.equals(p.getPlayerId()))
          .findAny()
          .orElseThrow(() -> new DataNotFoundException("Player " + playerId + " doesn't exist."));

      game.getPlayers().remove(player);

      List<Player> result = SerializationUtils.clone((ArrayList<Player>)game.getPlayers());
      result.forEach(p -> p.setCards(null));
      return result;
    } finally {
      this.eventProducer.produce(Event.builder()
          .entity(EventEntity.PLAYER)
          .entityId(playerId)
          .description("Delete the player from the game " + gameId)
          .createdAt(Instant.now())
          .build());
      this.lock.unlock(lockSignature);
    }
  }

  public List<PlayerSummary> getPlayers(String gameId) {
    final LockSignature lockSignature = this.lock.lock(LockKey.GAMES, gameId, LockKey.PLAYER)
        .orElseThrow(() -> new IllegalMonitorStateException("Another player operation is in progress"));
    try {
      return Optional.ofNullable(this.gameRepository.getGame(gameId))
          .map(Game::getPlayers)
          .stream()
          .flatMap(Collection::stream)
          .map(player -> PlayerSummary.builder()
              .playerId(player.getPlayerId())
              .playerName(player.getPlayerName())
              .totalAddedValue(this.calculateTotalAddedValue(player.getCards()))
              .build())
          .sorted(Comparator.comparing(PlayerSummary::getTotalAddedValue).reversed())
          .toList();
    } finally {
      this.eventProducer.produce(Event.builder()
          .entity(EventEntity.PLAYER)
          .entityId("all")
          .description("Retrieve all players of the game " + gameId)
          .createdAt(Instant.now())
          .build());
      this.lock.unlock(lockSignature);
    }
  }

  private int calculateTotalAddedValue(List<Card> cards) {
    return Optional.ofNullable(cards).stream().flatMap(Collection::stream)
        .map(card -> card.getFaceValue().getValue())
        .mapToInt(Integer::valueOf)
        .sum();
  }
}
