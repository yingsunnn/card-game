package pro.yingsun.game.service;

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
import pro.yingsun.game.respository.GameRepository;

@Service
@RequiredArgsConstructor
public class ShoeService {

  private final GameRepository gameRepository;
  private final DistributedLock lock;

  public void addDecksToShoe(String gameId, int incrementSize) {
    Game game = this.gameRepository.getGame(gameId);
    List<Card> shoe = Optional.ofNullable(game.getShoe())
        .map(s -> this.increaseShoe(s, incrementSize))
        .orElseGet(() -> this.increaseShoe(new LinkedList<>(), incrementSize));
    game.setShoe(shoe);
  }

  private List<Card> increaseShoe(List<Card> shoe, int incrementSize) {
    for (int i = 0; i < incrementSize; i ++) {
      shoe.addAll(Deck.cards);
    }
    return shoe;
  }

  public void shuffleShoe(String gameId) {
  }

  public ShoeSummary getShoeSummary(String gameId) {
    return null;
  }
}
