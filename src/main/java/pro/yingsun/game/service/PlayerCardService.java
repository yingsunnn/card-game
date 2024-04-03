package pro.yingsun.game.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import pro.yingsun.game.dto.Card;
import pro.yingsun.game.dto.Event;
import pro.yingsun.game.dto.Game;
import pro.yingsun.game.dto.Player;
import pro.yingsun.game.enumeration.EventEntity;
import pro.yingsun.game.exception.DataNotFoundException;
import pro.yingsun.game.lock.DistributedLock;
import pro.yingsun.game.lock.LockKey;
import pro.yingsun.game.lock.LockSignature;
import pro.yingsun.game.queue.EventProducer;
import pro.yingsun.game.respository.GameRepository;

@Service
@RequiredArgsConstructor
public class PlayerCardService {

  private final GameRepository gameRepository;
  private final DistributedLock lock;
  private final EventProducer eventProducer;

  public List<Card> getPlayerCards(String gameId, String playerId) {
    final LockSignature lockSignature = this.lock.lock(LockKey.GAMES, gameId, LockKey.SHOE)
        .orElseThrow(() -> new IllegalMonitorStateException("Another shoe operation is in progress when get player's cards."));
    try {
      Game game = this.gameRepository.getGame(gameId);

      return Optional.ofNullable(game.getPlayers()).stream().flatMap(Collection::stream)
          .filter(player -> player.getPlayerId().equals(playerId))
          .findFirst()
          .map(Player::getCards)
          .orElseThrow(() -> new DataNotFoundException("The player " + playerId + " doesn't exist."));
    } finally {
      this.eventProducer.produce(Event.builder()
          .gameId(gameId)
          .entity(EventEntity.PLAYER)
          .entityId(playerId)
          .description("Get player's cards in game " + gameId)
          .createdAt(Instant.now())
          .build());
      this.lock.unlock(lockSignature);
    }
  }

  public Card dealCardToPlayer(String gameId, String playerId) {
    final LockSignature lockSignature = this.lock.lock(LockKey.GAMES, gameId, LockKey.SHOE)
        .orElseThrow(() -> new IllegalMonitorStateException("Another shoe operation is in progress when deal card to player."));
    try {
      Game game = this.gameRepository.getGame(gameId);

      if (CollectionUtils.isEmpty(game.getShoe())) {
        throw new DataNotFoundException("Shoe of the game " + gameId + " is empty.");
      }

      Player player = Optional.ofNullable(game.getPlayers()).stream().flatMap(Collection::stream)
          .filter(p -> p.getPlayerId().equals(playerId))
          .findFirst()
          .orElseThrow(() -> new DataNotFoundException("The player " + playerId + " doesn't exist."));

      Card card = game.getShoe().poll();

      Optional.ofNullable(player.getCards()).orElseGet(() -> {
            List<Card> cards = new ArrayList<>();
            player.setCards(cards);
            return cards;})
          .add(card);

      return card;
    } finally {
      this.eventProducer.produce(Event.builder()
          .gameId(gameId)
          .entity(EventEntity.PLAYER)
          .entityId(playerId)
          .description("Deal a card to player in game " + gameId)
          .createdAt(Instant.now())
          .build());
      this.lock.unlock(lockSignature);
    }
  }
}
