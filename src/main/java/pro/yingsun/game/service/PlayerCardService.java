package pro.yingsun.game.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import pro.yingsun.game.dto.Card;
import pro.yingsun.game.dto.Game;
import pro.yingsun.game.dto.Player;
import pro.yingsun.game.exception.DataNotFoundException;
import pro.yingsun.game.lock.DistributedLock;
import pro.yingsun.game.lock.LockKey;
import pro.yingsun.game.lock.LockSignature;
import pro.yingsun.game.respository.GameRepository;

@Service
@RequiredArgsConstructor
public class PlayerCardService {

  private final GameRepository gameRepository;
  private final DistributedLock lock;

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
      this.lock.unlock(lockSignature);
    }
  }

  public Card dealCardToPlayer(String gameId, String playerId) {
    final LockSignature lockSignature = this.lock.lock(LockKey.GAMES, gameId, LockKey.SHOE)
        .orElseThrow(() -> new IllegalMonitorStateException("Another shoe operation is in progress when deal card to player."));
    try {
      Game game = this.gameRepository.getGame(gameId);

      if (CollectionUtils.isEmpty(game.getShoe())) {
        throw  new DataNotFoundException("Shoe of the game " + gameId + " is empty.");
      }

      Card card = game.getShoe().poll();

      Player player = Optional.ofNullable(game.getPlayers()).stream().flatMap(Collection::stream)
          .filter(p -> p.getPlayerId().equals(playerId))
          .findFirst()
          .orElseThrow(() -> new DataNotFoundException("The player " + playerId + " doesn't exist."));

      Optional.ofNullable(player.getCards()).orElseGet(() -> {
            List<Card> cards = new ArrayList<>();
            player.setCards(cards);
            return cards;})
          .add(card);

      return card;
    } finally {
      this.lock.unlock(lockSignature);
    }
  }
}
