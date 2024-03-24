package pro.yingsun.game.service;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.yingsun.game.dto.Card;
import pro.yingsun.game.dto.Deck;
import pro.yingsun.game.dto.Game;
import pro.yingsun.game.dto.ShoeSummary;
import pro.yingsun.game.lock.DistributedLock;
import pro.yingsun.game.lock.LockKey;
import pro.yingsun.game.lock.LockSignature;
import pro.yingsun.game.respository.GameRepository;

@Service
@RequiredArgsConstructor
public class ShoeService {

  private final GameRepository gameRepository;
  private final DistributedLock lock;

  public void addDecksToShoe(String gameId, int incrementSize) {
    final LockSignature lockSignature = this.lock.lock(LockKey.GAMES, gameId, LockKey.SHOE)
        .orElseThrow(() -> new IllegalMonitorStateException("Another shoe operation is in progress"));
    try {
      Game game = this.gameRepository.getGame(gameId);
      List<Card> shoe = Optional.ofNullable(game.getShoe())
          .map(s -> this.increaseShoe(s, incrementSize))
          .orElseGet(() -> this.increaseShoe(new LinkedList<>(), incrementSize));
      game.setShoe(shoe);
    } finally {
      this.lock.unlock(lockSignature);
    }
  }

  private List<Card> increaseShoe(List<Card> shoe, int incrementSize) {
    for (int i = 0; i < incrementSize; i++) {
      shoe.addAll(Deck.cards);
    }
    return shoe;
  }

  public void shuffleShoe(String gameId) {
    final LockSignature lockSignature = this.lock.lock(LockKey.GAMES, gameId, LockKey.SHOE)
        .orElseThrow(() -> new IllegalMonitorStateException("Another shoe operation is in progress"));
    try {
      Game game = this.gameRepository.getGame(gameId);
      Collections.shuffle(game.getShoe());
    } finally {
      this.lock.unlock(lockSignature);
    }
  }

  public ShoeSummary getShoeSummary(String gameId) {

    final LockSignature lockSignature = this.lock.lock(LockKey.GAMES, gameId, LockKey.SHOE)
        .orElseThrow(() -> new IllegalMonitorStateException("Another shoe operation is in progress"));
    try {
      Game game = this.gameRepository.getGame(gameId);
      ShoeSummary shoeSummary = ShoeSummary.builder().build();

      Optional.ofNullable(game.getShoe()).stream().flatMap(Collection::stream)
          .forEach(card -> {
            switch (card.getSuit()) {
              case HEART -> shoeSummary.increaseHearts();
              case SPADE -> shoeSummary.increaseSpades();
              case CLUB -> shoeSummary.increaseClubs();
              case DIAMOND -> shoeSummary.increaseDiamonds();
            }
          });

      return shoeSummary;
    } finally {
      this.lock.unlock(lockSignature);
    }
  }
}
